package com.AchadosPerdidos.API.Application.Services;

import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosListDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosUpdateDTO;
import com.AchadosPerdidos.API.Application.Mapper.UsuariosModelMapper;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IUsuariosService;
import com.AchadosPerdidos.API.Domain.Entity.Usuarios;
import com.AchadosPerdidos.API.Domain.Repository.UsuariosRepository;
import com.AchadosPerdidos.API.Exeptions.BusinessException;
import com.AchadosPerdidos.API.Exeptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class UsuariosService implements IUsuariosService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private UsuariosModelMapper usuariosModelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Cacheable(value = "usuarios", key = "'all'")
    public UsuariosListDTO getAllUsuarios() {
        List<Usuarios> usuarios = usuariosRepository.findAll();
        return usuariosModelMapper.toListDTO(usuarios);
    }

    @Override
    @Cacheable(value = "usuarios", key = "#id")
    public UsuariosListDTO getUsuarioById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID do usuário deve ser válido");
        }
        
        Usuarios usuarios = usuariosRepository.findById(id);
        if (usuarios == null) {
            throw new ResourceNotFoundException("Usuário não encontrado com ID: " + id);
        }
        return usuariosModelMapper.toListDTO(usuarios);
    }

    @Override
    @Cacheable(value = "usuarios", key = "'email_' + #email")
    public UsuariosDTO getUsuarioByEmail(String email) {
        if (!StringUtils.hasText(email)) {
            throw new IllegalArgumentException("Email não pode ser vazio");
        }
        
        Usuarios usuarios = usuariosRepository.findByEmail(email);
        if (usuarios == null) {
            throw new ResourceNotFoundException("Usuário não encontrado com email: " + email);
        }
        return usuariosModelMapper.toDTO(usuarios);
    }

    @Override
    @CacheEvict(value = "usuarios", allEntries = true)
    public UsuariosCreateDTO createUsuario(UsuariosCreateDTO usuariosCreateDTO) {
        if (usuariosCreateDTO == null) {
            throw new IllegalArgumentException("Dados do usuário não podem ser nulos");
        }
        
        if (StringUtils.hasText(usuariosCreateDTO.getEmail())) {
            Usuarios usuarioExistente = usuariosRepository.findByEmail(usuariosCreateDTO.getEmail());
            if (usuarioExistente != null) {
                throw new BusinessException("Já existe um usuário cadastrado com o email: " + usuariosCreateDTO.getEmail());
            }
        }
        
        if (StringUtils.hasText(usuariosCreateDTO.getCpf())) {
            List<Usuarios> usuariosComMesmoCpf = usuariosRepository.findAll().stream()
                .filter(u -> usuariosCreateDTO.getCpf().equals(u.getCpf()))
                .toList();
            
            if (!usuariosComMesmoCpf.isEmpty()) {
                throw new BusinessException("Já existe um usuário cadastrado com o CPF informado");
            }
        }
        
        Usuarios usuarios = usuariosModelMapper.toEntity(usuariosCreateDTO);
        
        if (StringUtils.hasText(usuarios.getHashSenha())) {
            usuarios.setHashSenha(passwordEncoder.encode(usuarios.getHashSenha()));
        }
        
        usuarios.setDtaCriacao(new Date());
        usuarios.setFlgInativo(false);
        
        usuarios.validate();
        
        Usuarios savedUsuarios = usuariosRepository.save(usuarios);
        return usuariosModelMapper.toCreateDTO(savedUsuarios);
    }

    @Override
    @CacheEvict(value = "usuarios", allEntries = true)
    public UsuariosUpdateDTO updateUsuario(int id, UsuariosUpdateDTO usuariosUpdateDTO) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID do usuário deve ser válido");
        }
        
        if (usuariosUpdateDTO == null) {
            throw new IllegalArgumentException("Dados de atualização não podem ser nulos");
        }
        
        Usuarios existingUsuarios = usuariosRepository.findById(id);
        if (existingUsuarios == null) {
            throw new ResourceNotFoundException("Usuário não encontrado com ID: " + id);
        }
        
        if (!existingUsuarios.isAtivo()) {
            throw new BusinessException("Não é possível atualizar um usuário inativo");
        }
        
        if (StringUtils.hasText(usuariosUpdateDTO.getEmail()) && 
            !usuariosUpdateDTO.getEmail().equals(existingUsuarios.getEmail())) {
            Usuarios usuarioComMesmoEmail = usuariosRepository.findByEmail(usuariosUpdateDTO.getEmail());
            if (usuarioComMesmoEmail != null && !usuarioComMesmoEmail.getId().equals(id)) {
                throw new BusinessException("Já existe outro usuário com o email: " + usuariosUpdateDTO.getEmail());
            }
        }
        
        if (StringUtils.hasText(usuariosUpdateDTO.getCpf()) && 
            !usuariosUpdateDTO.getCpf().equals(existingUsuarios.getCpf())) {
            List<Usuarios> usuariosComMesmoCpf = usuariosRepository.findAll().stream()
                .filter(u -> usuariosUpdateDTO.getCpf().equals(u.getCpf()) && !u.getId().equals(id))
                .toList();
            
            if (!usuariosComMesmoCpf.isEmpty()) {
                throw new BusinessException("Já existe outro usuário com o CPF informado");
            }
        }
        
        usuariosModelMapper.updateEntityFromUpdateDTO(existingUsuarios, usuariosUpdateDTO);
        
        existingUsuarios.validate();
        
        Usuarios updatedUsuarios = usuariosRepository.save(existingUsuarios);
        return usuariosModelMapper.toUpdateDTO(updatedUsuarios);
    }

    @Override
    @CacheEvict(value = "usuarios", allEntries = true)
    public boolean deleteUsuario(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID do usuário deve ser válido");
        }
        
        Usuarios usuarios = usuariosRepository.findById(id);
        if (usuarios == null) {
            throw new ResourceNotFoundException("Usuário não encontrado com ID: " + id);
        }
        
        if (!usuarios.isAtivo()) {
            throw new BusinessException("O usuário já está inativo");
        }
        
        usuarios.marcarComoInativo();
        usuariosRepository.save(usuarios);
        
        return true;
    }
    
    @Cacheable(value = "usuarios", key = "'active'")
    public List<UsuariosDTO> getActiveUsuarios() {
        List<Usuarios> activeUsuarios = usuariosRepository.findActive();
        return activeUsuarios.stream()
            .map(usuariosModelMapper::toDTO)
            .toList();
    }
    
    public UsuariosDTO validateLogin(String email, String senhaPlainText) {
        if (!StringUtils.hasText(email) || !StringUtils.hasText(senhaPlainText)) {
            throw new BusinessException("Email e senha são obrigatórios");
        }
        
        Usuarios usuario = usuariosRepository.findByEmail(email);
        if (usuario == null) {
            throw new BusinessException("Credenciais inválidas");
        }
        
        if (!usuario.isAtivo()) {
            throw new BusinessException("Usuário inativo. Entre em contato com o administrador.");
        }
        
        if (!passwordEncoder.matches(senhaPlainText, usuario.getHashSenha())) {
            throw new BusinessException("Credenciais inválidas");
        }
        
        return usuariosModelMapper.toDTO(usuario);
    }
    
    @CacheEvict(value = "usuarios", allEntries = true)
    public boolean alterarSenha(int id, String senhaAtual, String novaSenha) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID do usuário deve ser válido");
        }
        
        if (!StringUtils.hasText(senhaAtual) || !StringUtils.hasText(novaSenha)) {
            throw new BusinessException("Senha atual e nova senha são obrigatórias");
        }
        
        if (novaSenha.length() < 6) {
            throw new BusinessException("Nova senha deve ter no mínimo 6 caracteres");
        }
        
        Usuarios usuario = usuariosRepository.findById(id);
        if (usuario == null) {
            throw new ResourceNotFoundException("Usuário não encontrado com ID: " + id);
        }
        
        if (!usuario.isAtivo()) {
            throw new BusinessException("Não é possível alterar senha de usuário inativo");
        }
        
        if (!passwordEncoder.matches(senhaAtual, usuario.getHashSenha())) {
            throw new BusinessException("Senha atual incorreta");
        }
        
        usuario.setHashSenha(passwordEncoder.encode(novaSenha));
        usuariosRepository.save(usuario);
        
        return true;
    }
}
