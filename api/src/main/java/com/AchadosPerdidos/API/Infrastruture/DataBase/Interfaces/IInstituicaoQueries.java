package com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Instituicoes;
import java.util.List;

public interface IInstituicaoQueries {
    List<Instituicoes> findAll();
    Instituicoes findById(int id);
    Instituicoes insert(Instituicoes instituicao);
    Instituicoes update(Instituicoes instituicao);
    boolean deleteById(int id);
    List<Instituicoes> findActive();
    List<Instituicoes> findByType(String tipoInstituicao);
}
