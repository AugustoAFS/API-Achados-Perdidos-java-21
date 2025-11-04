package com.AchadosPerdidos.API.Domain.Repository.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Endereco;
import java.util.List;

public interface IEnderecoRepository {
    List<Endereco> findAll();
    Endereco findById(Integer id);
    Endereco save(Endereco endereco);
    boolean deleteById(Integer id);
    List<Endereco> findActive();
    List<Endereco> findByCidade(Integer cidadeId);
}

