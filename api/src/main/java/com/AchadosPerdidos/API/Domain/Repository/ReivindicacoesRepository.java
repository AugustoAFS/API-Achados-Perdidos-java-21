package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.Reivindicacoes;
import com.AchadosPerdidos.API.Domain.Repository.Interfaces.IReivindicacoesRepository;
import com.AchadosPerdidos.API.Infrastruture.DataBase.ReivindicacoesQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReivindicacoesRepository implements IReivindicacoesRepository {

    @Autowired
    private ReivindicacoesQueries reivindicacoesQueries;

    @Override
    public List<Reivindicacoes> findAll() {
        return reivindicacoesQueries.findAll();
    }

    @Override
    public Reivindicacoes findById(int id) {
        return reivindicacoesQueries.findById(id);
    }

    @Override
    public Reivindicacoes save(Reivindicacoes reivindicacoes) {
        if (reivindicacoes.getId() == null || reivindicacoes.getId() == 0) {
            return reivindicacoesQueries.insert(reivindicacoes);
        } else {
            return reivindicacoesQueries.update(reivindicacoes);
        }
    }

    @Override
    public boolean deleteById(int id) {
        return reivindicacoesQueries.deleteById(id);
    }

    @Override
    public List<Reivindicacoes> findByItem(int itemId) {
        return reivindicacoesQueries.findByItem(itemId);
    }

    @Override
    public List<Reivindicacoes> findByUser(int userId) {
        return reivindicacoesQueries.findByUser(userId);
    }

    @Override
    public List<Reivindicacoes> findByProprietario(int proprietarioId) {
        return reivindicacoesQueries.findByProprietario(proprietarioId);
    }

    @Override
    public Reivindicacoes findByItemAndUser(int itemId, int userId) {
        return reivindicacoesQueries.findByItemAndUser(itemId, userId);
    }
}
