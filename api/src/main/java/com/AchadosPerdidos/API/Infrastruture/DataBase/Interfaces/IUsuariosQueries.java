package com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Usuarios;
import java.util.List;

public interface IUsuariosQueries {
    List<Usuarios> findAll();
    Usuarios findById(int id);
    Usuarios findByEmail(String email);
    Usuarios findByEmailAndPassword(String email, String senha);
    Usuarios insert(Usuarios usuarios);
    Usuarios update(Usuarios usuarios);
    boolean deleteById(int id);
    List<Usuarios> findActive();
    List<Usuarios> findByRole(int tipoRoleId);
    List<Usuarios> findByInstitution(int instituicaoId);
    List<Usuarios> findByCampus(int campusId);
}