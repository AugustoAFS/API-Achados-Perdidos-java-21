package com.AchadosPerdidos.API.Application.Services;

import com.AchadosPerdidos.API.Application.DTOs.Empresa.EmpresaDTO;
import com.AchadosPerdidos.API.Application.DTOs.Empresa.EmpresaListDTO;
import com.AchadosPerdidos.API.Application.Mapper.EmpresaModelMapper;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IEmpresaService;
import com.AchadosPerdidos.API.Domain.Entity.Empresa;
import com.AchadosPerdidos.API.Domain.Repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EmpresaService implements IEmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private EmpresaModelMapper empresaModelMapper;

    @Override
    public EmpresaListDTO getAllEmpresas() {
        List<Empresa> empresas = empresaRepository.findAll();
        return empresaModelMapper.toListDTO(empresas);
    }

    @Override
    public EmpresaDTO getEmpresaById(int id) {
        Empresa empresa = empresaRepository.findById(id);
        return empresaModelMapper.toDTO(empresa);
    }

    @Override
    public EmpresaDTO createEmpresa(EmpresaDTO empresaDTO) {
        Empresa empresa = empresaModelMapper.toEntity(empresaDTO);
        empresa.setDtaCriacao(new Date());
        empresa.setFlgInativo(false);
        
        Empresa savedEmpresa = empresaRepository.save(empresa);
        return empresaModelMapper.toDTO(savedEmpresa);
    }

    @Override
    public EmpresaDTO updateEmpresa(int id, EmpresaDTO empresaDTO) {
        Empresa existingEmpresa = empresaRepository.findById(id);
        if (existingEmpresa == null) {
            return null;
        }
        
        existingEmpresa.setNome(empresaDTO.getNome());
        existingEmpresa.setNomeFantasia(empresaDTO.getNomeFantasia());
        existingEmpresa.setCnpj(empresaDTO.getCnpj());
        existingEmpresa.setEnderecoId(empresaDTO.getEnderecoId());
        existingEmpresa.setFlgInativo(empresaDTO.getFlgInativo());
        existingEmpresa.setDtaRemocao(empresaDTO.getDtaRemocao());
        
        Empresa updatedEmpresa = empresaRepository.save(existingEmpresa);
        return empresaModelMapper.toDTO(updatedEmpresa);
    }

    @Override
    public boolean deleteEmpresa(int id) {
        Empresa empresa = empresaRepository.findById(id);
        if (empresa == null) {
            return false;
        }
        
        return empresaRepository.deleteById(id);
    }

    @Override
    public EmpresaListDTO getActiveEmpresas() {
        List<Empresa> activeEmpresas = empresaRepository.findActive();
        return empresaModelMapper.toListDTO(activeEmpresas);
    }

    @Override
    public EmpresaListDTO getEmpresasByCountry(String paisSede) {
        List<Empresa> empresas = empresaRepository.findByCountry(paisSede);
        return empresaModelMapper.toListDTO(empresas);
    }
}
