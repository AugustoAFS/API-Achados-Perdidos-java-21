package com.AchadosPerdidos.API.Application.Services.Interfaces;

import com.AchadosPerdidos.API.Application.DTOs.Campus.CampusDTO;
import com.AchadosPerdidos.API.Application.DTOs.Campus.CampusListDTO;
import com.AchadosPerdidos.API.Application.DTOs.Campus.CampusCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Campus.CampusUpdateDTO;

public interface ICampusService {
    CampusDTO createCampus(CampusCreateDTO createDTO);
    CampusDTO updateCampus(int id, CampusUpdateDTO updateDTO);
    CampusListDTO getAllCampus();
    CampusDTO getCampusById(int id);
    CampusListDTO getActiveCampus();
    CampusListDTO getCampusByInstitution(int institutionId);
    boolean deleteCampus(int id);
}
