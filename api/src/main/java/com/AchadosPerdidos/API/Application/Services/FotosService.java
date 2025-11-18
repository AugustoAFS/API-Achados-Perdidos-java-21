package com.AchadosPerdidos.API.Application.Services;

import com.AchadosPerdidos.API.Application.DTOs.Fotos.FotosDTO;
import com.AchadosPerdidos.API.Application.DTOs.Fotos.FotosListDTO;
import com.AchadosPerdidos.API.Application.Mapper.FotosModelMapper;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IFotosService;
import com.AchadosPerdidos.API.Domain.Entity.Foto;
import com.AchadosPerdidos.API.Domain.Repository.FotosRepository;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FotosService implements IFotosService {

    @Autowired
    private FotosRepository fotosRepository;

    @Autowired
    private FotosModelMapper fotosModelMapper;

    @Autowired
    private S3Service s3Service;

    @Override
    public FotosListDTO getAllFotos() {
        List<Foto> fotos = fotosRepository.findAll();
        return fotosModelMapper.toListDTO(fotos);
    }

    @Override
    public FotosDTO getFotoById(int id) {
        Foto foto = fotosRepository.findById(id);
        return fotosModelMapper.toDTO(foto);
    }

    @Override
    public FotosDTO createFoto(FotosDTO fotosDTO) {
        Foto foto = fotosModelMapper.toEntity(fotosDTO);
        foto.setDtaCriacao(LocalDateTime.now());
        foto.setFlgInativo(false);
        
        Foto savedFoto = fotosRepository.save(foto);
        return fotosModelMapper.toDTO(savedFoto);
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
        return fotosModelMapper.toDTO(updatedFoto);
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
        return fotosModelMapper.toListDTO(activeFotos);
    }

    @Override
    public FotosListDTO getFotosByUser(int userId) {
        List<Foto> fotos = fotosRepository.findByUser(userId);
        return fotosModelMapper.toListDTO(fotos);
    }

    @Override
    public FotosListDTO getFotosByItem(int itemId) {
        List<Foto> fotos = fotosRepository.findByItem(itemId);
        return fotosModelMapper.toListDTO(fotos);
    }

    @Override
    public FotosListDTO getProfilePhotos(int userId) {
        List<Foto> fotos = fotosRepository.findProfilePhotos(userId);
        return fotosModelMapper.toListDTO(fotos);
    }

    @Override
    public FotosListDTO getItemPhotos(int itemId) {
        List<Foto> fotos = fotosRepository.findItemPhotos(itemId);
        return fotosModelMapper.toListDTO(fotos);
    }

    @Override
    public FotosDTO getMainItemPhoto(int itemId) {
        Foto foto = fotosRepository.findMainItemPhoto(itemId);
        return fotosModelMapper.toDTO(foto);
    }

    @Override
    public FotosDTO getProfilePhoto(int userId) {
        Foto foto = fotosRepository.findProfilePhoto(userId);
        return fotosModelMapper.toDTO(foto);
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
        try {
            // Validar arquivo
            if (file.isEmpty()) {
                throw new IllegalArgumentException("Arquivo não pode estar vazio");
            }

            // Validar tipo de arquivo
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new IllegalArgumentException("Arquivo deve ser uma imagem");
            }

            // Fazer upload para S3
            byte[] fileBytes;
            try {
                fileBytes = file.getBytes();
            } catch (IOException e) {
                throw new RuntimeException("Erro ao ler arquivo: " + e.getMessage(), e);
            }
            
            S3Service.S3UploadResult uploadResult = s3Service.uploadPhoto(
                fileBytes,
                file.getOriginalFilename(),
                contentType,
                userId,
                itemId,
                isProfilePhoto
            );

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
            Foto savedFoto = fotosRepository.save(foto);
            return fotosModelMapper.toDTO(savedFoto);

        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao fazer upload da foto: " + e.getMessage(), e);
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
        return uploadPhoto(file, userId, null, true);
    }

    /**
     * Faz upload de foto de item
     * @param file Arquivo da foto
     * @param userId ID do usuário
     * @param itemId ID do item
     * @return DTO da foto salva
     */
    public FotosDTO uploadItemPhoto(MultipartFile file, Integer userId, Integer itemId) {
        return uploadPhoto(file, userId, itemId, false);
    }
}
