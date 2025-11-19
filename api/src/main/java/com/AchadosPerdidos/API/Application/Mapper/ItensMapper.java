package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.Item.ItemCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Item.ItemDTO;
import com.AchadosPerdidos.API.Application.DTOs.Item.ItemListDTO;
import com.AchadosPerdidos.API.Application.DTOs.Item.ItemUpdateDTO;
import com.AchadosPerdidos.API.Domain.Entity.Itens;
import com.AchadosPerdidos.API.Domain.Entity.Local;
import com.AchadosPerdidos.API.Domain.Entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItensMapper {

    public ItemDTO toDTO(Itens itens) {
        if (itens == null) {
            return null;
        }
        
        Integer localId = itens.getLocal_id() != null ? itens.getLocal_id().getId() : null;
        Integer usuarioRelatorId = itens.getUsuario_relator_id() != null ? itens.getUsuario_relator_id().getId() : null;

        return new ItemDTO(
            itens.getId(),
            itens.getNome(),
            itens.getDescricao(),
            itens.getTipoItem(),
            localId,
            usuarioRelatorId,
            itens.getDtaCriacao(),
            itens.getFlgInativo(),
            itens.getDtaRemocao()
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
        itens.setLocal_id(toLocal(dto.getLocalId()));
        itens.setUsuario_relator_id(toUsuario(dto.getUsuarioRelatorId()));
        itens.setDtaCriacao(dto.getDtaCriacao());
        itens.setFlgInativo(dto.getFlgInativo());
        itens.setDtaRemocao(dto.getDtaRemocao());
        
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
        itens.setLocal_id(toLocal(dto.getLocalId()));
        itens.setUsuario_relator_id(toUsuario(dto.getUsuarioRelatorId()));
        
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
            itens.setLocal_id(toLocal(dto.getLocalId()));
        }
        if (dto.getUsuarioRelatorId() != null) {
            itens.setUsuario_relator_id(toUsuario(dto.getUsuarioRelatorId()));
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

    private Local toLocal(Integer id) {
        if (id == null) {
            return null;
        }
        Local local = new Local();
        local.setId(id);
        return local;
    }

    private Usuario toUsuario(Integer id) {
        if (id == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setId(id);
        return usuario;
    }
}
