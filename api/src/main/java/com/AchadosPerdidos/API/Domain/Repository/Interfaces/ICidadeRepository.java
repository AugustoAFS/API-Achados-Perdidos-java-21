package com.AchadosPerdidos.API.Domain.Repository.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Cidade;
import java.util.List;

public interface ICidadeRepository {
    List<Cidade> findAll();
    Cidade findById(Integer id);
    Cidade save(Cidade cidade);
    boolean deleteById(Integer id);
    List<Cidade> findActive();
    List<Cidade> findByEstado(Integer estadoId);
}

