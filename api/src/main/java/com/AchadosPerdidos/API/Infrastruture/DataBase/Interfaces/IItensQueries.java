package com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Itens;
import java.util.List;

public interface IItensQueries {

    List<Itens> findByCampus(int campusId);
    boolean marcarComoDevolvido(int itemId, int usuarioReivindicadorId);
    boolean marcarComoResgatado(int itemId, int usuarioReivindicadorId);
    
}
