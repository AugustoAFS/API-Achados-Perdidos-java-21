package com.AchadosPerdidos.API.Domain.Repository.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Reivindicacoes;
import java.util.List;

public interface IReivindicacoesRepository {
    List<Reivindicacoes> findAll();
    Reivindicacoes findById(int id);
    Reivindicacoes save(Reivindicacoes reivindicacoes);
    boolean deleteById(int id);
    List<Reivindicacoes> findByItem(int itemId);
    List<Reivindicacoes> findByUser(int userId);
    List<Reivindicacoes> findByProprietario(int proprietarioId);
    Reivindicacoes findByItemAndUser(int itemId, int userId);
}
