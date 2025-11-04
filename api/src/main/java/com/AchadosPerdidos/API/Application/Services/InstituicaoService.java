package com.AchadosPerdidos.API.Application.Services;

import com.AchadosPerdidos.API.Application.DTOs.Instituicao.InstituicaoDTO;
import com.AchadosPerdidos.API.Application.DTOs.Instituicao.InstituicaoListDTO;
import com.AchadosPerdidos.API.Application.DTOs.Instituicao.InstituicaoCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Instituicao.InstituicaoUpdateDTO;
import com.AchadosPerdidos.API.Application.Mapper.InstituicaoModelMapper;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IInstituicaoService;
import com.AchadosPerdidos.API.Domain.Entity.Instituicao;
import com.AchadosPerdidos.API.Domain.Repository.InstituicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class InstituicaoService implements IInstituicaoService {

    @Autowired
    private InstituicaoRepository instituicaoRepository;

    @Autowired
    private InstituicaoModelMapper instituicaoModelMapper;

    @Override
    public InstituicaoListDTO getAllInstituicoes() {
        List<Instituicao> instituicoes = instituicaoRepository.findAll();
        return instituicaoModelMapper.toListDTO(instituicoes);
    }

    @Override
    public InstituicaoDTO getInstituicaoById(int id) {
        Instituicao instituicao = instituicaoRepository.findById(id);
        return instituicaoModelMapper.toDTO(instituicao);
    }

    @Override
    public InstituicaoDTO createInstituicao(InstituicaoDTO instituicaoDTO) {
        Instituicao instituicao = instituicaoModelMapper.toEntity(instituicaoDTO);
        instituicao.setDtaCriacao(new Date());
        instituicao.setFlgInativo(false);
        
        Instituicao savedInstituicao = instituicaoRepository.save(instituicao);
        return instituicaoModelMapper.toDTO(savedInstituicao);
    }

    @Override
    public InstituicaoDTO createInstituicaoFromDTO(InstituicaoCreateDTO createDTO) {
        Instituicao instituicao = new Instituicao();
        instituicao.setNome(createDTO.getNome());
        instituicao.setCodigo(createDTO.getCodigo());
        instituicao.setTipo(createDTO.getTipo());
        instituicao.setCnpj(createDTO.getCnpj());
        instituicao.setDtaCriacao(new Date());
        instituicao.setFlgInativo(false);
        
        Instituicao savedInstituicao = instituicaoRepository.save(instituicao);
        return instituicaoModelMapper.toDTO(savedInstituicao);
    }

    @Override
    public InstituicaoDTO updateInstituicao(int id, InstituicaoDTO instituicaoDTO) {
        Instituicao existingInstituicao = instituicaoRepository.findById(id);
        if (existingInstituicao == null) {
            return null;
        }
        
        existingInstituicao.setNome(instituicaoDTO.getNome());
        existingInstituicao.setCodigo(instituicaoDTO.getCodigo());
        existingInstituicao.setTipo(instituicaoDTO.getTipo());
        existingInstituicao.setCnpj(instituicaoDTO.getCnpj());
        existingInstituicao.setFlgInativo(instituicaoDTO.getFlgInativo());
        existingInstituicao.setDtaRemocao(instituicaoDTO.getDtaRemocao());
        
        Instituicao updatedInstituicao = instituicaoRepository.save(existingInstituicao);
        return instituicaoModelMapper.toDTO(updatedInstituicao);
    }

    @Override
    public InstituicaoDTO updateInstituicaoFromDTO(int id, InstituicaoUpdateDTO updateDTO) {
        Instituicao existingInstituicao = instituicaoRepository.findById(id);
        if (existingInstituicao == null) {
            return null;
        }
        
        // Atualizar apenas os campos fornecidos
        if (updateDTO.getNome() != null) {
            existingInstituicao.setNome(updateDTO.getNome());
        }
        if (updateDTO.getCodigo() != null) {
            existingInstituicao.setCodigo(updateDTO.getCodigo());
        }
        if (updateDTO.getTipo() != null) {
            existingInstituicao.setTipo(updateDTO.getTipo());
        }
        if (updateDTO.getCnpj() != null) {
            existingInstituicao.setCnpj(updateDTO.getCnpj());
        }
        if (updateDTO.getFlgInativo() != null) {
            existingInstituicao.setFlgInativo(updateDTO.getFlgInativo());
        }
        
        Instituicao updatedInstituicao = instituicaoRepository.save(existingInstituicao);
        return instituicaoModelMapper.toDTO(updatedInstituicao);
    }

    @Override
    public boolean deleteInstituicao(int id) {
        Instituicao instituicao = instituicaoRepository.findById(id);
        if (instituicao == null) {
            return false;
        }
        
        return instituicaoRepository.deleteById(id);
    }

    @Override
    public InstituicaoListDTO getActiveInstituicoes() {
        List<Instituicao> activeInstituicoes = instituicaoRepository.findActive();
        return instituicaoModelMapper.toListDTO(activeInstituicoes);
    }

    @Override
    public InstituicaoListDTO getInstituicoesByType(String tipoInstituicao) {
        List<Instituicao> instituicoes = instituicaoRepository.findByType(tipoInstituicao);
        return instituicaoModelMapper.toListDTO(instituicoes);
    }
}
