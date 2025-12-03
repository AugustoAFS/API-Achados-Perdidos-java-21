package com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Endereco;
import java.util.List;

public interface IEnderecoQueries {
    List<Endereco> findActive();
    List<Endereco> findByCidade(Integer cidadeId);
}

