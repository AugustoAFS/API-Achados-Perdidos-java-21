package com.AchadosPerdidos.API.Application.Services;

import com.AchadosPerdidos.API.Application.DTOs.Fotos.FotosDTO;
import com.AchadosPerdidos.API.Application.DTOs.Fotos.FotosListDTO;
import com.AchadosPerdidos.API.Application.DTOs.FotoUsuario.FotoUsuarioCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.FotoUsuario.FotoUsuarioUpdateDTO;
import com.AchadosPerdidos.API.Application.DTOs.FotoItem.FotoItemCreateDTO;
import com.AchadosPerdidos.API.Application.Mapper.FotosMapper;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IFotosService;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IFotoUsuarioService;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IFotoItemService;
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

    @Autowired
    private IFotoUsuarioService fotoUsuarioService;

    @Autowired
    private IFotoItemService fotoItemService;

    @Override
    public FotosListDTO getAllFotos() {
        List<Foto> fotos = fotosRepository.findAll();
        return fotosMapper.toListDTO(fotos);
    }

    @Override
    public FotosDTO getFotoById(int id) {
        Foto foto = fotosRepository.findById(id);
        return fotosMapper.toDTO(foto);
    }

    @Override
    public FotosDTO createFoto(FotosDTO fotosDTO) {
        Foto foto = fotosMapper.toEntity(fotosDTO);
        foto.setDtaCriacao(LocalDateTime.now());
        foto.setFlgInativo(false);
        
        Foto savedFoto = fotosRepository.save(foto);
        return fotosMapper.toDTO(savedFoto);
    }

    @Override
    public FotosDTO updateFoto(int id, FotosDTO fotosDTO) {
        Foto existingFoto = fotosRepository.findById(id);
        if (existingFoto == null) {
            return null;
        }
        
        // Mapeamento correto dos campos do FotosDTO para a entidade Foto
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
    public boolean deleteFoto(int id) {
        Foto foto = fotosRepository.findById(id);
        if (foto == null) {
            return false;
        }
        
        return fotosRepository.deleteById(id);
    }

    @Override
    public FotosListDTO getActiveFotos() {
        List<Foto> activeFotos = fotosRepository.findActive();
        return fotosMapper.toListDTO(activeFotos);
    }

    @Override
    public FotosListDTO getFotosByUser(int userId) {
        List<Foto> fotos = fotosRepository.findByUser(userId);
        return fotosMapper.toListDTO(fotos);
    }

    @Override
    public FotosListDTO getFotosByItem(int itemId) {
        List<Foto> fotos = fotosRepository.findByItem(itemId);
        return fotosMapper.toListDTO(fotos);
    }

    @Override
    public FotosListDTO getProfilePhotos(int userId) {
        List<Foto> fotos = fotosRepository.findProfilePhotos(userId);
        return fotosMapper.toListDTO(fotos);
    }

    @Override
    public FotosListDTO getItemPhotos(int itemId) {
        List<Foto> fotos = fotosRepository.findItemPhotos(itemId);
        return fotosMapper.toListDTO(fotos);
    }

    @Override
    public FotosDTO getMainItemPhoto(int itemId) {
        Foto foto = fotosRepository.findMainItemPhoto(itemId);
        return fotosMapper.toDTO(foto);
    }

    @Override
    public FotosDTO getProfilePhoto(int userId) {
        Foto foto = fotosRepository.findProfilePhoto(userId);
        return fotosMapper.toDTO(foto);
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
            
            // Se for foto de item (não é foto de perfil e tem itemId), criar associação automaticamente
            if (!isProfilePhoto && itemId != null && itemId > 0 && fotoDTO != null && fotoDTO.getId() != null) {
                try {
                    var createDTO = new FotoItemCreateDTO();
                    createDTO.setItemId(itemId);
                    createDTO.setFotoId(fotoDTO.getId());
                    fotoItemService.createFotoItem(createDTO);
                    logger.info("Associação criada entre item ID: {} e foto ID: {}", itemId, fotoDTO.getId());
                } catch (Exception e) {
                    logger.error("Erro ao criar associação foto-item após upload: {}", e.getMessage(), e);
                    // Não falha o upload se a associação falhar, mas loga o erro
                }
            }
            
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
        try {
            if (foto != null && foto.getId() != null) {
                // Desativar fotos de perfil anteriores
                var fotosUsuarioList = fotoUsuarioService.findByUsuarioId(userId);
                if (fotosUsuarioList != null && fotosUsuarioList.getFotoUsuarios() != null) {
                    for (var fu : fotosUsuarioList.getFotoUsuarios()) {
                        if (fu.getFotoId() != null && !fu.getFotoId().equals(foto.getId()) && 
                            (fu.getFlgInativo() == null || !fu.getFlgInativo())) {
                            var updateDTO = new FotoUsuarioUpdateDTO();
                            updateDTO.setFlgInativo(true);
                            fotoUsuarioService.updateFotoUsuario(userId, fu.getFotoId(), updateDTO);
                            logger.info("Foto de perfil anterior (ID: {}) desativada para usuário ID: {}", fu.getFotoId(), userId);
                        }
                    }
                }
                
                // Criar nova associação
                var createDTO = new FotoUsuarioCreateDTO();
                createDTO.setUsuarioId(userId);
                createDTO.setFotoId(foto.getId());
                fotoUsuarioService.createFotoUsuario(createDTO);
                logger.info("Associação criada entre usuário ID: {} e foto ID: {}", userId, foto.getId());
            }
        } catch (Exception e) {
            logger.error("Erro ao criar associação foto-usuário após upload: {}", e.getMessage(), e);
            // Não falha o upload se a associação falhar, mas loga o erro
        }
        
        return foto;
    }

    /**
     * Faz upload de foto de item
     * @param file Arquivo da foto
     * @param userId ID do usuário
     * @param itemId ID do item
     * @return DTO da foto salva
     */
    public FotosDTO uploadItemPhoto(MultipartFile file, Integer userId, Integer itemId) {
        FotosDTO foto = uploadPhoto(file, userId, itemId, false);
        
        // Criar associação automaticamente na tabela fotos_item
        try {
            if (foto != null && foto.getId() != null && itemId != null && itemId > 0) {
                // Criar nova associação
                var createDTO = new FotoItemCreateDTO();
                createDTO.setItemId(itemId);
                createDTO.setFotoId(foto.getId());
                fotoItemService.createFotoItem(createDTO);
                logger.info("Associação criada entre item ID: {} e foto ID: {}", itemId, foto.getId());
            }
        } catch (Exception e) {
            logger.error("Erro ao criar associação foto-item após upload: {}", e.getMessage(), e);
            // Não falha o upload se a associação falhar, mas loga o erro
        }
        
        return foto;
    }
}
