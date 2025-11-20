package com.AchadosPerdidos.API.Domain.Repository.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Itens;
import com.AchadosPerdidos.API.Domain.Enum.Status_Item;
import java.util.List;

/**
 * Interface do Repository para Itens (Achados e Perdidos)
 * Define operações de persistência para entidade Itens
 *
 * REGRAS DE NEGÓCIO:
 * - Tipo: ACHADO (foto obrigatória) ou PERDIDO (foto opcional)
 * - Status: ATIVO, DEVOLVIDO, RESGATADO, CANCELADO
 */
public interface IItensRepository {
    // Operações CRUD básicas
    List<Itens> findAll();
    Itens findById(int id);
    Itens save(Itens itens);
    boolean deleteById(int id);

    // Buscas específicas
    List<Itens> findActive();
    List<Itens> findByUser(int userId);
    List<Itens> findByCampus(int campusId);
    List<Itens> searchByTerm(String searchTerm);
    List<Itens> findByTipo(String tipo);

    // Operações de Status (Regras de Negócio)
    boolean marcarComoDevolvido(int itemId, int usuarioReivindicadorId);
    boolean marcarComoResgatado(int itemId, int usuarioReivindicadorId);
    List<Itens> findByStatus(Status_Item status);
}
