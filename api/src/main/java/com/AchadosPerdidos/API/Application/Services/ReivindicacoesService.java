package com.AchadosPerdidos.API.Application.Services;

import com.AchadosPerdidos.API.Application.DTOs.Reivindicacoes.ReivindicacoesDTO;
import com.AchadosPerdidos.API.Application.DTOs.Reivindicacoes.ReivindicacoesListDTO;
import com.AchadosPerdidos.API.Application.Mapper.ReivindicacoesModelMapper;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IReivindicacoesService;
import com.AchadosPerdidos.API.Domain.Entity.Reivindicacoes;
import com.AchadosPerdidos.API.Domain.Repository.ReivindicacoesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReivindicacoesService implements IReivindicacoesService {

    @Autowired
    private ReivindicacoesRepository reivindicacoesRepository;

    @Autowired
    private ReivindicacoesModelMapper reivindicacoesModelMapper;

    @Override
    public ReivindicacoesListDTO getAllReivindicacoes() {
        List<Reivindicacoes> reivindicacoes = reivindicacoesRepository.findAll();
        return reivindicacoesModelMapper.toListDTO(reivindicacoes);
    }

    @Override
    public ReivindicacoesDTO getReivindicacaoById(int id) {
        Reivindicacoes reivindicacoes = reivindicacoesRepository.findById(id);
        return reivindicacoesModelMapper.toDTO(reivindicacoes);
    }

    @Override
    public ReivindicacoesDTO createReivindicacao(ReivindicacoesDTO reivindicacoesDTO) {
        Reivindicacoes reivindicacoes = reivindicacoesModelMapper.toEntity(reivindicacoesDTO);
        reivindicacoes.setDtaCriacao(new Date());
        reivindicacoes.setFlgInativo(false);
        
        Reivindicacoes savedReivindicacoes = reivindicacoesRepository.save(reivindicacoes);
        return reivindicacoesModelMapper.toDTO(savedReivindicacoes);
    }

    @Override
    public ReivindicacoesDTO updateReivindicacao(int id, ReivindicacoesDTO reivindicacoesDTO) {
        Reivindicacoes existingReivindicacoes = reivindicacoesRepository.findById(id);
        if (existingReivindicacoes == null) {
            return null;
        }
        
        existingReivindicacoes.setDetalhesReivindicacao(reivindicacoesDTO.getDetalhesReivindicacao());
        existingReivindicacoes.setItemId(reivindicacoesDTO.getItemId());
        existingReivindicacoes.setUsuarioReivindicadorId(reivindicacoesDTO.getUsuarioReivindicadorId());
        existingReivindicacoes.setUsuarioAchouId(reivindicacoesDTO.getUsuarioAchouId());
        existingReivindicacoes.setFlgInativo(reivindicacoesDTO.getFlgInativo());
        existingReivindicacoes.setDtaRemocao(reivindicacoesDTO.getDtaRemocao());
        
        Reivindicacoes updatedReivindicacoes = reivindicacoesRepository.save(existingReivindicacoes);
        return reivindicacoesModelMapper.toDTO(updatedReivindicacoes);
    }

    @Override
    public boolean deleteReivindicacao(int id) {
        Reivindicacoes reivindicacoes = reivindicacoesRepository.findById(id);
        if (reivindicacoes == null) {
            return false;
        }
        
        return reivindicacoesRepository.deleteById(id);
    }

    @Override
    public ReivindicacoesListDTO getReivindicacoesByItem(int itemId) {
        List<Reivindicacoes> reivindicacoes = reivindicacoesRepository.findByItem(itemId);
        return reivindicacoesModelMapper.toListDTO(reivindicacoes);
    }

    @Override
    public ReivindicacoesListDTO getReivindicacoesByUser(int userId) {
        List<Reivindicacoes> reivindicacoes = reivindicacoesRepository.findByUser(userId);
        return reivindicacoesModelMapper.toListDTO(reivindicacoes);
    }

    @Override
    public ReivindicacoesListDTO getReivindicacoesByProprietario(int proprietarioId) {
        List<Reivindicacoes> reivindicacoes = reivindicacoesRepository.findByProprietario(proprietarioId);
        return reivindicacoesModelMapper.toListDTO(reivindicacoes);
    }

    @Override
    public ReivindicacoesDTO getReivindicacaoByItemAndUser(int itemId, int userId) {
        Reivindicacoes reivindicacoes = reivindicacoesRepository.findByItemAndUser(itemId, userId);
        return reivindicacoesModelMapper.toDTO(reivindicacoes);
    }
}
