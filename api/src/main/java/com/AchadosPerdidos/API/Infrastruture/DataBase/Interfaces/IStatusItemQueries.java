package com.AchadosPerdidos.API.Infrastruture.DataBase.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.StatusItem;
import java.util.List;

public interface IStatusItemQueries {
    List<StatusItem> findAll();
    StatusItem findById(Integer id);
    StatusItem findByNome(String nome);
    StatusItem insert(StatusItem statusItem);
    StatusItem update(StatusItem statusItem);
    boolean deleteById(Integer id);
    List<StatusItem> findActive();
}

