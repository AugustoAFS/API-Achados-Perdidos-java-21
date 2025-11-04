package com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Campus;
import java.util.List;

public interface ICampusQueries {
    List<Campus> findAll();
    Campus findById(int id);
    Campus insert(Campus campus);
    Campus update(Campus campus);
    boolean deleteById(int id);
    List<Campus> findActive();
    List<Campus> findByInstitution(int institutionId);
}
