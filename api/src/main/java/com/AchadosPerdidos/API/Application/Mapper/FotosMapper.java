package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.Fotos.FotosDTO;
import com.AchadosPerdidos.API.Application.DTOs.Fotos.FotosListDTO;
import com.AchadosPerdidos.API.Application.Services.S3Service;
import com.AchadosPerdidos.API.Domain.Entity.Foto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FotosMapper {

    private static final Logger logger = LoggerFactory.getLogger(FotosMapper.class);

    @Autowired(required = false)
    private S3Service s3Service;

    public FotosDTO toDTO(Foto foto) {
        if (foto == null) {
            return null;
        }
        
        // Gerar Signed URL se a foto estiver no S3
        String url = foto.getUrl();
        if (s3Service != null && 
            "s3".equalsIgnoreCase(foto.getProvedorArmazenamento()) && 
            foto.getChaveArmazenamento() != null && 
            !foto.getChaveArmazenamento().isBlank()) {
            
            try {
                // Gera Signed URL com expiração de 1 hora para fotos de item
                // e 24 horas para fotos de perfil (pode ser ajustado conforme necessário)
                String signedUrl = s3Service.generateSignedUrl(foto.getChaveArmazenamento(), 60);
                if (signedUrl != null) {
                    url = signedUrl;
                } else {
                    // Se falhar ao gerar Signed URL, usa a URL do banco como fallback
                    logger.warn("Falha ao gerar Signed URL para foto ID: {}, usando URL do banco", foto.getId());
                }
            } catch (Exception e) {
                // Em caso de erro, usa a URL do banco como fallback
                logger.error("Erro ao gerar Signed URL para foto ID: {}: {}", foto.getId(), e.getMessage());
            }
        }
        
        return new FotosDTO(
            foto.getId(),
            url,
            foto.getProvedorArmazenamento(),
            foto.getChaveArmazenamento(),
            foto.getNomeArquivoOriginal(),
            foto.getTamanhoArquivoBytes(),
            foto.getDtaCriacao(),
            foto.getFlgInativo(),
            foto.getDtaRemocao()
        );
    }

    public Foto toEntity(FotosDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Foto foto = new Foto();
        foto.setId(dto.getId());
        foto.setUrl(dto.getUrl());
        foto.setProvedorArmazenamento(dto.getProvedorArmazenamento());
        foto.setChaveArmazenamento(dto.getChaveArmazenamento());
        foto.setNomeArquivoOriginal(dto.getNomeArquivoOriginal());
        foto.setTamanhoArquivoBytes(dto.getTamanhoArquivoBytes());
        foto.setDtaCriacao(dto.getDtaCriacao());
        foto.setFlgInativo(dto.getFlgInativo());
        foto.setDtaRemocao(dto.getDtaRemocao());
        
        return foto;
    }

    public FotosListDTO toListDTO(List<Foto> fotos) {
        if (fotos == null) {
            return null;
        }
        
        List<FotosDTO> dtoList = fotos.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        
        return new FotosListDTO(dtoList, dtoList.size());
    }
}
