package com.AchadosPerdidos.API.Domain.Repository.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Local;
import java.util.List;

public interface ILocalRepository {
    List<Local> findAll();
    Local findById(Integer id);
    Local save(Local local);
    boolean deleteById(Integer id);
    List<Local> findActive();
    List<Local> findByCampus(Integer campusId);
}

