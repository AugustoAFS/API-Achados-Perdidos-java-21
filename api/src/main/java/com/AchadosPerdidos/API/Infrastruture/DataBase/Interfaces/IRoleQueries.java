package com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Role;
import java.util.List;

public interface IRoleQueries {
    List<Role> findAll();
    Role findById(Integer id);
    Role findByNome(String nome);
    List<Role> findActive();
}

