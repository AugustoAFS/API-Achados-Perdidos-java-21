package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.FotoItem.FotoItemCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.FotoItem.FotoItemDTO;
import com.AchadosPerdidos.API.Application.DTOs.FotoItem.FotoItemListDTO;
import com.AchadosPerdidos.API.Application.DTOs.FotoItem.FotoItemUpdateDTO;
import com.AchadosPerdidos.API.Domain.Entity.FotoItem;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FotoItemModelMapper {

    public FotoItemDTO toDTO(FotoItem fotoItem) {
        if (fotoItem == null) {
            return null;
        }
        
        return new FotoItemDTO(
            fotoItem.getId(),
            fotoItem.getItemId(),
            fotoItem.getFotoId(),
            fotoItem.getDtaCriacao() != null ? Date.from(fotoItem.getDtaCriacao().atZone(ZoneId.systemDefault()).toInstant()) : null,
            fotoItem.getFlgInativo(),
            fotoItem.getDtaRemocao() != null ? Date.from(fotoItem.getDtaRemocao().atZone(ZoneId.systemDefault()).toInstant()) : null
        );
    }

    public FotoItem toEntity(FotoItemDTO dto) {
        if (dto == null) {
            return null;
        }
        
        FotoItem fotoItem = new FotoItem();
        fotoItem.setId(dto.getId());
        fotoItem.setItemId(dto.getItemId());
        fotoItem.setFotoId(dto.getFotoId());
        if (dto.getDtaCriacao() != null) {
            fotoItem.setDtaCriacao(dto.getDtaCriacao().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
        fotoItem.setFlgInativo(dto.getFlgInativo());
        // FotoItem não tem setDtaRemocao na entidade, apenas getter
        
        return fotoItem;
    }

    public FotoItem fromCreateDTO(FotoItemCreateDTO dto) {
        if (dto == null) {
            return null;
        }
        
        FotoItem fotoItem = new FotoItem();
        fotoItem.setItemId(dto.getItemId());
        fotoItem.setFotoId(dto.getFotoId());
        
        return fotoItem;
    }

    public void updateFromDTO(FotoItem fotoItem, FotoItemUpdateDTO dto) {
        if (fotoItem == null || dto == null) {
            return;
        }
        
        if (dto.getItemId() != null) {
            fotoItem.setItemId(dto.getItemId());
        }
        if (dto.getFotoId() != null) {
            fotoItem.setFotoId(dto.getFotoId());
        }
        if (dto.getFlgInativo() != null) {
            fotoItem.setFlgInativo(dto.getFlgInativo());
        }
    }

    public FotoItemListDTO toListDTO(List<FotoItem> fotoItens) {
        if (fotoItens == null) {
            return null;
        }
        
        List<FotoItemDTO> dtoList = fotoItens.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        
        return new FotoItemListDTO(dtoList, dtoList.size());
    }
}

