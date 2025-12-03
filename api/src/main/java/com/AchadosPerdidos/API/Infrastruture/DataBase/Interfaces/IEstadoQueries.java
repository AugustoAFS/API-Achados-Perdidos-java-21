package com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Estado;
import java.util.List;

public interface IEstadoQueries {
    List<Estado> findActive();
    Estado findByUf(String uf);
}

