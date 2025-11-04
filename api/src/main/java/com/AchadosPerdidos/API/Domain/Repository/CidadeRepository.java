package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.Cidade;
import com.AchadosPerdidos.API.Domain.Repository.Interfaces.ICidadeRepository;
import com.AchadosPerdidos.API.Infrastruture.DataBase.CidadeQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CidadeRepository implements ICidadeRepository {

    @Autowired
    private CidadeQueries cidadeQueries;
    
    @Override
    public List<Cidade> findAll() {
        return cidadeQueries.findAll();
    }

    @Override
    public Cidade findById(Integer id) {
        return cidadeQueries.findById(id);
    }

    @Override
    public Cidade save(Cidade cidade) {
        if (cidade.getId() == null || cidade.getId() == 0) {
            return cidadeQueries.insert(cidade);
        } else {
            return cidadeQueries.update(cidade);
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        return cidadeQueries.deleteById(id);
    }

    @Override
    public List<Cidade> findActive() {
        return cidadeQueries.findActive();
    }

    @Override
    public List<Cidade> findByEstado(Integer estadoId) {
        return cidadeQueries.findByEstado(estadoId);
    }
}

