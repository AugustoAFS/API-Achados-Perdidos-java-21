package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.ItensPerdidos.ItensPerdidosDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItensPerdidos.ItensPerdidosListDTO;
import com.AchadosPerdidos.API.Domain.Entity.ItensPerdidos;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItensPerdidosModelMapper {

    public ItensPerdidosDTO toDTO(ItensPerdidos itensPerdidos) {
        if (itensPerdidos == null) {
            return null;
        }
        
        ItensPerdidosDTO dto = new ItensPerdidosDTO();
        dto.setId(itensPerdidos.getId());
        dto.setNome(itensPerdidos.getNome());
        dto.setDescricao(itensPerdidos.getDescricao());
        dto.setEncontradoEm(itensPerdidos.getEncontrado_Em());
        dto.setUsuarioRelatorId(itensPerdidos.getUsuario_Relator_Id());
        dto.setLocalId(itensPerdidos.getLocal_Id());
        dto.setStatusItemId(itensPerdidos.getStatus_Item_Id());
        dto.setDtaCriacao(itensPerdidos.getDta_Criacao());
        dto.setFlgInativo(itensPerdidos.getFlg_Inativo());
        dto.setDtaRemocao(itensPerdidos.getDta_Remocao());
        
        return dto;
    }

    public ItensPerdidos toEntity(ItensPerdidosDTO dto) {
        if (dto == null) {
            return null;
        }
        
        ItensPerdidos itensPerdidos = new ItensPerdidos();
        itensPerdidos.setId(dto.getId());
        itensPerdidos.setNome(dto.getNome());
        itensPerdidos.setDescricao(dto.getDescricao());
        itensPerdidos.setEncontrado_Em(dto.getEncontradoEm());
        itensPerdidos.setUsuario_Relator_Id(dto.getUsuarioRelatorId());
        itensPerdidos.setLocal_Id(dto.getLocalId());
        itensPerdidos.setStatus_Item_Id(dto.getStatusItemId());
        itensPerdidos.setDta_Criacao(dto.getDtaCriacao());
        itensPerdidos.setFlg_Inativo(dto.getFlgInativo());
        itensPerdidos.setDta_Remocao(dto.getDtaRemocao());
        
        return itensPerdidos;
    }

    public ItensPerdidosListDTO toListDTO(List<ItensPerdidos> itensPerdidos) {
        if (itensPerdidos == null) {
            return null;
        }
        
        List<ItensPerdidosDTO> dtoList = itensPerdidos.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        
        ItensPerdidosListDTO listDTO = new ItensPerdidosListDTO();
        listDTO.setItensPerdidos(dtoList);
        listDTO.setTotalCount(dtoList.size());
        
        return listDTO;
    }
}

