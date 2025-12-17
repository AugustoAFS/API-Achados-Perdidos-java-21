package com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Campus;
import java.util.List;
import java.util.Optional;

public interface ICampusQueries {
    List<Campus> findActive();
    Optional<Campus> findByIdAndFlgInativoFalse(Integer id);
    List<Campus> findByInstitution(Integer institutionId);
}

