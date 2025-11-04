package com.AchadosPerdidos.API.Application.Services.Interfaces;

import com.AchadosPerdidos.API.Application.DTOs.StatusItem.StatusItemDTO;
import com.AchadosPerdidos.API.Application.DTOs.StatusItem.StatusItemCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.StatusItem.StatusItemUpdateDTO;

import java.util.List;

public interface IStatusItemService {
    List<StatusItemDTO> getAllStatus();
    StatusItemDTO getStatusById(Integer id);
    StatusItemDTO getStatusByNome(String nome);
    StatusItemDTO createStatus(StatusItemCreateDTO createDTO);
    StatusItemDTO updateStatus(Integer id, StatusItemUpdateDTO updateDTO);
    StatusItemDTO deleteStatus(Integer id);
    List<StatusItemDTO> getActiveStatus();
}

