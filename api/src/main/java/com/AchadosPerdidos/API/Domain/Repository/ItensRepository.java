package com.AchadosPerdidos.API.Domain.Repository;

import com.AchadosPerdidos.API.Domain.Entity.Itens;
import com.AchadosPerdidos.API.Domain.Enum.Status_Item;
import com.AchadosPerdidos.API.Domain.Enum.Tipo_Item;
import com.AchadosPerdidos.API.Domain.Repository.Interfaces.IItensRepository;
import com.AchadosPerdidos.API.Infrastruture.DataBase.ItensQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Repository para gerenciar Itens (Achados e Perdidos)
 * Usa JPA para CRUD básico e Queries apenas para operações complexas com JOINs
 *
 * REGRAS DE NEGÓCIO:
 * - ACHADO: Item encontrado (foto obrigatória)
 * - PERDIDO: Item perdido (foto opcional)
 * - Status: ATIVO, DEVOLVIDO, RESGATADO, CANCELADO
 */
@Repository
public class ItensRepository implements IItensRepository {

    @PersistenceContext
    private EntityManager entityManager;
    
    @Autowired
    private ItensQueries itensQueries; // Apenas para operações com JOINs

    // CRUD básico usando JPA
    @Override
    public List<Itens> findAll() {
        return entityManager.createQuery("SELECT i FROM Itens i ORDER BY i.Dta_Criacao DESC", Itens.class).getResultList();
    }

    @Override
    public Itens findById(int id) {
        return entityManager.find(Itens.class, id);
    }

    @Override
    @Transactional
    public Itens save(Itens itens) {
        if (itens.getId() == null || itens.getId() == 0) {
            entityManager.persist(itens);
            return itens;
        } else {
            return entityManager.merge(itens);
        }
    }

    @Override
    @Transactional
    public boolean deleteById(int id) {
        Itens itens = entityManager.find(Itens.class, id);
        if (itens != null) {
            entityManager.remove(itens);
            return true;
        }
        return false;
    }

    @Override
    public List<Itens> findActive() {
        return entityManager.createQuery(
            "SELECT i FROM Itens i WHERE i.Flg_Inativo = false ORDER BY i.Dta_Criacao DESC", Itens.class).getResultList();
    }

    // Queries - delegadas para ItensQueries
    @Override
    public List<Itens> findByUser(int userId) {
        return itensQueries.findByUser(userId);
    }

    @Override
    public List<Itens> findByTipo(String tipo) {
        return itensQueries.findByTipo(tipo);
    }

    @Override
    public List<Itens> findByStatus(Status_Item status) {
        return itensQueries.findByStatus(status);
    }

    @Override
    public List<Itens> searchByTerm(String searchTerm) {
        return itensQueries.searchByTerm(searchTerm);
    }

    @Override
    public List<Itens> findByCampus(int campusId) {
        return itensQueries.findByCampus(campusId);
    }

    @Override
    public boolean marcarComoDevolvido(int itemId, int usuarioReivindicadorId) {
        return itensQueries.marcarComoDevolvido(itemId, usuarioReivindicadorId);
    }

    @Override
    public boolean marcarComoResgatado(int itemId, int usuarioReivindicadorId) {
        return itensQueries.marcarComoResgatado(itemId, usuarioReivindicadorId);
    }
}
