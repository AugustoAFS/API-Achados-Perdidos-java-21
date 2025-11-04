package com.AchadosPerdidos.API.Domain.Repository.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Instituicao;
import java.util.List;

public interface IInstituicaoRepository {
    List<Instituicao> findAll();
    Instituicao findById(int id);
    Instituicao save(Instituicao instituicao);
    boolean deleteById(int id);
    List<Instituicao> findActive();
    List<Instituicao> findByType(String tipoInstituicao);
}
