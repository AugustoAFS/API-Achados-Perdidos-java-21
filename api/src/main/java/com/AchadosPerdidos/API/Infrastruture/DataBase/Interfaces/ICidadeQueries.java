package com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Cidade;
import java.util.List;

public interface ICidadeQueries {
    List<Cidade> findAll();
    Cidade findById(Integer id);
    Cidade insert(Cidade cidade);
    Cidade update(Cidade cidade);
    boolean deleteById(Integer id);
    List<Cidade> findActive();
    List<Cidade> findByEstado(Integer estadoId);
}

