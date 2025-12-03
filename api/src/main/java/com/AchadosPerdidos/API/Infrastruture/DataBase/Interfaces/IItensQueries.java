package com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Itens;
import com.AchadosPerdidos.API.Domain.Enum.Status_Item;
import java.util.List;

public interface IItensQueries {
    List<Itens> findByCampus(int campusId);
    boolean marcarComoDevolvido(int itemId, int usuarioReivindicadorId);
    boolean marcarComoResgatado(int itemId, int usuarioReivindicadorId);
    List<Itens> findByUser(int userId);
    List<Itens> searchByTerm(String searchTerm);
    List<Itens> findByTipo(String tipo);
    List<Itens> findByStatus(Status_Item status);
}
