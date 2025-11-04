package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.Role;
import com.AchadosPerdidos.API.Domain.Repository.Interfaces.IRoleRepository;
import com.AchadosPerdidos.API.Infrastruture.DataBase.RoleQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleRepository implements IRoleRepository {

    @Autowired
    private RoleQueries roleQueries;

    @Override
    public List<Role> findAll() {
        return roleQueries.findAll();
    }

    @Override
    public Role findById(Integer id) {
        return roleQueries.findById(id);
    }

    @Override
    public Role findByNome(String nome) {
        return roleQueries.findByNome(nome);
    }

    @Override
    public List<Role> findActive() {
        return roleQueries.findActive();
    }
}

