package com.AchadosPerdidos.API.Domain.Repository.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Role;
import java.util.List;

public interface IRoleRepository {
    List<Role> findAll();
    Role findById(Integer id);
    Role findByNome(String nome);
    List<Role> findActive();
}

