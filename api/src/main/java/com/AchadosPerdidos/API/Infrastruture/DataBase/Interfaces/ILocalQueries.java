package com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Local;
import java.util.List;

public interface ILocalQueries {
    List<Local> findAll();
    Local findById(Integer id);
    Local insert(Local local);
    Local update(Local local);
    boolean deleteById(Integer id);
    List<Local> findActive();
    List<Local> findByCampus(Integer campusId);
}

