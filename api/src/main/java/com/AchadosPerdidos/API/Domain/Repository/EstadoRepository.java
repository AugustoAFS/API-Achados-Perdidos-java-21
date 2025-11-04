package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.Estado;
import com.AchadosPerdidos.API.Domain.Repository.Interfaces.IEstadoRepository;
import com.AchadosPerdidos.API.Infrastruture.DataBase.EstadoQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EstadoRepository implements IEstadoRepository {

    @Autowired
    private EstadoQueries estadoQueries;
    
    @Override
    public List<Estado> findAll() {
        return estadoQueries.findAll();
    }

    @Override
    public Estado findById(Integer id) {
        return estadoQueries.findById(id);
    }

    @Override
    public Estado findByUf(String uf) {
        return estadoQueries.findByUf(uf);
    }

    @Override
    public Estado save(Estado estado) {
        if (estado.getId() == null || estado.getId() == 0) {
            return estadoQueries.insert(estado);
        } else {
            return estadoQueries.update(estado);
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        return estadoQueries.deleteById(id);
    }

    @Override
    public List<Estado> findActive() {
        return estadoQueries.findActive();
    }
}

