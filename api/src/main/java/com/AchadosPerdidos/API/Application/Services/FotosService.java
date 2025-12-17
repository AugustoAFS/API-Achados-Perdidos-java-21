package com.AchadosPerdidos.API.Application.Services;

import com.AchadosPerdidos.API.Application.DTOs.Fotos.FotosDTO;
import com.AchadosPerdidos.API.Application.DTOs.Fotos.FotosListDTO;
import com.AchadosPerdidos.API.Application.Mapper.FotosMapper;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IFotosService;
import com.AchadosPerdidos.API.Exeptions.BusinessException;
import com.AchadosPerdidos.API.Domain.Entity.Foto;
import com.AchadosPerdidos.API.Domain.Entity.FotoUsuario;
import com.AchadosPerdidos.API.Domain.Entity.FotoItem;
import com.AchadosPerdidos.API.Domain.Entity.Usuario;
import com.AchadosPerdidos.API.Domain.Repository.FotosRepository;
import com.AchadosPerdidos.API.Domain.Repository.FotoUsuarioRepository;
import com.AchadosPerdidos.API.Domain.Repository.FotoItemRepository;
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
    private static final int SIGNED_URL_EXPIRATION_MINUTES = 60; // 1 hora

    @Autowired
    private FotosRepository fotosRepository;

    @Autowired
    private FotosMapper fotosMapper;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private FotoUsuarioRepository fotoUsuarioRepository;

    @Autowired
    private FotoItemRepository fotoItemRepository;


    @Override
    public FotosListDTO getAllFotos() {
        List<Foto> fotos = fotosRepository.findAll();
        FotosListDTO listDTO = fotosMapper.toListDTO(fotos);

        if (listDTO != null && listDTO.getFotos() != null) {
            listDTO.getFotos().forEach(this::generateSignedUrlIfNeeded);
        }

        return listDTO;
    }

    @Override
    public FotosDTO createFotoUsaurio(FotosDTO fotosDTO) {
        Foto foto = fotosMapper.toEntity(fotosDTO);
        foto.setDtaCriacao(LocalDateTime.now());
        foto.setFlgInativo(false);
        
        Foto savedFoto = fotosRepository.save(foto);
        FotosDTO dto = fotosMapper.toDTO(savedFoto);

        generateSignedUrlIfNeeded(dto);

        return dto;
    }

    @Override
    public FotosDTO createFotoItem(FotosDTO fotosDTO) {
        Foto foto = fotosMapper.toEntity(fotosDTO);
        foto.setDtaCriacao(LocalDateTime.now());
        foto.setFlgInativo(false);
        
        Foto savedFoto = fotosRepository.save(foto);
        FotosDTO dto = fotosMapper.toDTO(savedFoto);

        generateSignedUrlIfNeeded(dto);

        return dto;
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
        FotosDTO dto = fotosMapper.toDTO(updatedFoto);

        generateSignedUrlIfNeeded(dto);

        return dto;
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
        FotosDTO dto = fotosMapper.toDTO(updatedFoto);

        generateSignedUrlIfNeeded(dto);

        return dto;
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

    public FotosDTO uploadPhoto(MultipartFile file, Integer userId, Integer itemId, boolean isProfilePhoto) {
        logger.info("Iniciando upload de foto - userId: {}, itemId: {}, isProfilePhoto: {}", userId, itemId, isProfilePhoto);
        
        try {
            if (file == null || file.isEmpty()) {
                logger.warn("Tentativa de upload com arquivo vazio");
                throw new IllegalArgumentException("Arquivo não pode estar vazio");
            }

            logger.debug("Arquivo recebido: nome={}, tamanho={}, contentType={}", 
                file.getOriginalFilename(), file.getSize(), file.getContentType());

            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                logger.warn("Tentativa de upload de arquivo que não é imagem: {}", contentType);
                throw new IllegalArgumentException("Arquivo deve ser uma imagem. Tipo recebido: " + contentType);
            }

            if (userId == null || userId <= 0) {
                logger.warn("Tentativa de upload com userId inválido: {}", userId);
                throw new IllegalArgumentException("userId é obrigatório e deve ser maior que zero");
            }

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

            Foto foto = new Foto();
            foto.setProvedorArmazenamento("s3");
            foto.setChaveArmazenamento(uploadResult.getS3Key());
            foto.setUrl(uploadResult.getFileUrl());
            foto.setNomeArquivoOriginal(uploadResult.getOriginalName());
            foto.setTamanhoArquivoBytes((long) fileBytes.length);
            foto.setFlgInativo(false);
            foto.setDtaCriacao(LocalDateTime.now());

            logger.debug("Salvando foto no banco de dados...");
            Foto savedFoto = fotosRepository.save(foto);
            logger.info("Foto salva com sucesso. ID: {}", savedFoto.getId());
            
            FotosDTO fotoDTO = fotosMapper.toDTO(savedFoto);

            return fotoDTO;

        } catch (IllegalArgumentException e) {
            logger.error("Erro de validação no upload: {}", e.getMessage());
            throw e;
        } catch (IllegalStateException e) {
            logger.error("Erro de configuração S3: {}", e.getMessage());
            throw e;
        } catch (RuntimeException e) {
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

    public boolean deletePhoto(int fotoId) {
        Foto foto = fotosRepository.findById(fotoId);
        if (foto == null) {
            return false;
        }

        try {
            if ("s3".equals(foto.getProvedorArmazenamento())) {
                s3Service.deleteFile(foto.getChaveArmazenamento());
            }

            return fotosRepository.deleteById(fotoId);

        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao deletar foto: " + e.getMessage(), e);
        }
    }

    public FotosDTO uploadProfilePhoto(MultipartFile file, Integer userId) {
        logger.info("Iniciando upload de foto de perfil para usuário ID: {}", userId);

        FotosDTO foto = uploadPhoto(file, userId, null, true);

        if (foto == null || foto.getId() == null) {
            logger.error("Foto de perfil não foi salva no banco. UserId: {}", userId);
            throw new RuntimeException("Erro: Foto de perfil não foi salva no banco de dados");
        }

        try {
            FotoUsuario fotoUsuario = new FotoUsuario();
            Usuario usuario = new Usuario();
            usuario.setId(userId);
            fotoUsuario.setUsuarioId(usuario);

            Foto fotoEntity = new Foto();
            fotoEntity.setId(foto.getId());
            fotoUsuario.setFotoId(fotoEntity);

            fotoUsuario.setDtaCriacao(LocalDateTime.now());
            fotoUsuario.setFlgInativo(false);

            FotoUsuario savedFotoUsuario = fotoUsuarioRepository.save(fotoUsuario);
            logger.info("Associação foto-usuário criada com sucesso. ID: {}, Foto ID: {}, Usuário ID: {}",
                savedFotoUsuario.getId(), foto.getId(), userId);

        } catch (Exception e) {
            logger.error("Erro ao criar associação foto-usuário: {}", e.getMessage(), e);
            throw new RuntimeException("Foto salva, mas erro ao criar associação: " + e.getMessage(), e);
        }

        return foto;
    }

    public FotosDTO uploadItemPhoto(MultipartFile file, Integer userId, Integer itemId) {
        logger.info("Iniciando upload de foto de item. UserId: {}, ItemId: {}", userId, itemId);

        if (itemId == null || itemId <= 0) {
            throw new IllegalArgumentException("ID do item é obrigatório para upload de foto de item");
        }
        
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Arquivo de foto não pode ser nulo ou vazio");
        }
        
        FotosDTO foto = uploadPhoto(file, userId, itemId, false);
        
        if (foto == null || foto.getId() == null) {
            logger.error("Foto não foi salva no banco. ItemId: {}", itemId);
            throw new RuntimeException("Erro: Foto não foi salva no banco de dados");
        }
        
        try {
            Foto fotoVerificada = fotosRepository.findById(foto.getId());
            if (fotoVerificada == null) {
                logger.error("Foto ID {} não encontrada no banco após upload. ItemId: {}", foto.getId(), itemId);
                throw new RuntimeException("Erro: Foto não encontrada no banco após upload");
            }

            FotoItem fotoItem = new FotoItem();
            fotoItem.setItemId(itemId);

            Foto fotoEntity = new Foto();
            fotoEntity.setId(foto.getId());
            fotoItem.setFotoId(fotoEntity);

            fotoItem.setDtaCriacao(LocalDateTime.now());
            fotoItem.setFlgInativo(false);

            FotoItem savedFotoItem = fotoItemRepository.save(fotoItem);
            logger.info("Associação foto-item criada com sucesso. ID: {}, Foto ID: {}, Item ID: {}",
                savedFotoItem.getId(), foto.getId(), itemId);

        } catch (BusinessException e) {
            if (e.getMessage() != null && e.getMessage().contains("Já existe uma associação ativa")) {
                logger.warn("Associação já existe entre item ID: {} e foto ID: {}", itemId, foto.getId());
            } else {
                logger.error("Erro de negócio ao criar associação foto-item: {}", e.getMessage());
                throw e;
            }
        } catch (Exception e) {
            logger.error("Erro ao criar associação foto-item: {}", e.getMessage(), e);
            throw new RuntimeException("Foto salva, mas erro ao criar associação: " + e.getMessage(), e);
        }
        
        return foto;
    }

    private void generateSignedUrlIfNeeded(FotosDTO fotosDTO) {
        if (fotosDTO == null) {
            return;
        }

        if ("s3".equalsIgnoreCase(fotosDTO.getProvedorArmazenamento()) &&
            fotosDTO.getChaveArmazenamento() != null &&
            !fotosDTO.getChaveArmazenamento().isBlank()) {

            try {
                logger.debug("Gerando Signed URL para foto ID: {}, S3 Key: {}",
                    fotosDTO.getId(), fotosDTO.getChaveArmazenamento());

                String signedUrl = s3Service.generateSignedUrl(
                    fotosDTO.getChaveArmazenamento(),
                    SIGNED_URL_EXPIRATION_MINUTES
                );

                if (signedUrl != null) {
                    fotosDTO.setUrl(signedUrl);
                    logger.debug("Signed URL gerada com sucesso para foto ID: {}", fotosDTO.getId());
                } else {
                    logger.warn("Falha ao gerar Signed URL para foto ID: {}, usando URL do banco",
                        fotosDTO.getId());
                }
            } catch (Exception e) {
                logger.error("❌ Erro ao gerar Signed URL para foto ID: {}: {}",
                    fotosDTO.getId(), e.getMessage());
            }
        }
    }

    private void generateSignedUrlsInBatch(List<FotosDTO> fotosDTOList) {
        if (fotosDTOList == null || fotosDTOList.isEmpty()) {
            return;
        }

        logger.debug("Gerando Signed URLs em lote para {} fotos", fotosDTOList.size());
        fotosDTOList.parallelStream().forEach(this::generateSignedUrlIfNeeded);
    }
}
