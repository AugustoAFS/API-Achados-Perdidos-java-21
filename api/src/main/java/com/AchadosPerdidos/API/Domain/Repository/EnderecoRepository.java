package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.Endereco;
import com.AchadosPerdidos.API.Domain.Repository.Interfaces.IEnderecoRepository;
import com.AchadosPerdidos.API.Infrastruture.DataBase.EnderecoQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EnderecoRepository implements IEnderecoRepository {

    @Autowired
    private EnderecoQueries enderecoQueries;
    
    @Override
    public List<Endereco> findAll() {
        return enderecoQueries.findAll();
    }

    @Override
    public Endereco findById(Integer id) {
        return enderecoQueries.findById(id);
    }

    @Override
    public Endereco save(Endereco endereco) {
        if (endereco.getId() == null || endereco.getId() == 0) {
            return enderecoQueries.insert(endereco);
        } else {
            return enderecoQueries.update(endereco);
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        return enderecoQueries.deleteById(id);
    }

    @Override
    public List<Endereco> findActive() {
        return enderecoQueries.findActive();
    }

    @Override
    public List<Endereco> findByCidade(Integer cidadeId) {
        return enderecoQueries.findByCidade(cidadeId);
    }
}

