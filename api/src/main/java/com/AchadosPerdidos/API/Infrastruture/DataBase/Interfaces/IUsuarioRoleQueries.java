package com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.UsuarioRole;
import java.util.List;

public interface IUsuarioRoleQueries {
    List<UsuarioRole> findAll();
    UsuarioRole findByUsuarioIdAndRoleId(Integer usuarioId, Integer roleId);
    List<UsuarioRole> findByUsuarioId(Integer usuarioId);
    List<UsuarioRole> findByRoleId(Integer roleId);
    UsuarioRole insert(UsuarioRole usuarioRole);
    UsuarioRole update(UsuarioRole usuarioRole);
    boolean deleteByUsuarioIdAndRoleId(Integer usuarioId, Integer roleId);
    List<UsuarioRole> findActive();
}

