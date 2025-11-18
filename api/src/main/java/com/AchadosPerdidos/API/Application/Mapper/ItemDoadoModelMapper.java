package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.ItemDoado.ItemDoadoCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemDoado.ItemDoadoDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemDoado.ItemDoadoListDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemDoado.ItemDoadoUpdateDTO;
import com.AchadosPerdidos.API.Domain.Entity.ItemDoado;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemDoadoModelMapper {

    public ItemDoadoDTO toDTO(ItemDoado itemDoado) {
        if (itemDoado == null) {
            return null;
        }
        
        return new ItemDoadoDTO(
            itemDoado.getId(),
            itemDoado.getId(), // itemId é o mesmo que id
            itemDoado.getDoado_em() != null ? Date.from(itemDoado.getDoado_em().atZone(ZoneId.systemDefault()).toInstant()) : null,
            itemDoado.getDta_Criacao() != null ? Date.from(itemDoado.getDta_Criacao().atZone(ZoneId.systemDefault()).toInstant()) : null,
            itemDoado.getFlg_Inativo(),
            itemDoado.getDta_Remocao() != null ? Date.from(itemDoado.getDta_Remocao().atZone(ZoneId.systemDefault()).toInstant()) : null
        );
    }

    public ItemDoado toEntity(ItemDoadoDTO dto) {
        if (dto == null) {
            return null;
        }
        
        ItemDoado itemDoado = new ItemDoado();
        itemDoado.setId(dto.getId());
        if (dto.getDoadoEm() != null) {
            itemDoado.setDoado_em(dto.getDoadoEm().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
        if (dto.getDtaCriacao() != null) {
            itemDoado.setDta_Criacao(dto.getDtaCriacao().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
        itemDoado.setFlg_Inativo(dto.getFlgInativo());
        if (dto.getDtaRemocao() != null) {
            itemDoado.setDta_Remocao(dto.getDtaRemocao().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
        
        return itemDoado;
    }

    public ItemDoado fromCreateDTO(ItemDoadoCreateDTO dto) {
        if (dto == null) {
            return null;
        }
        
        ItemDoado itemDoado = new ItemDoado();
        itemDoado.setId(dto.getItemId());
        if (dto.getDoadoEm() != null) {
            itemDoado.setDoado_em(dto.getDoadoEm());
        }
        
        return itemDoado;
    }

    public void updateFromDTO(ItemDoado itemDoado, ItemDoadoUpdateDTO dto) {
        if (itemDoado == null || dto == null) {
            return;
        }
        
        if (dto.getDoadoEm() != null) {
            itemDoado.setDoado_em(dto.getDoadoEm());
        }
        if (dto.getFlgInativo() != null) {
            itemDoado.setFlg_Inativo(dto.getFlgInativo());
        }
    }

    public ItemDoadoListDTO toListDTO(List<ItemDoado> itensDoados) {
        if (itensDoados == null) {
            return null;
        }
        
        List<ItemDoadoDTO> dtoList = itensDoados.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        
        return new ItemDoadoListDTO(dtoList, dtoList.size());
    }
}

