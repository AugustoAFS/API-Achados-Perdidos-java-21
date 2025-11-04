package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.Local;
import com.AchadosPerdidos.API.Domain.Repository.Interfaces.ILocalRepository;
import com.AchadosPerdidos.API.Infrastruture.DataBase.LocalQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LocalRepository implements ILocalRepository {

    @Autowired
    private LocalQueries localQueries;
    
    @Override
    public List<Local> findAll() {
        return localQueries.findAll();
    }

    @Override
    public Local findById(Integer id) {
        return localQueries.findById(id);
    }

    @Override
    public Local save(Local local) {
        if (local.getId() == null || local.getId() == 0) {
            return localQueries.insert(local);
        } else {
            return localQueries.update(local);
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        return localQueries.deleteById(id);
    }

    @Override
    public List<Local> findActive() {
        return localQueries.findActive();
    }

    @Override
    public List<Local> findByCampus(Integer campusId) {
        return localQueries.findByCampus(campusId);
    }
}

