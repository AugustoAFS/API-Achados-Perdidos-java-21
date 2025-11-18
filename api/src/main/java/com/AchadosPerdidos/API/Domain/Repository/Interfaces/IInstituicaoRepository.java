package com.AchadosPerdidos.API.Domain.Repository.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Instituicoes;
import java.util.List;

public interface IInstituicaoRepository {
    List<Instituicoes> findAll();
    Instituicoes findById(int id);
    Instituicoes save(Instituicoes instituicao);
    boolean deleteById(int id);
    List<Instituicoes> findActive();
    List<Instituicoes> findByType(String tipoInstituicao);
}
