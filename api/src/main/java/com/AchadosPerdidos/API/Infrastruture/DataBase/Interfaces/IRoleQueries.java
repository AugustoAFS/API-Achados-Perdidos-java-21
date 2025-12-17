package com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Role;
import java.util.List;
import java.util.Optional;

public interface IRoleQueries {
    List<Role> findActive();
    Optional<Role> findByNome(String nome);
}

