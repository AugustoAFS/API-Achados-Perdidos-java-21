package com.AchadosPerdidos.API.Application.Services;

import com.AchadosPerdidos.API.Application.DTOs.Empresa.EmpresaDTO;
import com.AchadosPerdidos.API.Application.DTOs.Empresa.EmpresaListDTO;
import com.AchadosPerdidos.API.Application.Mapper.EmpresaModelMapper;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IEmpresaService;
import com.AchadosPerdidos.API.Domain.Entity.Empresa;
import com.AchadosPerdidos.API.Domain.Repository.EmpresaRepository;
import com.AchadosPerdidos.API.Domain.Repository.EnderecoRepository;
import com.AchadosPerdidos.API.Domain.Validator.EntityValidator;
import com.AchadosPerdidos.API.Exeptions.BusinessException;
import com.AchadosPerdidos.API.Exeptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class EmpresaService implements IEmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private EmpresaModelMapper empresaModelMapper;
    
    @Autowired
    private EnderecoRepository enderecoRepository;

    @Override
    @Cacheable(value = "empresas", key = "'all'")
    public EmpresaListDTO getAllEmpresas() {
        List<Empresa> empresas = empresaRepository.findAll();
        return empresaModelMapper.toListDTO(empresas);
    }

    @Override
    @Cacheable(value = "empresas", key = "#id")
    public EmpresaDTO getEmpresaById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID da empresa deve ser válido");
        }
        
        Empresa empresa = empresaRepository.findById(id);
        if (empresa == null) {
            throw new ResourceNotFoundException("Empresa não encontrada com ID: " + id);
        }
        return empresaModelMapper.toDTO(empresa);
    }

    @Override
    @CacheEvict(value = "empresas", allEntries = true)
    public EmpresaDTO createEmpresa(EmpresaDTO empresaDTO) {
        if (empresaDTO == null) {
            throw new IllegalArgumentException("Dados da empresa não podem ser nulos");
        }
        
        validarEmpresaParaCriacao(empresaDTO);
        
        Empresa empresa = empresaModelMapper.toEntity(empresaDTO);
        empresa.setDtaCriacao(new Date());
        empresa.setFlgInativo(false);
        
        Empresa savedEmpresa = empresaRepository.save(empresa);
        return empresaModelMapper.toDTO(savedEmpresa);
    }

    @Override
    @CacheEvict(value = "empresas", allEntries = true)
    public EmpresaDTO updateEmpresa(int id, EmpresaDTO empresaDTO) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID da empresa deve ser válido");
        }
        
        if (empresaDTO == null) {
            throw new IllegalArgumentException("Dados de atualização não podem ser nulos");
        }
        
        Empresa existingEmpresa = empresaRepository.findById(id);
        if (existingEmpresa == null) {
            throw new ResourceNotFoundException("Empresa não encontrada com ID: " + id);
        }
        
        if (existingEmpresa.getDtaRemocao() != null) {
            throw new BusinessException("Não é possível atualizar uma empresa que já foi removida");
        }
        
        if (StringUtils.hasText(empresaDTO.getCnpj()) && 
            !empresaDTO.getCnpj().equals(existingEmpresa.getCnpj())) {
            validarCnpjUnico(empresaDTO.getCnpj(), id);
        }
        
        if (empresaDTO.getEnderecoId() != null && empresaDTO.getEnderecoId() > 0) {
            validarEnderecoExiste(empresaDTO.getEnderecoId());
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
    @CacheEvict(value = "empresas", allEntries = true)
    public boolean deleteEmpresa(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID da empresa deve ser válido");
        }
        
        Empresa empresa = empresaRepository.findById(id);
        if (empresa == null) {
            throw new ResourceNotFoundException("Empresa não encontrada com ID: " + id);
        }
        
        if (Boolean.TRUE.equals(empresa.getFlgInativo())) {
            throw new BusinessException("A empresa já está inativa");
        }
        
        empresa.setFlgInativo(true);
        empresa.setDtaRemocao(new Date());
        empresaRepository.save(empresa);
        
        return true;
    }

    @Override
    @Cacheable(value = "empresas", key = "'active'")
    public EmpresaListDTO getActiveEmpresas() {
        List<Empresa> activeEmpresas = empresaRepository.findActive();
        return empresaModelMapper.toListDTO(activeEmpresas);
    }
    
    private void validarEmpresaParaCriacao(EmpresaDTO empresaDTO) {
        if (!StringUtils.hasText(empresaDTO.getNome())) {
            throw new BusinessException("Empresa", "criar", "Nome é obrigatório");
        }
        
        if (empresaDTO.getNome().length() < 3) {
            throw new BusinessException("Empresa", "criar", "Nome deve ter no mínimo 3 caracteres");
        }
        
        if (empresaDTO.getNome().length() > 255) {
            throw new BusinessException("Empresa", "criar", "Nome deve ter no máximo 255 caracteres");
        }
        
        if (!StringUtils.hasText(empresaDTO.getNomeFantasia())) {
            throw new BusinessException("Empresa", "criar", "Nome fantasia é obrigatório");
        }
        
        if (empresaDTO.getNomeFantasia().length() < 3) {
            throw new BusinessException("Empresa", "criar", "Nome fantasia deve ter no mínimo 3 caracteres");
        }
        
        if (StringUtils.hasText(empresaDTO.getCnpj())) {
            EntityValidator.validateCnpjFormat(empresaDTO.getCnpj());
            
            if (!EntityValidator.isValidCnpj(empresaDTO.getCnpj())) {
                throw new BusinessException("CNPJ inválido");
            }
            
            validarCnpjUnico(empresaDTO.getCnpj(), null);
        }
        
        if (empresaDTO.getEnderecoId() != null && empresaDTO.getEnderecoId() > 0) {
            validarEnderecoExiste(empresaDTO.getEnderecoId());
        }
    }
    
    private void validarCnpjUnico(String cnpj, Integer empresaIdExcluir) {
        List<Empresa> empresasComMesmoCnpj = empresaRepository.findAll().stream()
            .filter(e -> cnpj.equals(e.getCnpj()))
            .filter(e -> empresaIdExcluir == null || !e.getId().equals(empresaIdExcluir))
            .toList();
        
        if (!empresasComMesmoCnpj.isEmpty()) {
            throw new BusinessException("Já existe uma empresa cadastrada com o CNPJ informado");
        }
    }
    
    private void validarEnderecoExiste(Integer enderecoId) {
        if (enderecoRepository.findById(enderecoId) == null) {
            throw new ResourceNotFoundException("Endereço", "ID", enderecoId);
        }
    }
}
