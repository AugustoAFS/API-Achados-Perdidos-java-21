package com.AchadosPerdidos.API.Application.Services;

import com.AchadosPerdidos.API.Application.DTOs.Reivindicacoes.ReivindicacoesDTO;
import com.AchadosPerdidos.API.Application.DTOs.Reivindicacoes.ReivindicacoesListDTO;
import com.AchadosPerdidos.API.Application.Mapper.ReivindicacoesModelMapper;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IReivindicacoesService;
import com.AchadosPerdidos.API.Domain.Entity.Itens;
import com.AchadosPerdidos.API.Domain.Entity.Reivindicacoes;
import com.AchadosPerdidos.API.Domain.Repository.ReivindicacoesRepository;
import com.AchadosPerdidos.API.Exeptions.BusinessException;
import com.AchadosPerdidos.API.Exeptions.ResourceNotFoundException;
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
    
    @Autowired
    private ItensService itensService;

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
        Itens item = itensService.getItemEntityById(reivindicacoesDTO.getItemId());
        if (item == null) {
            throw new ResourceNotFoundException("Item não encontrado com ID: " + reivindicacoesDTO.getItemId());
        }
        
        if (!item.isDisponivelParaReivindicacao()) {
            throw new BusinessException("Este item não está disponível para reivindicação");
        }
        
        if (item.getUsuarioRelatorId().equals(reivindicacoesDTO.getUsuarioReivindicadorId())) {
            throw new BusinessException("Você não pode reivindicar um item que você mesmo relatou");
        }
        
        Reivindicacoes reivindicacaoExistente = reivindicacoesRepository.findByItemAndUser(
            reivindicacoesDTO.getItemId(), 
            reivindicacoesDTO.getUsuarioReivindicadorId()
        );
        
        if (reivindicacaoExistente != null && reivindicacaoExistente.isAtiva()) {
            throw new BusinessException("Você já possui uma reivindicação ativa para este item");
        }
        
        Reivindicacoes reivindicacoes = reivindicacoesModelMapper.toEntity(reivindicacoesDTO);
        reivindicacoes.setDtaCriacao(new Date());
        reivindicacoes.setFlgInativo(false);
        reivindicacoes.setUsuarioAchouId(null);
        
        reivindicacoes.validate();
        
        Reivindicacoes savedReivindicacoes = reivindicacoesRepository.save(reivindicacoes);
        return reivindicacoesModelMapper.toDTO(savedReivindicacoes);
    }

    @Override
    public ReivindicacoesDTO updateReivindicacao(int id, ReivindicacoesDTO reivindicacoesDTO) {
        Reivindicacoes existingReivindicacoes = reivindicacoesRepository.findById(id);
        if (existingReivindicacoes == null) {
            throw new ResourceNotFoundException("Reivindicação não encontrada com ID: " + id);
        }
        
        if (existingReivindicacoes.getDtaRemocao() != null) {
            throw new BusinessException("Não é possível atualizar uma reivindicação que já foi removida");
        }
        
        if (existingReivindicacoes.isAprovada()) {
            throw new BusinessException("Não é possível atualizar uma reivindicação que já foi aprovada");
        }
        
        existingReivindicacoes.setDetalhesReivindicacao(reivindicacoesDTO.getDetalhesReivindicacao());
        
        existingReivindicacoes.validate();
        
        Reivindicacoes updatedReivindicacoes = reivindicacoesRepository.save(existingReivindicacoes);
        return reivindicacoesModelMapper.toDTO(updatedReivindicacoes);
    }

    @Override
    public boolean deleteReivindicacao(int id) {
        Reivindicacoes reivindicacoes = reivindicacoesRepository.findById(id);
        if (reivindicacoes == null) {
            throw new ResourceNotFoundException("Reivindicação não encontrada com ID: " + id);
        }
        
        if (reivindicacoes.isAprovada()) {
            throw new BusinessException("Não é possível cancelar uma reivindicação que já foi aprovada");
        }
        
        reivindicacoes.marcarComoInativa();
        reivindicacoesRepository.save(reivindicacoes);
        return true;
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
    
    public ReivindicacoesDTO aprovarReivindicacao(int reivindicacaoId, int usuarioRelatorId) {
        Reivindicacoes reivindicacao = reivindicacoesRepository.findById(reivindicacaoId);
        if (reivindicacao == null) {
            throw new ResourceNotFoundException("Reivindicação não encontrada com ID: " + reivindicacaoId);
        }
        
        Itens item = itensService.getItemEntityById(reivindicacao.getItemId());
        if (item == null) {
            throw new ResourceNotFoundException("Item não encontrado");
        }
        
        if (!item.getUsuarioRelatorId().equals(usuarioRelatorId)) {
            throw new BusinessException("Apenas o relator do item pode aprovar reivindicações");
        }
        
        if (!reivindicacao.isAtiva()) {
            throw new BusinessException("Esta reivindicação não está ativa");
        }
        
        if (!item.isDisponivelParaReivindicacao()) {
            throw new BusinessException("Este item não está mais disponível");
        }
        
        reivindicacao.aprovar(usuarioRelatorId);
        
        item.setStatusItemId(2);
        itensService.getItemEntityById(item.getId());
        
        List<Reivindicacoes> outrasReivindicacoes = reivindicacoesRepository.findByItem(item.getId());
        for (Reivindicacoes outra : outrasReivindicacoes) {
            if (outra.isAtiva() && !outra.getId().equals(reivindicacaoId)) {
                outra.marcarComoInativa();
                reivindicacoesRepository.save(outra);
            }
        }
        
        Reivindicacoes savedReivindicacao = reivindicacoesRepository.save(reivindicacao);
        return reivindicacoesModelMapper.toDTO(savedReivindicacao);
    }
    
    public boolean rejeitarReivindicacao(int reivindicacaoId, int usuarioRelatorId) {
        Reivindicacoes reivindicacao = reivindicacoesRepository.findById(reivindicacaoId);
        if (reivindicacao == null) {
            throw new ResourceNotFoundException("Reivindicação não encontrada com ID: " + reivindicacaoId);
        }
        
        Itens item = itensService.getItemEntityById(reivindicacao.getItemId());
        if (item == null) {
            throw new ResourceNotFoundException("Item não encontrado");
        }
        
        if (!item.getUsuarioRelatorId().equals(usuarioRelatorId)) {
            throw new BusinessException("Apenas o relator do item pode rejeitar reivindicações");
        }
        
        if (!reivindicacao.isAtiva()) {
            throw new BusinessException("Esta reivindicação não está ativa");
        }
        
        reivindicacao.marcarComoInativa();
        reivindicacoesRepository.save(reivindicacao);
        return true;
    }
}
