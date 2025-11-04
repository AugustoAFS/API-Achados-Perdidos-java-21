package com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Instituicao;
import java.util.List;

public interface IInstituicaoQueries {
    List<Instituicao> findAll();
    Instituicao findById(int id);
    Instituicao insert(Instituicao instituicao);
    Instituicao update(Instituicao instituicao);
    boolean deleteById(int id);
    List<Instituicao> findActive();
    List<Instituicao> findByType(String tipoInstituicao);
}
