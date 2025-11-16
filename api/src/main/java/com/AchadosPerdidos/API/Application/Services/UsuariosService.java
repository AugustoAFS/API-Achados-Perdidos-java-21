package com.AchadosPerdidos.API.Application.Services;

import com.AchadosPerdidos.API.Application.DTOs.Auth.AuthResponseDTO;
import com.AchadosPerdidos.API.Application.DTOs.Auth.LoginRequestDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosListDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosUpdateDTO;
import com.AchadosPerdidos.API.Application.Mapper.UsuariosModelMapper;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IJwtTokenService;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IUsuariosService;
import com.AchadosPerdidos.API.Exeptions.BusinessException;
import com.AchadosPerdidos.API.Domain.Entity.Usuarios;
import com.AchadosPerdidos.API.Domain.Repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UsuariosService implements IUsuariosService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private UsuariosModelMapper usuariosModelMapper;

    @Autowired
    private IJwtTokenService jwtTokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.expiry-in-minutes:60}")
    private int jwtExpiryInMinutes;

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

    @Override
    public AuthResponseDTO login(LoginRequestDTO loginRequest) {
        if (loginRequest == null || loginRequest.getEmail() == null || loginRequest.getSenha() == null) {
            throw new BusinessException("Email e senha são obrigatórios");
        }

        Usuarios usuario = usuariosRepository.findByEmail(loginRequest.getEmail());
        if (usuario == null) {
            throw new BusinessException("Email ou senha inválidos");
        }

        if (usuario.getFlgInativo() != null && usuario.getFlgInativo()) {
            throw new BusinessException("Usuário inativo");
        }

        if (usuario.getHashSenha() == null || usuario.getHashSenha().trim().isEmpty()) {
            throw new BusinessException("Usuário sem senha cadastrada");
        }

        if (!passwordEncoder.matches(loginRequest.getSenha(), usuario.getHashSenha())) {
            throw new BusinessException("Email ou senha inválidos");
        }

        String role = "USER";
        String token = jwtTokenService.generateToken(
            usuario.getEmail(),
            usuario.getNomeCompleto(),
            role,
            String.valueOf(usuario.getId())
        );

        long expiresIn = jwtExpiryInMinutes * 60L;

        return new AuthResponseDTO(
            token,
            "Bearer",
            expiresIn,
            usuario.getId(),
            usuario.getNomeCompleto(),
            usuario.getEmail(),
            role,
            null
        );
    }

    @Override
    public boolean redefinirSenha(int id, String novaSenha) {
        if (novaSenha == null || novaSenha.trim().isEmpty()) {
            throw new BusinessException("A nova senha não pode ser vazia");
        }

        Usuarios usuario = usuariosRepository.findById(id);
        if (usuario == null) {
            throw new BusinessException("Usuário não encontrado");
        }

        // Gera um novo hash BCrypt para a senha
        String hashSenha = passwordEncoder.encode(novaSenha);
        usuario.setHashSenha(hashSenha);
        
        Usuarios updatedUsuario = usuariosRepository.save(usuario);
        return updatedUsuario != null;
    }
}