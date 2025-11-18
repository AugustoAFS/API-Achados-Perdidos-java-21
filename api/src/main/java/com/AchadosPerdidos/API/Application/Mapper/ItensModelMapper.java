package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.Item.ItemCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Item.ItemDTO;
import com.AchadosPerdidos.API.Application.DTOs.Item.ItemListDTO;
import com.AchadosPerdidos.API.Application.DTOs.Item.ItemUpdateDTO;
import com.AchadosPerdidos.API.Domain.Entity.Itens;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItensModelMapper {

    public ItemDTO toDTO(Itens itens) {
        if (itens == null) {
            return null;
        }
        
        return new ItemDTO(
            itens.getId(),
            itens.getNome(),
            itens.getDescricao(),
            itens.getTipoItem(),
            itens.getLocal_id(),
            itens.getUsuario_relator_id(),
            itens.getDtaCriacao() != null ? Date.from(itens.getDtaCriacao().atZone(ZoneId.systemDefault()).toInstant()) : null,
            itens.getFlgInativo(),
            itens.getDtaRemocao() != null ? Date.from(itens.getDtaRemocao().atZone(ZoneId.systemDefault()).toInstant()) : null
        );
    }

    public Itens toEntity(ItemDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Itens itens = new Itens();
        itens.setId(dto.getId());
        itens.setNome(dto.getNome());
        itens.setDescricao(dto.getDescricao());
        itens.setTipoItem(dto.getTipoItem());
        itens.setLocal_id(dto.getLocalId());
        itens.setUsuario_relator_id(dto.getUsuarioRelatorId());
        if (dto.getDtaCriacao() != null) {
            itens.setDtaCriacao(dto.getDtaCriacao().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
        itens.setFlgInativo(dto.getFlgInativo());
        if (dto.getDtaRemocao() != null) {
            itens.setDtaRemocao(dto.getDtaRemocao().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
        
        return itens;
    }

    public Itens fromCreateDTO(ItemCreateDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Itens itens = new Itens();
        itens.setNome(dto.getNome());
        itens.setDescricao(dto.getDescricao());
        itens.setTipoItem(dto.getTipoItem());
        itens.setLocal_id(dto.getLocalId());
        itens.setUsuario_relator_id(dto.getUsuarioRelatorId());
        
        return itens;
    }

    public void updateFromDTO(Itens itens, ItemUpdateDTO dto) {
        if (itens == null || dto == null) {
            return;
        }
        
        if (dto.getNome() != null) {
            itens.setNome(dto.getNome());
        }
        if (dto.getDescricao() != null) {
            itens.setDescricao(dto.getDescricao());
        }
        if (dto.getTipoItem() != null) {
            itens.setTipoItem(dto.getTipoItem());
        }
        if (dto.getLocalId() != null) {
            itens.setLocal_id(dto.getLocalId());
        }
        if (dto.getUsuarioRelatorId() != null) {
            itens.setUsuario_relator_id(dto.getUsuarioRelatorId());
        }
        if (dto.getFlgInativo() != null) {
            itens.setFlgInativo(dto.getFlgInativo());
        }
    }

    public ItemListDTO toListDTO(List<Itens> itens) {
        if (itens == null) {
            return null;
        }
        
        List<ItemDTO> dtoList = itens.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        
        return new ItemListDTO(dtoList, dtoList.size());
    }
}
