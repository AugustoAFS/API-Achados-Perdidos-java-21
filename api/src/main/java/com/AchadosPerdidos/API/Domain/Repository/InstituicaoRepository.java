package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.Instituicoes;
import com.AchadosPerdidos.API.Domain.Repository.Interfaces.IInstituicaoRepository;
import com.AchadosPerdidos.API.Infrastruture.DataBase.InstituicaoQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InstituicaoRepository implements IInstituicaoRepository {

    @Autowired
    private InstituicaoQueries instituicaoQueries;

    @Override
    public List<Instituicoes> findAll() {
        return instituicaoQueries.findAll();
    }

    @Override
    public Instituicoes findById(int id) {
        return instituicaoQueries.findById(id);
    }

    @Override
    public Instituicoes save(Instituicoes instituicao) {
        if (instituicao.getId() == null || instituicao.getId() == 0) {
            return instituicaoQueries.insert(instituicao);
        } else {
            return instituicaoQueries.update(instituicao);
        }
    }

    @Override
    public boolean deleteById(int id) {
        return instituicaoQueries.deleteById(id);
    }

    @Override
    public List<Instituicoes> findActive() {
        return instituicaoQueries.findActive();
    }

    @Override
    public List<Instituicoes> findByType(String tipoInstituicao) {
        return instituicaoQueries.findByType(tipoInstituicao);
    }
}
