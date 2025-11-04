package com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Estado;
import java.util.List;

public interface IEstadoQueries {
    List<Estado> findAll();
    Estado findById(Integer id);
    Estado findByUf(String uf);
    Estado insert(Estado estado);
    Estado update(Estado estado);
    boolean deleteById(Integer id);
    List<Estado> findActive();
}

