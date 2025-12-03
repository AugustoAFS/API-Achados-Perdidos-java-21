package com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Instituicoes;
import java.util.List;

public interface IInstituicaoQueries {
    List<Instituicoes> findActive();
    List<Instituicoes> findByType(String tipoInstituicao);
}

