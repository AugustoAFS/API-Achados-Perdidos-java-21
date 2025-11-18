package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.ItemAchado.ItemAchadoCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemAchado.ItemAchadoDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemAchado.ItemAchadoListDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemAchado.ItemAchadoUpdateDTO;
import com.AchadosPerdidos.API.Domain.Entity.ItemAchado;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemAchadoModelMapper {

    public ItemAchadoDTO toDTO(ItemAchado itemAchado) {
        if (itemAchado == null) {
            return null;
        }
        
        return new ItemAchadoDTO(
            itemAchado.getId(),
            itemAchado.getId(), // itemId é o mesmo que id
            itemAchado.getEncontrado_em() != null ? Date.from(itemAchado.getEncontrado_em().atZone(ZoneId.systemDefault()).toInstant()) : null,
            itemAchado.getDtaCriacao() != null ? Date.from(itemAchado.getDtaCriacao().atZone(ZoneId.systemDefault()).toInstant()) : null,
            itemAchado.getFlgInativo(),
            itemAchado.getDtaRemocao() != null ? Date.from(itemAchado.getDtaRemocao().atZone(ZoneId.systemDefault()).toInstant()) : null
        );
    }

    public ItemAchado toEntity(ItemAchadoDTO dto) {
        if (dto == null) {
            return null;
        }
        
        ItemAchado itemAchado = new ItemAchado();
        itemAchado.setId(dto.getId());
        if (dto.getEncontradoEm() != null) {
            itemAchado.setEncontrado_em(dto.getEncontradoEm().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
        if (dto.getDtaCriacao() != null) {
            itemAchado.setDtaCriacao(dto.getDtaCriacao().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
        itemAchado.setFlgInativo(dto.getFlgInativo());
        if (dto.getDtaRemocao() != null) {
            itemAchado.setDtaRemocao(dto.getDtaRemocao().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
        
        return itemAchado;
    }

    public ItemAchado fromCreateDTO(ItemAchadoCreateDTO dto) {
        if (dto == null) {
            return null;
        }
        
        ItemAchado itemAchado = new ItemAchado();
        // ID será definido depois quando o Item for criado (mesmo ID do Item)
        if (dto.getEncontradoEm() != null) {
            itemAchado.setEncontrado_em(dto.getEncontradoEm());
        } else {
            itemAchado.setEncontrado_em(java.time.LocalDateTime.now());
        }
        
        return itemAchado;
    }

    public void updateFromDTO(ItemAchado itemAchado, ItemAchadoUpdateDTO dto) {
        if (itemAchado == null || dto == null) {
            return;
        }
        
        if (dto.getEncontradoEm() != null) {
            itemAchado.setEncontrado_em(dto.getEncontradoEm());
        }
        if (dto.getFlgInativo() != null) {
            itemAchado.setFlgInativo(dto.getFlgInativo());
        }
    }

    public ItemAchadoListDTO toListDTO(List<ItemAchado> itensAchados) {
        if (itensAchados == null) {
            return null;
        }
        
        List<ItemAchadoDTO> dtoList = itensAchados.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        
        return new ItemAchadoListDTO(dtoList, dtoList.size());
    }
}

