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
    public UsuariosDTO getUsuarioById(int id) {
        Usuarios usuarios = usuariosRepository.findById(id);
        return usuariosModelMapper.toDTO(usuarios);
    }

    @Override
    public UsuariosDTO getUsuarioByEmail(String email) {
        Usuarios usuarios = usuariosRepository.findByEmail(email);
        return usuariosModelMapper.toDTO(usuarios);
    }

    @Override
    public UsuariosDTO createUsuario(UsuariosDTO usuariosDTO) {
        Usuarios usuarios = usuariosModelMapper.toEntity(usuariosDTO);
        if(UsuariosDTO.empresaid == 0 || UsuariosDTO.EmpresaId == null)
        {

        }
        usuarios.setDtaCriacao(new Date());
        usuarios.setFlgInativo(false);
        
        Usuarios savedUsuarios = usuariosRepository.save(usuarios);
        return usuariosModelMapper.toDTO(savedUsuarios);
    }

    @Override
    public UsuariosUpdateDTO updateUsuario(int id, UsuariosUpdateDTO usuariosDTO) {
        Usuarios existingUsuarios = usuariosRepository.findById(id);
        if (existingUsuarios == null) {
            return null;
        }
        
        existingUsuarios.setNomeCompleto(usuariosDTO.getNomeCompleto());
        existingUsuarios.setCpf(usuariosDTO.getCpf());
        existingUsuarios.setEmail(usuariosDTO.getEmail());
        existingUsuarios.setHashSenha(usuariosDTO.getHashSenha());
        existingUsuarios.setMatricula(usuariosDTO.getMatricula());
        existingUsuarios.setNumeroTelefone(usuariosDTO.getNumeroTelefone());
        existingUsuarios.setEmpresaId(usuariosDTO.getEmpresaId());
        existingUsuarios.setEnderecoId(usuariosDTO.getEnderecoId());
        existingUsuarios.setFlgInativo(usuariosDTO.getFlgInativo());
        existingUsuarios.setDtaRemocao(usuariosDTO.getDtaRemocao());
        
        Usuarios updatedUsuarios = usuariosRepository.save(existingUsuarios);
        return usuariosModelMapper.toDTO(updatedUsuarios);
    }

    @Override
    public boolean deleteUsuario(int id) {
        Usuarios usuarios = usuariosRepository.findById(id);
        if (usuarios == null) {
            return false;
        }
        
        return usuariosRepository.deleteById(id);
    }

    @Override
    public UsuariosListDTO getActiveUsuarios() {
        List<Usuarios> activeUsuarios = usuariosRepository.findActive();
        return usuariosModelMapper.toListDTO(activeUsuarios);
    }

    @Override
    public UsuariosListDTO getUsuariosByRole(int tipoRoleId) {
        List<Usuarios> usuarios = usuariosRepository.findByRole(tipoRoleId);
        return usuariosModelMapper.toListDTO(usuarios);
    }

    @Override
    public UsuariosListDTO getUsuariosByInstitution(int instituicaoId) {
        List<Usuarios> usuarios = usuariosRepository.findByInstitution(instituicaoId);
        return usuariosModelMapper.toListDTO(usuarios);
    }

    @Override
    public UsuariosListDTO getUsuariosByCampus(int campusId) {
        List<Usuarios> usuarios = usuariosRepository.findByCampus(campusId);
        return usuariosModelMapper.toListDTO(usuarios);
    }

    @Override
    public UsuariosDTO authenticateUsuario(String email, String senha) {
        Usuarios usuarios = usuariosRepository.findByEmailAndPassword(email, senha);
        return usuariosModelMapper.toDTO(usuarios);
    }
    
    @Override
    public UsuariosDTO createUsuarioFromDTO(UsuariosCreateDTO createDTO) {
        Usuarios usuarios = new Usuarios();
        usuarios.setNomeCompleto(createDTO.getNomeCompleto());
        usuarios.setCpf(createDTO.getCpf());
        usuarios.setEmail(createDTO.getEmail());
        // TODO: Hashear a senha antes de salvar
        usuarios.setHashSenha(createDTO.getSenha()); // Por enquanto salva texto plano - precisa hashear
        usuarios.setMatricula(createDTO.getMatricula());
        usuarios.setNumeroTelefone(createDTO.getNumeroTelefone());
        usuarios.setEmpresaId(createDTO.getEmpresaId());
        usuarios.setEnderecoId(createDTO.getEnderecoId());
        usuarios.setDtaCriacao(new Date());
        usuarios.setFlgInativo(false);
        
        Usuarios savedUsuarios = usuariosRepository.save(usuarios);
        return usuariosModelMapper.toDTO(savedUsuarios);
    }
    
    @Override
    public UsuariosDTO updateUsuarioFromDTO(int id, UsuariosUpdateDTO updateDTO) {
        Usuarios existingUsuarios = usuariosRepository.findById(id);
        if (existingUsuarios == null) {
            return null;
        }
        
        // Atualizar apenas os campos fornecidos
        if (updateDTO.getNomeCompleto() != null) {
            existingUsuarios.setNomeCompleto(updateDTO.getNomeCompleto());
        }
        if (updateDTO.getCpf() != null) {
            existingUsuarios.setCpf(updateDTO.getCpf());
        }
        if (updateDTO.getEmail() != null) {
            existingUsuarios.setEmail(updateDTO.getEmail());
        }
        if (updateDTO.getSenha() != null) {
            // TODO: Hashear a senha antes de salvar
            existingUsuarios.setHashSenha(updateDTO.getSenha()); // Por enquanto salva texto plano - precisa hashear
        }
        if (updateDTO.getMatricula() != null) {
            existingUsuarios.setMatricula(updateDTO.getMatricula());
        }
        if (updateDTO.getNumeroTelefone() != null) {
            existingUsuarios.setNumeroTelefone(updateDTO.getNumeroTelefone());
        }
        if (updateDTO.getEmpresaId() != null) {
            existingUsuarios.setEmpresaId(updateDTO.getEmpresaId());
        }
        if (updateDTO.getEnderecoId() != null) {
            existingUsuarios.setEnderecoId(updateDTO.getEnderecoId());
        }
        if (updateDTO.getFlgInativo() != null) {
            existingUsuarios.setFlgInativo(updateDTO.getFlgInativo());
        }
        
        Usuarios updatedUsuarios = usuariosRepository.save(existingUsuarios);
        return usuariosModelMapper.toDTO(updatedUsuarios);
    }
}