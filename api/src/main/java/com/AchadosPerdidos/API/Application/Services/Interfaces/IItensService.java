package com.AchadosPerdidos.API.Application.Services.Interfaces;

import com.AchadosPerdidos.API.Application.DTOs.Itens.ItensDTO;
import com.AchadosPerdidos.API.Application.DTOs.Itens.ItensListDTO;
import com.AchadosPerdidos.API.Application.DTOs.Itens.ItensCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Itens.ItensUpdateDTO;
import java.util.List;

public interface IItensService {
    ItensListDTO getAllItens();
    ItensDTO getItemById(int id);
    ItensDTO createItem(ItensDTO itensDTO);
    ItensDTO updateItem(int id, ItensDTO itensDTO);
    boolean deleteItem(int id);
    ItensListDTO getActiveItens();
    ItensListDTO getItensByStatus(int statusId);
    ItensListDTO getItensByUser(int userId);
    ItensListDTO getItensByCampus(int campusId);
    ItensListDTO getItensByLocal(int localId);
    ItensListDTO getItensByEmpresa(int empresaId);
    ItensListDTO searchItens(String searchTerm);
    ItensDTO createItemFromDTO(ItensCreateDTO createDTO);
    ItensDTO updateItemFromDTO(int id, ItensUpdateDTO updateDTO);
    
    // Métodos para sistema de notificações e controle de prazos
    List<com.AchadosPerdidos.API.Domain.Entity.Itens> getItemsNearDonationDeadline(int daysFromNow);
    List<com.AchadosPerdidos.API.Domain.Entity.Itens> getExpiredItems(int daysExpired);
    boolean markItemAsDonated(int itemId);
    com.AchadosPerdidos.API.Domain.Entity.Itens getItemEntityById(int id);
}
