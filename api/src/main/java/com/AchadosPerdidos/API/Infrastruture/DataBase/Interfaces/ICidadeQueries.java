package com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Cidade;
import java.util.List;

public interface ICidadeQueries {
    List<Cidade> findActive();
    List<Cidade> findByEstado(Integer estadoId);
}

