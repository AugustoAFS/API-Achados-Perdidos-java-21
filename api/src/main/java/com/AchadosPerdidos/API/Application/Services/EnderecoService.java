package com.AchadosPerdidos.API.Application.Services;

import com.AchadosPerdidos.API.Application.DTOs.Endereco.EnderecoDTO;
import com.AchadosPerdidos.API.Application.DTOs.Endereco.EnderecoCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Endereco.EnderecoUpdateDTO;
import com.AchadosPerdidos.API.Application.Mapper.EnderecoMapper;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IEnderecoService;
import com.AchadosPerdidos.API.Domain.Entity.Cidade;
import com.AchadosPerdidos.API.Domain.Entity.Endereco;
import com.AchadosPerdidos.API.Domain.Repository.CidadeRepository;
import com.AchadosPerdidos.API.Domain.Repository.EnderecoRepository;
import com.AchadosPerdidos.API.Exeptions.BusinessException;
import com.AchadosPerdidos.API.Exeptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnderecoService implements IEnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EnderecoMapper enderecoMapper;

    @Override
    @Cacheable(value = "enderecos", key = "'all'")
    public List<EnderecoDTO> getAllEnderecos() {
        List<Endereco> enderecos = enderecoRepository.findAll();
        return enderecos.stream()
                .map(enderecoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "enderecos", key = "#id")
    public EnderecoDTO getEnderecoById(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID do endereço deve ser válido");
        }
        
        Endereco endereco = getEnderecoOrThrow(id);
        return enderecoMapper.toDTO(endereco);
    }

    @Override
    @CacheEvict(value = "enderecos", allEntries = true)
    public EnderecoDTO createEndereco(EnderecoCreateDTO createDTO) {
        if (createDTO == null) {
            throw new IllegalArgumentException("Dados do endereço não podem ser nulos");
        }
        
        if (!StringUtils.hasText(createDTO.getLogradouro())) {
            throw new BusinessException("Endereço", "criar", "Logradouro é obrigatório");
        }
        
        if (!StringUtils.hasText(createDTO.getNumero())) {
            throw new BusinessException("Endereço", "criar", "Número é obrigatório");
        }
        
        if (createDTO.getCidadeId() == null || createDTO.getCidadeId() <= 0) {
            throw new BusinessException("Endereço", "criar", "ID da cidade é obrigatório e deve ser válido");
        }
        
        // Regra de negócio: Verificar se a cidade existe
        Cidade cidade = validarCidadeExiste(createDTO.getCidadeId());
        
        // Regra de negócio: Validar formato do CEP (se fornecido) - 8 dígitos sem traço
        if (createDTO.getCep() != null && !createDTO.getCep().matches("\\d{8}")) {
            throw new BusinessException("Endereço", "criar", "CEP deve conter exatamente 8 dígitos numéricos");
        }
        
        Endereco endereco = new Endereco();
        endereco.setLogradouro(createDTO.getLogradouro());
        endereco.setNumero(createDTO.getNumero());
        endereco.setComplemento(createDTO.getComplemento());
        endereco.setBairro(createDTO.getBairro());
        endereco.setCep(createDTO.getCep());
        endereco.setCidadeId(cidade);
        endereco.setDtaCriacao(LocalDateTime.now());
        endereco.setFlgInativo(false);
        
        Endereco savedEndereco = enderecoRepository.save(endereco);
        return enderecoMapper.toDTO(savedEndereco);
    }

    @Override
    @CacheEvict(value = "enderecos", allEntries = true)
    public EnderecoDTO updateEndereco(Integer id, EnderecoUpdateDTO updateDTO) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID do endereço deve ser válido");
        }
        
        if (updateDTO == null) {
            throw new IllegalArgumentException("Dados de atualização não podem ser nulos");
        }
        
        Endereco existingEndereco = getEnderecoOrThrow(id);
        
        if (updateDTO.getLogradouro() != null) {
            if (!StringUtils.hasText(updateDTO.getLogradouro())) {
                throw new BusinessException("Endereço", "atualizar", "Logradouro não pode ser vazio");
            }
            existingEndereco.setLogradouro(updateDTO.getLogradouro());
        }
        if (updateDTO.getNumero() != null) {
            if (!StringUtils.hasText(updateDTO.getNumero())) {
                throw new BusinessException("Endereço", "atualizar", "Número não pode ser vazio");
            }
            existingEndereco.setNumero(updateDTO.getNumero());
        }
        if (updateDTO.getComplemento() != null) {
            existingEndereco.setComplemento(updateDTO.getComplemento());
        }
        if (updateDTO.getBairro() != null) {
            existingEndereco.setBairro(updateDTO.getBairro());
        }
        if (updateDTO.getCep() != null) {
            if (!updateDTO.getCep().matches("\\d{8}")) {
                throw new BusinessException("Endereço", "atualizar", "CEP deve conter exatamente 8 dígitos numéricos");
            }
            existingEndereco.setCep(updateDTO.getCep());
        }
        if (updateDTO.getCidadeId() != null) {
            if (updateDTO.getCidadeId() <= 0) {
                throw new BusinessException("Endereço", "atualizar", "ID da cidade deve ser válido");
            }
            // Verificar se a nova cidade existe
            existingEndereco.setCidadeId(validarCidadeExiste(updateDTO.getCidadeId()));
        }
        if (updateDTO.getFlgInativo() != null) {
            existingEndereco.setFlgInativo(updateDTO.getFlgInativo());
        }
        
        Endereco updatedEndereco = enderecoRepository.save(existingEndereco);
        return enderecoMapper.toDTO(updatedEndereco);
    }

    @Override
    @CacheEvict(value = "enderecos", allEntries = true)
    public EnderecoDTO deleteEndereco(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID do endereço deve ser válido");
        }
        
        Endereco endereco = getEnderecoOrThrow(id);
        
        // Soft delete: Marcar como inativo ao invés de deletar fisicamente
        if (Boolean.TRUE.equals(endereco.getFlgInativo())) {
            throw new BusinessException("Endereço", "deletar", "O endereço já está inativo");
        }
        
        endereco.setFlgInativo(true);
        // Endereco entity não possui setDtaRemocao, apenas getter
        
        Endereco updatedEndereco = enderecoRepository.save(endereco);
        return enderecoMapper.toDTO(updatedEndereco);
    }

    @Override
    @Cacheable(value = "enderecos", key = "'cidade_' + #cidadeId")
    public List<EnderecoDTO> getEnderecosByCidade(Integer cidadeId) {
        if (cidadeId == null || cidadeId <= 0) {
            throw new IllegalArgumentException("ID da cidade deve ser válido");
        }
        
        List<Endereco> enderecos = enderecoRepository.findByCidade(cidadeId);
        return enderecos.stream()
                .map(enderecoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "enderecos", key = "'active'")
    public List<EnderecoDTO> getActiveEnderecos() {
        List<Endereco> activeEnderecos = enderecoRepository.findActive();
        return activeEnderecos.stream()
                .map(enderecoMapper::toDTO)
                .collect(Collectors.toList());
    }

    private Endereco getEnderecoOrThrow(Integer id) {
        return enderecoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço", "ID", id));
    }

    private Cidade validarCidadeExiste(Integer cidadeId) {
        return cidadeRepository.findById(cidadeId)
                .orElseThrow(() -> new ResourceNotFoundException("Cidade", "ID", cidadeId));
    }
}

