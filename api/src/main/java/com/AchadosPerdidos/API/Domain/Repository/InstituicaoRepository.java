package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.Instituicao;
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
    public List<Instituicao> findAll() {
        return instituicaoQueries.findAll();
    }

    @Override
    public Instituicao findById(int id) {
        return instituicaoQueries.findById(id);
    }

    @Override
    public Instituicao save(Instituicao instituicao) {
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
    public List<Instituicao> findActive() {
        return instituicaoQueries.findActive();
    }

    @Override
    public List<Instituicao> findByType(String tipoInstituicao) {
        return instituicaoQueries.findByType(tipoInstituicao);
    }
}
