package com.AchadosPerdidos.API.Domain.Repository.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Empresa;
import java.util.List;

public interface IEmpresaRepository {
    List<Empresa> findAll();
    Empresa findById(int id);
    Empresa save(Empresa empresa);
    boolean deleteById(int id);
    List<Empresa> findActive();
    List<Empresa> findByCountry(String paisSede);
}
