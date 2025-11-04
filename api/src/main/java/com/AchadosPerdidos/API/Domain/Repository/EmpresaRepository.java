package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.Empresa;
import com.AchadosPerdidos.API.Domain.Repository.Interfaces.IEmpresaRepository;
import com.AchadosPerdidos.API.Infrastruture.DataBase.EmpresaQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmpresaRepository implements IEmpresaRepository {

    @Autowired
    private EmpresaQueries empresaQueries;

    @Override
    public List<Empresa> findAll() {
        return empresaQueries.findAll();
    }

    @Override
    public Empresa findById(int id) {
        return empresaQueries.findById(id);
    }

    @Override
    public Empresa save(Empresa empresa) {
        if (empresa.getId() == null || empresa.getId() == 0) {
            return empresaQueries.insert(empresa);
        } else {
            return empresaQueries.update(empresa);
        }
    }

    @Override
    public boolean deleteById(int id) {
        return empresaQueries.deleteById(id);
    }

    @Override
    public List<Empresa> findActive() {
        return empresaQueries.findActive();
    }

    @Override
    public List<Empresa> findByCountry(String paisSede) {
        return empresaQueries.findByCountry(paisSede);
    }
}
