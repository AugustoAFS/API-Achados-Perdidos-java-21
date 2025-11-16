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
import com.AchadosPerdidos.API.Exeptions.ResourceNotFoundException;
import com.AchadosPerdidos.API.Domain.Entity.Usuarios;
import com.AchadosPerdidos.API.Domain.Repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
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
    private IJwtTokenService jwtTokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.expiry-in-minutes:60}")
    private int jwtExpiryInMinutes;

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
        
        // Validação da senha
        if (usuariosCreateDTO.getSenha() == null || usuariosCreateDTO.getSenha().trim().isEmpty()) {
            throw new BusinessException("A senha é obrigatória");
        }
        
        // Validação de email duplicado
        if (StringUtils.hasText(usuariosCreateDTO.getEmail())) {
            Usuarios usuarioExistente = usuariosRepository.findByEmail(usuariosCreateDTO.getEmail());
            if (usuarioExistente != null) {
                throw new BusinessException("Já existe um usuário cadastrado com o email: " + usuariosCreateDTO.getEmail());
            }
        }
        
        // Validação de CPF duplicado
        if (StringUtils.hasText(usuariosCreateDTO.getCpf())) {
            List<Usuarios> usuariosComMesmoCpf = usuariosRepository.findAll().stream()
                .filter(u -> usuariosCreateDTO.getCpf().equals(u.getCpf()))
                .toList();
            
            if (!usuariosComMesmoCpf.isEmpty()) {
                throw new BusinessException("Já existe um usuário cadastrado com o CPF informado");
            }
        }
        
        // Converter DTO para entidade (sem hash ainda)
        Usuarios usuarios = usuariosModelMapper.toEntity(usuariosCreateDTO);

        // Gerar hash BCrypt da senha no serviço
        String hashSenha = passwordEncoder.encode(usuariosCreateDTO.getSenha());
        usuarios.setHashSenha(hashSenha);

        usuarios.setDtaCriacao(new Date());
        usuarios.setFlgInativo(false);
        
        usuarios.validate();
        
        Usuarios savedUsuarios = usuariosRepository.save(usuarios);
        
        // Associar usuário ao campus se fornecido
        if (usuariosCreateDTO.getCampusId() != null && usuariosCreateDTO.getCampusId() > 0) {
            usuariosRepository.associarUsuarioCampus(savedUsuarios.getId(), usuariosCreateDTO.getCampusId());
        }
        
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

        long expiresIn = jwtExpiryInMinutes * 60L;

        // Criar resposta com token e informações do usuário
        return new AuthResponseDTO(
            token,
            "Bearer",
            expiresIn,
            usuario.getId(),
            usuario.getNomeCompleto() != null ? usuario.getNomeCompleto() : "",
            usuario.getEmail(),
            "User",
            campusNome
        );
    }
}
