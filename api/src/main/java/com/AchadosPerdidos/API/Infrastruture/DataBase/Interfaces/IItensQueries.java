package com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Itens;
import java.util.List;

public interface IItensQueries {
    List<Itens> findAll();
    Itens findById(int id);
    Itens insert(Itens itens);
    Itens update(Itens itens);
    boolean deleteById(int id);
    List<Itens> findActive();
    List<Itens> findByUser(int userId);
    List<Itens> findByCampus(int campusId);
    List<Itens> findByLocal(int localId);
    List<Itens> searchByTerm(String searchTerm);
    List<Itens> findByTipo(String tipo);
}
