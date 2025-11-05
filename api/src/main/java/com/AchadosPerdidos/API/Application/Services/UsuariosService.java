package com.AchadosPerdidos.API.Application.Services;

import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosListDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosUpdateDTO;
import com.AchadosPerdidos.API.Application.Mapper.UsuariosModelMapper;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IUsuariosService;
import com.AchadosPerdidos.API.Domain.Entity.Usuarios;
import com.AchadosPerdidos.API.Domain.Repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UsuariosService implements IUsuariosService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private UsuariosModelMapper usuariosModelMapper;

    @Override
    public UsuariosListDTO getAllUsuarios() {
        List<Usuarios> usuarios = usuariosRepository.findAll();
        return usuariosModelMapper.toListDTO(usuarios);
    }

    @Override
    public UsuariosListDTO getUsuarioById(int id) {
        Usuarios usuarios = usuariosRepository.findById(id);
        if (usuarios == null) {
            return null;
        }
        return usuariosModelMapper.toListDTO(usuarios);
    }

    @Override
    public UsuariosDTO getUsuarioByEmail(String email) {
        Usuarios usuarios = usuariosRepository.findByEmail(email);
        if (usuarios == null) {
            return null;
        }
        return usuariosModelMapper.toDTO(usuarios);
    }

    @Override
    public UsuariosCreateDTO createUsuario(UsuariosCreateDTO usuariosCreateDTO) {
        Usuarios usuarios = usuariosModelMapper.toEntity(usuariosCreateDTO);

        usuarios.setDtaCriacao(new Date());
        usuarios.setFlgInativo(false);
        
        Usuarios savedUsuarios = usuariosRepository.save(usuarios);
        return usuariosModelMapper.toCreateDTO(savedUsuarios);
    }

    @Override
    public UsuariosUpdateDTO updateUsuario(int id, UsuariosUpdateDTO usuariosUpdateDTO) {
        Usuarios existingUsuarios = usuariosRepository.findById(id);
        if (existingUsuarios == null) {
            return null;
        }
        
        usuariosModelMapper.updateEntityFromUpdateDTO(existingUsuarios, usuariosUpdateDTO);
        Usuarios updatedUsuarios = usuariosRepository.save(existingUsuarios);
        return usuariosModelMapper.toUpdateDTO(updatedUsuarios);
    }

    @Override
    public boolean deleteUsuario(int id) {
        Usuarios usuarios = usuariosRepository.findById(id);
        if (usuarios == null) {
            return false;
        }
        
        return usuariosRepository.deleteById(id);
    }
}