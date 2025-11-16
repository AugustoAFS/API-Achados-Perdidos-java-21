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
import com.AchadosPerdidos.API.Domain.Entity.Usuarios;
import com.AchadosPerdidos.API.Domain.Repository.UsuariosRepository;
import com.AchadosPerdidos.API.Exeptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
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
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IJwtTokenService jwtTokenService;

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
        // Validação da senha
        if (usuariosCreateDTO.getSenha() == null || usuariosCreateDTO.getSenha().trim().isEmpty()) {
            throw new BusinessException("A senha é obrigatória");
        }

        // Converter DTO para entidade (sem hash ainda)
        Usuarios usuarios = usuariosModelMapper.toEntity(usuariosCreateDTO);

        // Gerar hash BCrypt da senha no serviço
        String hashSenha = passwordEncoder.encode(usuariosCreateDTO.getSenha());
        usuarios.setHashSenha(hashSenha);

        usuarios.setDtaCriacao(new Date());
        usuarios.setFlgInativo(false);
        
        Usuarios savedUsuarios = usuariosRepository.save(usuarios);
        
        // Associar usuário ao campus se fornecido
        if (usuariosCreateDTO.getCampusId() != null && usuariosCreateDTO.getCampusId() > 0) {
            usuariosRepository.associarUsuarioCampus(savedUsuarios.getId(), usuariosCreateDTO.getCampusId());
        }
        
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
    public boolean redefinirSenha(String cpf, String matricula, String novaSenha) {
        // Validação da nova senha
        if (novaSenha == null || novaSenha.trim().isEmpty()) {
            throw new BusinessException("A nova senha não pode ser vazia");
        }

        // Validação: deve fornecer CPF ou matrícula (pelo menos um)
        if ((cpf == null || cpf.trim().isEmpty()) && (matricula == null || matricula.trim().isEmpty())) {
            throw new BusinessException("É necessário fornecer CPF ou matrícula para redefinir a senha");
        }

        // Buscar usuário por CPF ou matrícula
        Usuarios usuario = null;
        if (cpf != null && !cpf.trim().isEmpty()) {
            usuario = usuariosRepository.findByCpf(cpf.trim());
            if (usuario == null) {
                throw new BusinessException("Usuário não encontrado com o CPF fornecido");
            }
        } else if (matricula != null && !matricula.trim().isEmpty()) {
            usuario = usuariosRepository.findByMatricula(matricula.trim());
            if (usuario == null) {
                throw new BusinessException("Usuário não encontrado com a matrícula fornecida");
            }
        }

        if (usuario == null) {
            throw new BusinessException("Usuário não encontrado");
        }

        // Gera um novo hash BCrypt para a senha
        String hashSenha = passwordEncoder.encode(novaSenha);
        usuario.setHashSenha(hashSenha);
        
        Usuarios updatedUsuario = usuariosRepository.save(usuario);
        return updatedUsuario != null;
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO loginRequestDTO) {
        // Validação dos campos de entrada
        if (loginRequestDTO.getEmail() == null || loginRequestDTO.getEmail().trim().isEmpty()) {
            throw new BusinessException("Email é obrigatório");
        }
        if (loginRequestDTO.getSenha() == null || loginRequestDTO.getSenha().trim().isEmpty()) {
            throw new BusinessException("Senha é obrigatória");
        }

        // Buscar usuário pelo email
        Usuarios usuario = usuariosRepository.findByEmail(loginRequestDTO.getEmail());
        if (usuario == null) {
            throw new BusinessException("Email ou senha inválidos");
        }

        // Verificar se o usuário está ativo
        if (usuario.getFlgInativo() != null && usuario.getFlgInativo()) {
            throw new BusinessException("Usuário inativo");
        }

        // Verificar senha usando BCrypt
        if (usuario.getHashSenha() == null || usuario.getHashSenha().trim().isEmpty()) {
            throw new BusinessException("Usuário não possui senha cadastrada. Use o endpoint de redefinir senha.");
        }

        boolean senhaValida = passwordEncoder.matches(loginRequestDTO.getSenha(), usuario.getHashSenha());
        if (!senhaValida) {
            throw new BusinessException("Email ou senha inválidos");
        }

        // Buscar campus ativo do usuário
        String campusNome = usuariosRepository.getCampusNomeAtivoByUsuarioId(usuario.getId());

        // Gerar token JWT
        String token = jwtTokenService.generateToken(
            usuario.getEmail(),
            usuario.getNomeCompleto() != null ? usuario.getNomeCompleto() : "",
            "User", // Role padrão
            String.valueOf(usuario.getId())
        );

        // Criar resposta com token e informações do usuário
        return new AuthResponseDTO(
            token,
            "Bearer",
            3600, // 1 hora em segundos
            usuario.getId(),
            usuario.getNomeCompleto() != null ? usuario.getNomeCompleto() : "",
            usuario.getEmail(),
            "User",
            campusNome
        );
    }
}