package com.AchadosPerdidos.API.Application.Services.Interfaces;

import com.AchadosPerdidos.API.Application.DTOs.Reivindicacoes.ReivindicacoesDTO;
import com.AchadosPerdidos.API.Application.DTOs.Reivindicacoes.ReivindicacoesListDTO;

public interface IReivindicacoesService {
    ReivindicacoesListDTO getAllReivindicacoes();
    ReivindicacoesDTO getReivindicacaoById(int id);
    ReivindicacoesDTO createReivindicacao(ReivindicacoesDTO reivindicacoesDTO);
    ReivindicacoesDTO updateReivindicacao(int id, ReivindicacoesDTO reivindicacoesDTO);
    boolean deleteReivindicacao(int id);
    ReivindicacoesListDTO getReivindicacoesByItem(int itemId);
    ReivindicacoesListDTO getReivindicacoesByUser(int userId);
    ReivindicacoesListDTO getReivindicacoesByProprietario(int proprietarioId);
    ReivindicacoesDTO getReivindicacaoByItemAndUser(int itemId, int userId);
}
