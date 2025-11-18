package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.ItemDevolvido.ItemDevolvidoCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemDevolvido.ItemDevolvidoDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemDevolvido.ItemDevolvidoListDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemDevolvido.ItemDevolvidoUpdateDTO;
import com.AchadosPerdidos.API.Domain.Entity.ItemDevolvido;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemDevolvidoModelMapper {

    public ItemDevolvidoDTO toDTO(ItemDevolvido itemDevolvido) {
        if (itemDevolvido == null) {
            return null;
        }
        
        return new ItemDevolvidoDTO(
            itemDevolvido.getId(),
            itemDevolvido.getItem_id(),
            itemDevolvido.getDetalhes_devolucao(),
            itemDevolvido.getUsuario_devolvedor_id(),
            itemDevolvido.getUsuario_achou_id(),
            itemDevolvido.getDtaCriacao() != null ? Date.from(itemDevolvido.getDtaCriacao().atZone(ZoneId.systemDefault()).toInstant()) : null,
            itemDevolvido.getFlgInativo(),
            itemDevolvido.getDtaRemocao() != null ? Date.from(itemDevolvido.getDtaRemocao().atZone(ZoneId.systemDefault()).toInstant()) : null
        );
    }

    public ItemDevolvido toEntity(ItemDevolvidoDTO dto) {
        if (dto == null) {
            return null;
        }
        
        ItemDevolvido itemDevolvido = new ItemDevolvido();
        itemDevolvido.setId(dto.getId());
        itemDevolvido.setItem_id(dto.getItemId());
        itemDevolvido.setDetalhes_devolucao(dto.getDetalhesDevolucao());
        itemDevolvido.setUsuario_devolvedor_id(dto.getUsuarioDevolvedorId());
        itemDevolvido.setUsuario_achou_id(dto.getUsuarioAchouId());
        if (dto.getDtaCriacao() != null) {
            itemDevolvido.setDtaCriacao(dto.getDtaCriacao().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
        itemDevolvido.setFlgInativo(dto.getFlgInativo());
        if (dto.getDtaRemocao() != null) {
            itemDevolvido.setDtaRemocao(dto.getDtaRemocao().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
        
        return itemDevolvido;
    }

    public ItemDevolvido fromCreateDTO(ItemDevolvidoCreateDTO dto) {
        if (dto == null) {
            return null;
        }
        
        ItemDevolvido itemDevolvido = new ItemDevolvido();
        itemDevolvido.setItem_id(dto.getItemId());
        itemDevolvido.setDetalhes_devolucao(dto.getDetalhesDevolucao());
        itemDevolvido.setUsuario_devolvedor_id(dto.getUsuarioDevolvedorId());
        itemDevolvido.setUsuario_achou_id(dto.getUsuarioAchouId());
        
        return itemDevolvido;
    }

    public void updateFromDTO(ItemDevolvido itemDevolvido, ItemDevolvidoUpdateDTO dto) {
        if (itemDevolvido == null || dto == null) {
            return;
        }
        
        if (dto.getDetalhesDevolucao() != null) {
            itemDevolvido.setDetalhes_devolucao(dto.getDetalhesDevolucao());
        }
        if (dto.getUsuarioDevolvedorId() != null) {
            itemDevolvido.setUsuario_devolvedor_id(dto.getUsuarioDevolvedorId());
        }
        if (dto.getUsuarioAchouId() != null) {
            itemDevolvido.setUsuario_achou_id(dto.getUsuarioAchouId());
        }
        if (dto.getFlgInativo() != null) {
            itemDevolvido.setFlgInativo(dto.getFlgInativo());
        }
    }

    public ItemDevolvidoListDTO toListDTO(List<ItemDevolvido> itensDevolvidos) {
        if (itensDevolvidos == null) {
            return null;
        }
        
        List<ItemDevolvidoDTO> dtoList = itensDevolvidos.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        
        return new ItemDevolvidoListDTO(dtoList, dtoList.size());
    }
}

