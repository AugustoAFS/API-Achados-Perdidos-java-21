package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.Empresa.EmpresaDTO;
import com.AchadosPerdidos.API.Application.DTOs.Empresa.EmpresaListDTO;
import com.AchadosPerdidos.API.Domain.Entity.Empresa;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmpresaModelMapper {

    public EmpresaDTO toDTO(Empresa empresa) {
        if (empresa == null) {
            return null;
        }
        
        return new EmpresaDTO(
            empresa.getId(),
            empresa.getNome(),
            empresa.getNomeFantasia(),
            empresa.getCnpj(),
            empresa.getEnderecoId(),
            empresa.getDtaCriacao(),
            empresa.getFlgInativo(),
            empresa.getDtaRemocao()
        );
    }

    public Empresa toEntity(EmpresaDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Empresa empresa = new Empresa();
        empresa.setId(dto.getId());
        empresa.setNome(dto.getNome());
        empresa.setNomeFantasia(dto.getNomeFantasia());
        empresa.setCnpj(dto.getCnpj());
        empresa.setEnderecoId(dto.getEnderecoId());
        empresa.setDtaCriacao(dto.getDtaCriacao());
        empresa.setFlgInativo(dto.getFlgInativo());
        empresa.setDtaRemocao(dto.getDtaRemocao());
        
        return empresa;
    }

    public EmpresaListDTO toListDTO(List<Empresa> empresas) {
        if (empresas == null) {
            return null;
        }
        
        List<EmpresaDTO> dtoList = empresas.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        
        return new EmpresaListDTO(dtoList, dtoList.size());
    }
}
