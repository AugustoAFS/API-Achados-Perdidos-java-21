package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.ItemPerdido.ItemPerdidoCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemPerdido.ItemPerdidoDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemPerdido.ItemPerdidoListDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemPerdido.ItemPerdidoUpdateDTO;
import com.AchadosPerdidos.API.Domain.Entity.ItemPerdido;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemPerdidoModelMapper {

    public ItemPerdidoDTO toDTO(ItemPerdido itemPerdido) {
        if (itemPerdido == null) {
            return null;
        }
        
        return new ItemPerdidoDTO(
            itemPerdido.getId(),
            itemPerdido.getId(), // itemId é o mesmo que id
            itemPerdido.getPerdido_em() != null ? Date.from(itemPerdido.getPerdido_em().atZone(ZoneId.systemDefault()).toInstant()) : null,
            itemPerdido.getDta_Criacao() != null ? Date.from(itemPerdido.getDta_Criacao().atZone(ZoneId.systemDefault()).toInstant()) : null,
            itemPerdido.getFlg_Inativo(),
            itemPerdido.getDta_Remocao() != null ? Date.from(itemPerdido.getDta_Remocao().atZone(ZoneId.systemDefault()).toInstant()) : null
        );
    }

    public ItemPerdido toEntity(ItemPerdidoDTO dto) {
        if (dto == null) {
            return null;
        }
        
        ItemPerdido itemPerdido = new ItemPerdido();
        itemPerdido.setId(dto.getId());
        if (dto.getPerdidoEm() != null) {
            itemPerdido.setPerdido_em(dto.getPerdidoEm().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
        if (dto.getDtaCriacao() != null) {
            itemPerdido.setDta_Criacao(dto.getDtaCriacao().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
        itemPerdido.setFlg_Inativo(dto.getFlgInativo());
        if (dto.getDtaRemocao() != null) {
            itemPerdido.setDta_Remocao(dto.getDtaRemocao().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
        
        return itemPerdido;
    }

    public ItemPerdido fromCreateDTO(ItemPerdidoCreateDTO dto) {
        if (dto == null) {
            return null;
        }
        
        ItemPerdido itemPerdido = new ItemPerdido();
        // ID será definido depois quando o Item for criado (mesmo ID do Item)
        if (dto.getPerdidoEm() != null) {
            itemPerdido.setPerdido_em(dto.getPerdidoEm());
        } else {
            itemPerdido.setPerdido_em(java.time.LocalDateTime.now());
        }
        
        return itemPerdido;
    }

    public void updateFromDTO(ItemPerdido itemPerdido, ItemPerdidoUpdateDTO dto) {
        if (itemPerdido == null || dto == null) {
            return;
        }
        
        if (dto.getPerdidoEm() != null) {
            itemPerdido.setPerdido_em(dto.getPerdidoEm());
        }
        if (dto.getFlgInativo() != null) {
            itemPerdido.setFlg_Inativo(dto.getFlgInativo());
        }
    }

    public ItemPerdidoListDTO toListDTO(List<ItemPerdido> itensPerdidos) {
        if (itensPerdidos == null) {
            return null;
        }
        
        List<ItemPerdidoDTO> dtoList = itensPerdidos.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        
        return new ItemPerdidoListDTO(dtoList, dtoList.size());
    }
}

