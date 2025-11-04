package com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Reivindicacoes;
import java.util.List;

public interface IReivindicacoesQueries {
    List<Reivindicacoes> findAll();
    Reivindicacoes findById(int id);
    Reivindicacoes insert(Reivindicacoes reivindicacoes);
    Reivindicacoes update(Reivindicacoes reivindicacoes);
    boolean deleteById(int id);
    List<Reivindicacoes> findByItem(int itemId);
    List<Reivindicacoes> findByUser(int userId);
    List<Reivindicacoes> findByProprietario(int proprietarioId);
    Reivindicacoes findByItemAndUser(int itemId, int userId);
}
