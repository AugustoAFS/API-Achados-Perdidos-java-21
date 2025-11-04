package com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Empresa;
import java.util.List;

public interface IEmpresaQueries {
    List<Empresa> findAll();
    Empresa findById(int id);
    Empresa insert(Empresa empresa);
    Empresa update(Empresa empresa);
    boolean deleteById(int id);
    List<Empresa> findActive();
    List<Empresa> findByCountry(String paisSede);
}
