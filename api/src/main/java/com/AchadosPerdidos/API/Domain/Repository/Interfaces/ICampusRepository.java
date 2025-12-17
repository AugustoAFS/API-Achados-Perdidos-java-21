package com.AchadosPerdidos.API.Domain.Repository.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Campus;
import java.util.List;
import java.util.Optional;

public interface ICampusRepository {
    // Operações CRUD básicas
    List<Campus> findAll();
    Optional<Campus> findById(Integer id);
    Campus save(Campus campus);
    void deleteById(Integer id);
    
    // Buscas específicas
    List<Campus> findActive();
    Optional<Campus> findByIdAndFlgInativoFalse(Integer id);
    List<Campus> findByInstitution(Integer institutionId);
}
