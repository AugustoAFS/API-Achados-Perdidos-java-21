package com.AchadosPerdidos.API.Application.Services;

import com.AchadosPerdidos.API.Application.DTOs.Campus.CampusDTO;
import com.AchadosPerdidos.API.Application.DTOs.Campus.CampusListDTO;
import com.AchadosPerdidos.API.Application.DTOs.Campus.CampusCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Campus.CampusUpdateDTO;
import com.AchadosPerdidos.API.Application.Mapper.CampusModelMapper;
import com.AchadosPerdidos.API.Application.Services.Interfaces.ICampusService;
import com.AchadosPerdidos.API.Domain.Entity.Campus;
import com.AchadosPerdidos.API.Domain.Repository.CampusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CampusService implements ICampusService {

    @Autowired
    private CampusRepository campusRepository;

    @Autowired
    private CampusModelMapper campusModelMapper;

    @Override
    public CampusListDTO getAllCampus() {
        List<Campus> campus = campusRepository.findAll();
        return campusModelMapper.toListDTO(campus);
    }

    @Override
    public CampusDTO getCampusById(int id) {
        Campus campus = campusRepository.findById(id);
        return campusModelMapper.toDTO(campus);
    }

    @Override
    public CampusDTO createCampus(CampusDTO campusDTO) {
        Campus campus = campusModelMapper.toEntity(campusDTO);
        campus.setDtaCriacao(new Date());
        campus.setFlgInativo(false);
        
        Campus savedCampus = campusRepository.save(campus);
        return campusModelMapper.toDTO(savedCampus);
    }

    @Override
    public CampusDTO createCampusFromDTO(CampusCreateDTO createDTO) {
        Campus campus = new Campus();
        campus.setNome(createDTO.getNome());
        campus.setInstituicaoId(createDTO.getInstituicaoId());
        campus.setEnderecoId(createDTO.getEnderecoId());
        campus.setDtaCriacao(new Date());
        campus.setFlgInativo(false);
        
        Campus savedCampus = campusRepository.save(campus);
        return campusModelMapper.toDTO(savedCampus);
    }

    @Override
    public CampusDTO updateCampus(int id, CampusDTO campusDTO) {
        Campus existingCampus = campusRepository.findById(id);
        if (existingCampus == null) {
            return null;
        }
        
        existingCampus.setNome(campusDTO.getNome());
        existingCampus.setInstituicaoId(campusDTO.getInstituicaoId());
        existingCampus.setEnderecoId(campusDTO.getEnderecoId());
        existingCampus.setFlgInativo(campusDTO.getFlgInativo());
        existingCampus.setDtaRemocao(campusDTO.getDtaRemocao());
        
        Campus updatedCampus = campusRepository.save(existingCampus);
        return campusModelMapper.toDTO(updatedCampus);
    }

    @Override
    public CampusDTO updateCampusFromDTO(int id, CampusUpdateDTO updateDTO) {
        Campus existingCampus = campusRepository.findById(id);
        if (existingCampus == null) {
            return null;
        }
        
        // Atualizar apenas os campos fornecidos
        if (updateDTO.getNome() != null) {
            existingCampus.setNome(updateDTO.getNome());
        }
        if (updateDTO.getInstituicaoId() != null) {
            existingCampus.setInstituicaoId(updateDTO.getInstituicaoId());
        }
        if (updateDTO.getEnderecoId() != null) {
            existingCampus.setEnderecoId(updateDTO.getEnderecoId());
        }
        if (updateDTO.getFlgInativo() != null) {
            existingCampus.setFlgInativo(updateDTO.getFlgInativo());
        }
        
        Campus updatedCampus = campusRepository.save(existingCampus);
        return campusModelMapper.toDTO(updatedCampus);
    }

    @Override
    public boolean deleteCampus(int id) {
        Campus campus = campusRepository.findById(id);
        if (campus == null) {
            return false;
        }
        
        return campusRepository.deleteById(id);
    }

    @Override
    public CampusListDTO getActiveCampus() {
        List<Campus> activeCampus = campusRepository.findActive();
        return campusModelMapper.toListDTO(activeCampus);
    }

    @Override
    public CampusListDTO getCampusByInstitution(int institutionId) {
        List<Campus> campus = campusRepository.findByInstitution(institutionId);
        return campusModelMapper.toListDTO(campus);
    }
}
