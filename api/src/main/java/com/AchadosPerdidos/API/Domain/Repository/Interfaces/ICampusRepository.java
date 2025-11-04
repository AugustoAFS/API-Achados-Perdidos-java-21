package com.AchadosPerdidos.API.Domain.Repository.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Campus;
import java.util.List;

public interface ICampusRepository {
    List<Campus> findAll();
    Campus findById(int id);
    Campus save(Campus campus);
    boolean deleteById(int id);
    List<Campus> findActive();
    List<Campus> findByInstitution(int institutionId);
}
