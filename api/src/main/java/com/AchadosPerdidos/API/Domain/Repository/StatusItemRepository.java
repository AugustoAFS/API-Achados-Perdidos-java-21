package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.StatusItem;
import com.AchadosPerdidos.API.Domain.Repository.Interfaces.IStatusItemRepository;
import com.AchadosPerdidos.API.Infrastruture.DataBase.StatusItemQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StatusItemRepository implements IStatusItemRepository {

    @Autowired
    private StatusItemQueries statusItemQueries;
    
    @Override
    public List<StatusItem> findAll() {
        return statusItemQueries.findAll();
    }

    @Override
    public StatusItem findById(Integer id) {
        return statusItemQueries.findById(id);
    }

    @Override
    public StatusItem findByNome(String nome) {
        return statusItemQueries.findByNome(nome);
    }

    @Override
    public StatusItem save(StatusItem statusItem) {
        if (statusItem.getId() == null || statusItem.getId() == 0) {
            return statusItemQueries.insert(statusItem);
        } else {
            return statusItemQueries.update(statusItem);
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        return statusItemQueries.deleteById(id);
    }

    @Override
    public List<StatusItem> findActive() {
        return statusItemQueries.findActive();
    }
}

