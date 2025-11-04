package com.AchadosPerdidos.API.Application.Services.Interfaces;

import com.AchadosPerdidos.API.Application.DTOs.Local.LocalDTO;
import com.AchadosPerdidos.API.Application.DTOs.Local.LocalCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Local.LocalUpdateDTO;

import java.util.List;

public interface ILocalService {
    List<LocalDTO> getAllLocais();
    LocalDTO getLocalById(Integer id);
    LocalDTO createLocal(LocalCreateDTO createDTO);
    LocalDTO updateLocal(Integer id, LocalUpdateDTO updateDTO);
    LocalDTO deleteLocal(Integer id);
    List<LocalDTO> getLocaisByCampus(Integer campusId);
    List<LocalDTO> getActiveLocais();
}

