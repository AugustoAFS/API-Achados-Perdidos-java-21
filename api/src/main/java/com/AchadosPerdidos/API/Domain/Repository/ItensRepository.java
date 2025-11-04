package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.Itens;
import com.AchadosPerdidos.API.Domain.Repository.Interfaces.IItensRepository;
import com.AchadosPerdidos.API.Infrastruture.DataBase.ItensQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItensRepository implements IItensRepository {

    @Autowired
    private ItensQueries itensQueries;

    @Override
    public List<Itens> findAll() {
        return itensQueries.findAll();
    }

    @Override
    public Itens findById(int id) {
        return itensQueries.findById(id);
    }

    @Override
    public Itens save(Itens itens) {
        if (itens.getId() == null || itens.getId() == 0) {
            return itensQueries.insert(itens);
        } else {
            return itensQueries.update(itens);
        }
    }

    @Override
    public boolean deleteById(int id) {
        return itensQueries.deleteById(id);
    }

    @Override
    public List<Itens> findActive() {
        return itensQueries.findActive();
    }

    @Override
    public List<Itens> findByStatus(int statusId) {
        return itensQueries.findByStatus(statusId);
    }

    @Override
    public List<Itens> findByUser(int userId) {
        return itensQueries.findByUser(userId);
    }

    @Override
    public List<Itens> findByCampus(int campusId) {
        return itensQueries.findByCampus(campusId);
    }

    @Override
    public List<Itens> findByLocal(int localId) {
        return itensQueries.findByLocal(localId);
    }

    @Override
    public List<Itens> findByEmpresa(int empresaId) {
        return itensQueries.findByEmpresa(empresaId);
    }

    @Override
    public List<Itens> searchByTerm(String searchTerm) {
        return itensQueries.searchByTerm(searchTerm);
    }

    @Override
    public List<Itens> findItemsNearDonationDeadline(int daysFromNow) {
        return itensQueries.findItemsNearDonationDeadline(daysFromNow);
    }

    @Override
    public List<Itens> findExpiredItems(int daysExpired) {
        return itensQueries.findExpiredItems(daysExpired);
    }
}
