package com.AchadosPerdidos.API.Application.Services.Interfaces;

import com.AchadosPerdidos.API.Application.DTOs.Campus.CampusDTO;
import com.AchadosPerdidos.API.Application.DTOs.Campus.CampusListDTO;
import com.AchadosPerdidos.API.Application.DTOs.Campus.CampusCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Campus.CampusUpdateDTO;

public interface ICampusService {
    CampusListDTO getAllCampus();
    CampusDTO getCampusById(int id);
    CampusDTO createCampus(CampusDTO campusDTO);
    CampusDTO updateCampus(int id, CampusDTO campusDTO);
    boolean deleteCampus(int id);
    CampusListDTO getActiveCampus();
    CampusListDTO getCampusByInstitution(int institutionId);
    CampusDTO createCampusFromDTO(CampusCreateDTO createDTO);
    CampusDTO updateCampusFromDTO(int id, CampusUpdateDTO updateDTO);
}
