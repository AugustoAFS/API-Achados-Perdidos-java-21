package com.AchadosPerdidos.API.Application.Services;

import com.AchadosPerdidos.API.Application.DTOs.FotoUsuario.FotoUsuarioListDTO;
import com.AchadosPerdidos.API.Application.Mapper.FotoUsuarioMapper;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IFotoUsuarioService;
import com.AchadosPerdidos.API.Domain.Entity.FotoUsuario;
import com.AchadosPerdidos.API.Domain.Repository.FotoUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class FotoUsuarioService implements IFotoUsuarioService {

    @Autowired
    private FotoUsuarioRepository fotoUsuarioRepository;

    @Autowired
    private FotoUsuarioMapper fotoUsuarioMapper;

    @Override
    @Cacheable(value = "fotosUsuario", key = "'all'")
    public FotoUsuarioListDTO getAllFotosUsuario() {
        return fotoUsuarioMapper.toListDTO(fotoUsuarioRepository.findAll());
    }

    @Override
    @Cacheable(value = "fotosUsuario", key = "'active'")
    public FotoUsuarioListDTO getActiveFotosUsuario() {
        return fotoUsuarioMapper.toListDTO(fotoUsuarioRepository.findActive());
    }
}

