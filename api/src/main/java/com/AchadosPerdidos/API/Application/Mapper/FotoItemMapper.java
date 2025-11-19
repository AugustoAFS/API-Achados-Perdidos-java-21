package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.FotoItem.FotoItemCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.FotoItem.FotoItemDTO;
import com.AchadosPerdidos.API.Application.DTOs.FotoItem.FotoItemListDTO;
import com.AchadosPerdidos.API.Application.DTOs.FotoItem.FotoItemUpdateDTO;
import com.AchadosPerdidos.API.Domain.Entity.Foto;
import com.AchadosPerdidos.API.Domain.Entity.FotoItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FotoItemMapper {

    public FotoItemDTO toDTO(FotoItem fotoItem) {
        if (fotoItem == null) {
            return null;
        }
        
        Integer itemId = fotoItem.getItemId();
        Integer fotoId = fotoItem.getFotoId() != null ? fotoItem.getFotoId().getId() : null;

        return new FotoItemDTO(
            fotoItem.getId(),
            itemId,
            fotoId,
            fotoItem.getDtaCriacao(),
            fotoItem.getFlgInativo(),
            fotoItem.getDtaRemocao()
        );
    }

    public FotoItem toEntity(FotoItemDTO dto) {
        if (dto == null) {
            return null;
        }
        
        FotoItem fotoItem = new FotoItem();
        fotoItem.setId(dto.getId());
        fotoItem.setItemId(dto.getItemId());
        fotoItem.setFotoId(toFoto(dto.getFotoId()));
        fotoItem.setDtaCriacao(dto.getDtaCriacao());
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
        fotoItem.setFotoId(toFoto(dto.getFotoId()));
        
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
            fotoItem.setFotoId(toFoto(dto.getFotoId()));
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

    private Foto toFoto(Integer id) {
        if (id == null) {
            return null;
        }
        Foto foto = new Foto();
        foto.setId(id);
        return foto;
    }
}

