package com.AchadosPerdidos.API.Domain.Repository.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Endereco;
import java.util.List;

/**
 * Interface do Repository para Endereços
 * Define operações de persistência para entidade Endereco
 * Vinculados a Cidades (logradouro, número, bairro, CEP)
 */
public interface IEnderecoRepository {
    List<Endereco> findAll();
    Endereco findById(Integer id);
    Endereco save(Endereco endereco);
    boolean deleteById(Integer id);
    List<Endereco> findActive();
    List<Endereco> findByCidade(Integer cidadeId);
}

