package com.AchadosPerdidos.API.Domain.Repository.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.UsuarioRole;
import java.util.List;

public interface IUsuarioRoleRepository {
    List<UsuarioRole> findAll();
    UsuarioRole findByUsuarioIdAndRoleId(Integer usuarioId, Integer roleId);
    UsuarioRole save(UsuarioRole usuarioRole);
    boolean deleteByUsuarioIdAndRoleId(Integer usuarioId, Integer roleId);
    List<UsuarioRole> findActive();
    List<UsuarioRole> findByUsuarioId(Integer usuarioId);
    List<UsuarioRole> findByRoleId(Integer roleId);
}

