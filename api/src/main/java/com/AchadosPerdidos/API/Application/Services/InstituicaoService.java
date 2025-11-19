package com.AchadosPerdidos.API.Application.Services;

import com.AchadosPerdidos.API.Application.DTOs.Instituicao.InstituicaoDTO;
import com.AchadosPerdidos.API.Application.DTOs.Instituicao.InstituicaoListDTO;
import com.AchadosPerdidos.API.Application.DTOs.Instituicao.InstituicaoCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Instituicao.InstituicaoUpdateDTO;
import com.AchadosPerdidos.API.Application.Mapper.InstituicaoMapper;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IInstituicaoService;
import com.AchadosPerdidos.API.Domain.Entity.Instituicoes;
import com.AchadosPerdidos.API.Domain.Repository.InstituicaoRepository;
import com.AchadosPerdidos.API.Domain.Validator.EntityValidator;
import com.AchadosPerdidos.API.Exeptions.BusinessException;
import com.AchadosPerdidos.API.Exeptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InstituicaoService implements IInstituicaoService {

    @Autowired
    private InstituicaoRepository instituicaoRepository;

    @Autowired
    private InstituicaoMapper instituicaoMapper;

    @Override
    @Cacheable(value = "instituicoes", key = "'all'")
    public InstituicaoListDTO getAllInstituicoes() {
        List<Instituicoes> instituicoes = instituicaoRepository.findAll();
        return instituicaoMapper.toListDTO(instituicoes);
    }

    @Override
    @Cacheable(value = "instituicoes", key = "#id")
    public InstituicaoDTO getInstituicaoById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID da instituição deve ser válido");
        }
        
        Instituicoes instituicao = getInstituicaoOrThrow(id);
        return instituicaoMapper.toDTO(instituicao);
    }

    @Override
    @CacheEvict(value = "instituicoes", allEntries = true)
    public InstituicaoDTO createInstituicao(InstituicaoDTO instituicaoDTO) {
        if (instituicaoDTO == null) {
            throw new IllegalArgumentException("Dados da instituição não podem ser nulos");
        }
        
        validarInstituicaoParaCriacao(
            instituicaoDTO.getNome(), 
            instituicaoDTO.getCodigo(), 
            instituicaoDTO.getTipo(), 
            instituicaoDTO.getCnpj()
        );
        
        Instituicoes instituicao = instituicaoMapper.toEntity(instituicaoDTO);
        instituicao.setDtaCriacao(LocalDateTime.now());
        instituicao.setFlgInativo(false);
        
        Instituicoes savedInstituicao = instituicaoRepository.save(instituicao);
        return instituicaoMapper.toDTO(savedInstituicao);
    }

    @Override
    @CacheEvict(value = "instituicoes", allEntries = true)
    public InstituicaoDTO createInstituicaoFromDTO(InstituicaoCreateDTO createDTO) {
        if (createDTO == null) {
            throw new IllegalArgumentException("Dados da instituição não podem ser nulos");
        }
        
        validarInstituicaoParaCriacao(
            createDTO.getNome(), 
            createDTO.getCodigo(), 
            createDTO.getTipo(), 
            createDTO.getCnpj()
        );
        
        Instituicoes instituicao = new Instituicoes();
        instituicao.setNome(createDTO.getNome());
        instituicao.setCodigo(createDTO.getCodigo());
        instituicao.setTipo(createDTO.getTipo());
        instituicao.setCnpj(createDTO.getCnpj());
        instituicao.setDtaCriacao(LocalDateTime.now());
        instituicao.setFlgInativo(false);
        
        Instituicoes savedInstituicao = instituicaoRepository.save(instituicao);
        return instituicaoMapper.toDTO(savedInstituicao);
    }

    @Override
    @CacheEvict(value = "instituicoes", allEntries = true)
    public InstituicaoDTO updateInstituicao(int id, InstituicaoDTO instituicaoDTO) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID da instituição deve ser válido");
        }
        
        if (instituicaoDTO == null) {
            throw new IllegalArgumentException("Dados de atualização não podem ser nulos");
        }
        
        Instituicoes existingInstituicao = getInstituicaoOrThrow(id);
        
        if (existingInstituicao.getDtaRemocao() != null) {
            throw new BusinessException("Não é possível atualizar uma instituição que já foi removida");
        }
        
        if (StringUtils.hasText(instituicaoDTO.getCodigo()) && 
            !instituicaoDTO.getCodigo().equals(existingInstituicao.getCodigo())) {
            validarCodigoUnico(instituicaoDTO.getCodigo(), id);
        }
        
        if (StringUtils.hasText(instituicaoDTO.getCnpj()) && 
            !instituicaoDTO.getCnpj().equals(existingInstituicao.getCnpj())) {
            validarCnpjUnico(instituicaoDTO.getCnpj(), id);
        }
        
        existingInstituicao.setNome(instituicaoDTO.getNome());
        existingInstituicao.setCodigo(instituicaoDTO.getCodigo());
        existingInstituicao.setTipo(instituicaoDTO.getTipo());
        existingInstituicao.setCnpj(instituicaoDTO.getCnpj());
        existingInstituicao.setFlgInativo(instituicaoDTO.getFlgInativo());
        if (instituicaoDTO.getDtaRemocao() != null) {
            existingInstituicao.setDtaRemocao(instituicaoDTO.getDtaRemocao());
        }
        
        Instituicoes updatedInstituicao = instituicaoRepository.save(existingInstituicao);
        return instituicaoMapper.toDTO(updatedInstituicao);
    }

    @Override
    @CacheEvict(value = "instituicoes", allEntries = true)
    public InstituicaoDTO updateInstituicaoFromDTO(int id, InstituicaoUpdateDTO updateDTO) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID da instituição deve ser válido");
        }
        
        if (updateDTO == null) {
            throw new IllegalArgumentException("Dados de atualização não podem ser nulos");
        }
        
        Instituicoes existingInstituicao = getInstituicaoOrThrow(id);
        
        if (existingInstituicao.getDtaRemocao() != null) {
            throw new BusinessException("Não é possível atualizar uma instituição que já foi removida");
        }
        
        if (updateDTO.getNome() != null) {
            if (!StringUtils.hasText(updateDTO.getNome())) {
                throw new BusinessException("Instituição", "atualizar", "Nome não pode ser vazio");
            }
            if (updateDTO.getNome().length() < 3) {
                throw new BusinessException("Instituição", "atualizar", "Nome deve ter no mínimo 3 caracteres");
            }
            existingInstituicao.setNome(updateDTO.getNome());
        }
        
        if (updateDTO.getCodigo() != null) {
            if (!StringUtils.hasText(updateDTO.getCodigo())) {
                throw new BusinessException("Instituição", "atualizar", "Código não pode ser vazio");
            }
            if (!updateDTO.getCodigo().equals(existingInstituicao.getCodigo())) {
                validarCodigoUnico(updateDTO.getCodigo(), id);
            }
            existingInstituicao.setCodigo(updateDTO.getCodigo());
        }
        
        if (updateDTO.getTipo() != null) {
            if (!StringUtils.hasText(updateDTO.getTipo())) {
                throw new BusinessException("Instituição", "atualizar", "Tipo não pode ser vazio");
            }
            existingInstituicao.setTipo(updateDTO.getTipo());
        }
        
        if (updateDTO.getCnpj() != null) {
            if (StringUtils.hasText(updateDTO.getCnpj())) {
                EntityValidator.validateCnpjFormat(updateDTO.getCnpj());
                if (!EntityValidator.isValidCnpj(updateDTO.getCnpj())) {
                    throw new BusinessException("CNPJ inválido");
                }
                
                if (!updateDTO.getCnpj().equals(existingInstituicao.getCnpj())) {
                    validarCnpjUnico(updateDTO.getCnpj(), id);
                }
            }
            existingInstituicao.setCnpj(updateDTO.getCnpj());
        }
        
        if (updateDTO.getFlgInativo() != null) {
            existingInstituicao.setFlgInativo(updateDTO.getFlgInativo());
        }
        
        Instituicoes updatedInstituicao = instituicaoRepository.save(existingInstituicao);
        return instituicaoMapper.toDTO(updatedInstituicao);
    }

    @Override
    @CacheEvict(value = "instituicoes", allEntries = true)
    public boolean deleteInstituicao(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID da instituição deve ser válido");
        }
        
        Instituicoes instituicao = getInstituicaoOrThrow(id);
        
        if (Boolean.TRUE.equals(instituicao.getFlgInativo())) {
            throw new BusinessException("A instituição já está inativa");
        }
        
        instituicao.setFlgInativo(true);
        instituicao.setDtaRemocao(LocalDateTime.now());
        instituicaoRepository.save(instituicao);
        
        return true;
    }

    @Override
    @Cacheable(value = "instituicoes", key = "'active'")
    public InstituicaoListDTO getActiveInstituicoes() {
        List<Instituicoes> activeInstituicoes = instituicaoRepository.findActive();
        return instituicaoMapper.toListDTO(activeInstituicoes);
    }

    @Override
    @Cacheable(value = "instituicoes", key = "'type_' + #tipoInstituicao")
    public InstituicaoListDTO getInstituicoesByType(String tipoInstituicao) {
        if (!StringUtils.hasText(tipoInstituicao)) {
            throw new IllegalArgumentException("Tipo da instituição não pode ser vazio");
        }
        
        List<Instituicoes> instituicoes = instituicaoRepository.findByType(tipoInstituicao);
        return instituicaoMapper.toListDTO(instituicoes);
    }
    
    private void validarInstituicaoParaCriacao(String nome, String codigo, String tipo, String cnpj) {
        if (!StringUtils.hasText(nome)) {
            throw new BusinessException("Instituição", "criar", "Nome é obrigatório");
        }
        
        if (nome.length() < 3) {
            throw new BusinessException("Instituição", "criar", "Nome deve ter no mínimo 3 caracteres");
        }
        
        if (nome.length() > 255) {
            throw new BusinessException("Instituição", "criar", "Nome deve ter no máximo 255 caracteres");
        }
        
        if (!StringUtils.hasText(codigo)) {
            throw new BusinessException("Instituição", "criar", "Código é obrigatório");
        }
        
        if (codigo.length() > 100) {
            throw new BusinessException("Instituição", "criar", "Código deve ter no máximo 100 caracteres");
        }
        
        validarCodigoUnico(codigo, null);
        
        if (!StringUtils.hasText(tipo)) {
            throw new BusinessException("Instituição", "criar", "Tipo é obrigatório");
        }
        
        if (tipo.length() > 50) {
            throw new BusinessException("Instituição", "criar", "Tipo deve ter no máximo 50 caracteres");
        }
        
        if (StringUtils.hasText(cnpj)) {
            EntityValidator.validateCnpjFormat(cnpj);
            
            if (!EntityValidator.isValidCnpj(cnpj)) {
                throw new BusinessException("CNPJ inválido");
            }
            
            validarCnpjUnico(cnpj, null);
        }
    }
    
    private void validarCodigoUnico(String codigo, Integer instituicaoIdExcluir) {
        List<Instituicoes> instituicoesComMesmoCodigo = instituicaoRepository.findAll().stream()
            .filter(i -> codigo.equals(i.getCodigo()))
            .filter(i -> instituicaoIdExcluir == null || !i.getId().equals(instituicaoIdExcluir))
            .toList();
        
        if (!instituicoesComMesmoCodigo.isEmpty()) {
            throw new BusinessException("Já existe uma instituição cadastrada com o código informado");
        }
    }
    
    private void validarCnpjUnico(String cnpj, Integer instituicaoIdExcluir) {
        List<Instituicoes> instituicoesComMesmoCnpj = instituicaoRepository.findAll().stream()
            .filter(i -> cnpj.equals(i.getCnpj()))
            .filter(i -> instituicaoIdExcluir == null || !i.getId().equals(instituicaoIdExcluir))
            .toList();
        
        if (!instituicoesComMesmoCnpj.isEmpty()) {
            throw new BusinessException("Já existe uma instituição cadastrada com o CNPJ informado");
        }
    }

    private Instituicoes getInstituicaoOrThrow(int id) {
        return instituicaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instituição não encontrada com ID: " + id));
    }
}
