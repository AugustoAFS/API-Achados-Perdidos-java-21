package com.AchadosPerdidos.API.Application.Services;

import com.AchadosPerdidos.API.Application.DTOs.Fotos.FotosDTO;
import com.AchadosPerdidos.API.Application.DTOs.Fotos.FotosListDTO;
import com.AchadosPerdidos.API.Application.Mapper.FotosMapper;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IFotosService;
import com.AchadosPerdidos.API.Exeptions.BusinessException;
import com.AchadosPerdidos.API.Domain.Entity.Foto;
import com.AchadosPerdidos.API.Domain.Repository.FotosRepository;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FotosService implements IFotosService {

    private static final Logger logger = LoggerFactory.getLogger(FotosService.class);

    @Autowired
    private FotosRepository fotosRepository;

    @Autowired
    private FotosMapper fotosMapper;

    @Autowired
    private S3Service s3Service;


    @Override
    public FotosListDTO getAllFotos() {
        List<Foto> fotos = fotosRepository.findAll();
        return fotosMapper.toListDTO(fotos);
    }

    @Override
    public FotosDTO createFotoUsaurio(FotosDTO fotosDTO) {
        Foto foto = fotosMapper.toEntity(fotosDTO);
        foto.setDtaCriacao(LocalDateTime.now());
        foto.setFlgInativo(false);
        
        Foto savedFoto = fotosRepository.save(foto);
        return fotosMapper.toDTO(savedFoto);
    }

    @Override
    public FotosDTO createFotoItem(FotosDTO fotosDTO) {
        Foto foto = fotosMapper.toEntity(fotosDTO);
        foto.setDtaCriacao(LocalDateTime.now());
        foto.setFlgInativo(false);
        
        Foto savedFoto = fotosRepository.save(foto);
        return fotosMapper.toDTO(savedFoto);
    }

    @Override
    public FotosDTO updateFotoUsuario(int id, FotosDTO fotosDTO) {
        Foto existingFoto = fotosRepository.findById(id);
        if (existingFoto == null) {
            return null;
        }
        
        if (fotosDTO.getUrl() != null) {
            existingFoto.setUrl(fotosDTO.getUrl());
        }
        if (fotosDTO.getProvedorArmazenamento() != null) {
            existingFoto.setProvedorArmazenamento(fotosDTO.getProvedorArmazenamento());
        }
        if (fotosDTO.getChaveArmazenamento() != null) {
            existingFoto.setChaveArmazenamento(fotosDTO.getChaveArmazenamento());
        }
        if (fotosDTO.getNomeArquivoOriginal() != null) {
            existingFoto.setNomeArquivoOriginal(fotosDTO.getNomeArquivoOriginal());
        }
        if (fotosDTO.getTamanhoArquivoBytes() != null) {
            existingFoto.setTamanhoArquivoBytes(fotosDTO.getTamanhoArquivoBytes());
        }
        existingFoto.setDtaCriacao(LocalDateTime.now());
        
        Foto updatedFoto = fotosRepository.save(existingFoto);
        return fotosMapper.toDTO(updatedFoto);
    }

    @Override
    public FotosDTO updateFotoItem(int id, FotosDTO fotosDTO) {
        Foto existingFoto = fotosRepository.findById(id);
        if (existingFoto == null) {
            return null;
        }
        
        if (fotosDTO.getUrl() != null) {
            existingFoto.setUrl(fotosDTO.getUrl());
        }
        if (fotosDTO.getProvedorArmazenamento() != null) {
            existingFoto.setProvedorArmazenamento(fotosDTO.getProvedorArmazenamento());
        }
        if (fotosDTO.getChaveArmazenamento() != null) {
            existingFoto.setChaveArmazenamento(fotosDTO.getChaveArmazenamento());
        }
        if (fotosDTO.getNomeArquivoOriginal() != null) {
            existingFoto.setNomeArquivoOriginal(fotosDTO.getNomeArquivoOriginal());
        }
        if (fotosDTO.getTamanhoArquivoBytes() != null) {
            existingFoto.setTamanhoArquivoBytes(fotosDTO.getTamanhoArquivoBytes());
        }
        existingFoto.setDtaCriacao(LocalDateTime.now());
        
        Foto updatedFoto = fotosRepository.save(existingFoto);
        return fotosMapper.toDTO(updatedFoto);
    }

    @Override
    public boolean deleteFotoUsuario(int id) {
        Foto foto = fotosRepository.findById(id);
        if (foto == null) {
            return false;
        }
        
        return fotosRepository.deleteById(id);
    }

    @Override
    public boolean deleteFotoItem(int id) {
        Foto foto = fotosRepository.findById(id);
        if (foto == null) {
            return false;
        }
        
        return fotosRepository.deleteById(id);
    }

    /**
     * Faz upload de uma foto para o S3 e salva no banco de dados
     * @param file Arquivo da foto
     * @param userId ID do usuário
     * @param itemId ID do item (opcional)
     * @param isProfilePhoto Se é foto de perfil
     * @return DTO da foto salva
     */
    public FotosDTO uploadPhoto(MultipartFile file, Integer userId, Integer itemId, boolean isProfilePhoto) {
        logger.info("Iniciando upload de foto - userId: {}, itemId: {}, isProfilePhoto: {}", userId, itemId, isProfilePhoto);
        
        try {
            // Validar arquivo
            if (file == null || file.isEmpty()) {
                logger.warn("Tentativa de upload com arquivo vazio");
                throw new IllegalArgumentException("Arquivo não pode estar vazio");
            }

            logger.debug("Arquivo recebido: nome={}, tamanho={}, contentType={}", 
                file.getOriginalFilename(), file.getSize(), file.getContentType());

            // Validar tipo de arquivo
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                logger.warn("Tentativa de upload de arquivo que não é imagem: {}", contentType);
                throw new IllegalArgumentException("Arquivo deve ser uma imagem. Tipo recebido: " + contentType);
            }

            // Validar userId
            if (userId == null || userId <= 0) {
                logger.warn("Tentativa de upload com userId inválido: {}", userId);
                throw new IllegalArgumentException("userId é obrigatório e deve ser maior que zero");
            }

            // Fazer upload para S3
            byte[] fileBytes;
            try {
                fileBytes = file.getBytes();
                logger.debug("Arquivo lido com sucesso. Tamanho em bytes: {}", fileBytes.length);
            } catch (IOException e) {
                logger.error("Erro ao ler arquivo: {}", e.getMessage(), e);
                throw new RuntimeException("Erro ao ler arquivo: " + e.getMessage(), e);
            }
            
            logger.info("Fazendo upload para S3...");
            S3Service.S3UploadResult uploadResult = s3Service.uploadPhoto(
                fileBytes,
                file.getOriginalFilename(),
                contentType,
                userId,
                itemId,
                isProfilePhoto
            );
            logger.info("Upload para S3 concluído. S3Key: {}, URL: {}", uploadResult.getS3Key(), uploadResult.getFileUrl());

            // Criar entidade Foto
            Foto foto = new Foto();
            foto.setProvedorArmazenamento("s3");
            foto.setChaveArmazenamento(uploadResult.getS3Key());
            foto.setUrl(uploadResult.getFileUrl());
            foto.setNomeArquivoOriginal(uploadResult.getOriginalName());
            foto.setTamanhoArquivoBytes((long) fileBytes.length);
            foto.setFlgInativo(false);
            foto.setDtaCriacao(LocalDateTime.now());

            // Salvar no banco de dados
            logger.debug("Salvando foto no banco de dados...");
            Foto savedFoto = fotosRepository.save(foto);
            logger.info("Foto salva com sucesso. ID: {}", savedFoto.getId());
            
            FotosDTO fotoDTO = fotosMapper.toDTO(savedFoto);
            
            // A associação foto-item será criada pelo método uploadItemPhoto se necessário
            // Não criar aqui para evitar duplicação - uploadItemPhoto é responsável por isso
            
            return fotoDTO;

        } catch (IllegalArgumentException e) {
            logger.error("Erro de validação no upload: {}", e.getMessage());
            throw e;
        } catch (IllegalStateException e) {
            logger.error("Erro de configuração S3: {}", e.getMessage());
            // Preserva a mensagem original do IllegalStateException para que seja mais clara
            throw e;
        } catch (RuntimeException e) {
            // Verifica se é um erro de bucket não encontrado
            if (e.getMessage() != null && e.getMessage().contains("Bucket S3") && e.getMessage().contains("não existe")) {
                logger.error("Bucket S3 não encontrado: {}", e.getMessage());
                throw new IllegalStateException(e.getMessage(), e);
            }
            logger.error("Erro ao fazer upload da foto: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao fazer upload da foto: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Erro inesperado ao fazer upload: {}", e.getMessage(), e);
            throw new RuntimeException("Erro inesperado ao fazer upload: " + e.getMessage(), e);
        }
    }

    /**
     * Baixa uma foto do S3
     * @param fotoId ID da foto
     * @return Conteúdo da foto em bytes
     */
    public byte[] downloadPhoto(int fotoId) {
        Foto foto = fotosRepository.findById(fotoId);
        if (foto == null) {
            throw new IllegalArgumentException("Foto não encontrada");
        }

        if (!"s3".equals(foto.getProvedorArmazenamento())) {
            throw new IllegalArgumentException("Foto não está armazenada no S3");
        }

        return s3Service.downloadFile(foto.getChaveArmazenamento());
    }

    /**
     * Deleta uma foto do S3 e do banco de dados
     * @param fotoId ID da foto
     * @return true se deletada com sucesso
     */
    public boolean deletePhoto(int fotoId) {
        Foto foto = fotosRepository.findById(fotoId);
        if (foto == null) {
            return false;
        }

        try {
            // Deletar do S3 se for S3
            if ("s3".equals(foto.getProvedorArmazenamento())) {
                s3Service.deleteFile(foto.getChaveArmazenamento());
            }

            // Deletar do banco de dados
            return fotosRepository.deleteById(fotoId);

        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao deletar foto: " + e.getMessage(), e);
        }
    }

    /**
     * Faz upload de foto de perfil
     * @param file Arquivo da foto
     * @param userId ID do usuário
     * @return DTO da foto salva
     */
    public FotosDTO uploadProfilePhoto(MultipartFile file, Integer userId) {
        FotosDTO foto = uploadPhoto(file, userId, null, true);
        
        // Criar associação automaticamente na tabela fotos_usuario
        // Nota: Métodos createFotoUsuario e updateFotoUsuario não estão mais na interface IFotoUsuarioService
        // Esta funcionalidade precisa ser implementada diretamente via repository ou adicionada à interface
        try {
            if (foto != null && foto.getId() != null) {
                logger.info("Foto de perfil salva com ID: {} para usuário ID: {}", foto.getId(), userId);
                // TODO: Implementar criação de associação foto-usuário via repository direto
            }
        } catch (Exception e) {
            logger.error("Erro ao processar associação foto-usuário após upload: {}", e.getMessage(), e);
        }
        
        return foto;
    }

    /**
     * Faz upload de foto de item e cria associação na tabela foto_item
     * A associação só é criada se a foto for salva no banco com sucesso
     * @param file Arquivo da foto
     * @param userId ID do usuário
     * @param itemId ID do item
     * @return DTO da foto salva
     */
    public FotosDTO uploadItemPhoto(MultipartFile file, Integer userId, Integer itemId) {
        if (itemId == null || itemId <= 0) {
            throw new IllegalArgumentException("ID do item é obrigatório para upload de foto de item");
        }
        
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Arquivo de foto não pode ser nulo ou vazio");
        }
        
        // 1. Fazer upload da foto e salvar no banco
        FotosDTO foto = uploadPhoto(file, userId, itemId, false);
        
        // 2. Verificar se a foto foi criada com sucesso no banco antes de criar associação
        if (foto == null || foto.getId() == null) {
            logger.error("❌ Foto não foi salva no banco, não é possível criar associação. ItemId: {}", itemId);
            throw new RuntimeException("Erro: Foto não foi salva no banco de dados");
        }
        
        // 3. Verificar se a foto realmente existe no banco antes de associar
        try {
            Foto fotoVerificada = fotosRepository.findById(foto.getId());
            if (fotoVerificada == null) {
                logger.error("❌ Foto ID {} não encontrada no banco após upload. ItemId: {}", foto.getId(), itemId);
                throw new RuntimeException("Erro: Foto não encontrada no banco após upload");
            }
            
            // 4. Criar associação na tabela foto_item APENAS se a foto foi salva com sucesso
            // Nota: Método createFotoItem não está mais na interface IFotoItemService
            // Esta funcionalidade precisa ser implementada diretamente via repository ou adicionada à interface
            logger.info("✅ Foto salva com sucesso. Item ID: {} e foto ID: {} (foto salva no banco)", itemId, foto.getId());
            // TODO: Implementar criação de associação foto-item via repository direto
            
        } catch (BusinessException e) {
            // Se já existe associação ativa, apenas loga (não é erro crítico)
            if (e.getMessage() != null && e.getMessage().contains("Já existe uma associação ativa")) {
                logger.warn("⚠️ Associação já existe entre item ID: {} e foto ID: {}", itemId, foto.getId());
            } else {
                logger.error("❌ Erro de negócio ao criar associação foto-item: {}", e.getMessage());
                throw e; // Re-lança exceções de negócio
            }
        } catch (Exception e) {
            logger.error("❌ Erro ao criar associação foto-item após upload bem-sucedido: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao criar associação foto-item: " + e.getMessage(), e);
        }
        
        return foto;
    }
}
