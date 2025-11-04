package com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Endereco;
import java.util.List;

public interface IEnderecoQueries {
    List<Endereco> findAll();
    Endereco findById(Integer id);
    Endereco insert(Endereco endereco);
    Endereco update(Endereco endereco);
    boolean deleteById(Integer id);
    List<Endereco> findActive();
    List<Endereco> findByCidade(Integer cidadeId);
}

