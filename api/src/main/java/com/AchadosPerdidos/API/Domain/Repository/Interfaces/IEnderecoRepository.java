package com.AchadosPerdidos.API.Domain.Repository.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Endereco;
import java.util.List;
import java.util.Optional;

/**
 * Interface do Repository para Endereços
 * Define operações de persistência para entidade Endereco
 * Vinculados a Cidades (logradouro, número, bairro, CEP)
 */
public interface IEnderecoRepository {
    // Operações CRUD básicas
    List<Endereco> findAll();
    Optional<Endereco> findById(Integer id);
    Endereco save(Endereco endereco);
    void deleteById(Integer id);
    
    // Buscas específicas
    List<Endereco> findActive();
    List<Endereco> findByCidade(Integer cidadeId);
}

