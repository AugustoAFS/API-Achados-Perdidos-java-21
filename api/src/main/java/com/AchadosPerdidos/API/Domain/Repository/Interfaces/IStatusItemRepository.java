package com.AchadosPerdidos.API.Domain.Repository.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.StatusItem;
import java.util.List;

public interface IStatusItemRepository {
    List<StatusItem> findAll();
    StatusItem findById(Integer id);
    StatusItem findByNome(String nome);
    StatusItem save(StatusItem statusItem);
    boolean deleteById(Integer id);
    List<StatusItem> findActive();
}

