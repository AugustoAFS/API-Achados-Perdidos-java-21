package com.AchadosPerdidos.API.Application.Services;

import com.AchadosPerdidos.API.Application.DTOs.Auth.AuthResponseDTO;
import com.AchadosPerdidos.API.Application.DTOs.Auth.LoginRequestDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosListDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosUpdateDTO;
import com.AchadosPerdidos.API.Application.Mapper.UsuariosModelMapper;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IJWTService;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IUsuariosService;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IDeviceTokenService;
import com.AchadosPerdidos.API.Exeptions.BusinessException;
import com.AchadosPerdidos.API.Exeptions.ResourceNotFoundException;
import com.AchadosPerdidos.API.Domain.Entity.Usuario;
import com.AchadosPerdidos.API.Domain.Entity.UsuarioRole;
import com.AchadosPerdidos.API.Domain.Entity.Role;
import com.AchadosPerdidos.API.Domain.Repository.UsuariosRepository;
import com.AchadosPerdidos.API.Domain.Repository.UsuarioRoleRepository;
import com.AchadosPerdidos.API.Domain.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuariosService implements IUsuariosService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private UsuariosModelMapper usuariosModelMapper;

    @Autowired
    private IJWTService jwtTokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRoleRepository usuarioRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private IDeviceTokenService deviceTokenService;

    @Value("${jwt.expiry-in-minutes:60}")
    private int jwtExpiryInMinutes;

    @Override
    @Cacheable(value = "usuarios", key = "'all'")
    public UsuariosListDTO getAllUsuarios() {
        List<Usuario> usuarios = usuariosRepository.findAll();
        return usuariosModelMapper.toListDTO(usuarios);
    }

    @Override
    @Cacheable(value = "usuarios", key = "#id")
    public UsuariosListDTO getUsuarioById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID do usuário deve ser válido");
        }
        
        Usuario usuario = usuariosRepository.findById(id);
        if (usuario == null) {
            throw new ResourceNotFoundException("Usuário não encontrado com ID: " + id);
        }
        return usuariosModelMapper.toListDTO(usuario);
    }

    @Override
    @Cacheable(value = "usuarios", key = "'email_' + #email")
    public UsuariosDTO getUsuarioByEmail(String email) {
        if (!StringUtils.hasText(email)) {
            throw new IllegalArgumentException("Email não pode ser vazio");
        }
        
        Usuario usuario = usuariosRepository.findByEmail(email);
        if (usuario == null) {
            throw new ResourceNotFoundException("Usuário não encontrado com email: " + email);
        }
        return usuariosModelMapper.toDTO(usuario);
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
            Usuario usuarioExistente = usuariosRepository.findByEmail(usuariosCreateDTO.getEmail());
            if (usuarioExistente != null) {
                throw new BusinessException("Já existe um usuário cadastrado com o email: " + usuariosCreateDTO.getEmail());
            }
        }
        
        // Validação de CPF duplicado
        if (StringUtils.hasText(usuariosCreateDTO.getCpf())) {
            List<Usuario> usuariosComMesmoCpf = usuariosRepository.findAll().stream()
                .filter(u -> usuariosCreateDTO.getCpf().equals(u.getCpf()))
                .toList();
            
            if (!usuariosComMesmoCpf.isEmpty()) {
                throw new BusinessException("Já existe um usuário cadastrado com o CPF informado");
            }
        }
        
        // Converter DTO para entidade (sem hash ainda)
        Usuario usuario = usuariosModelMapper.fromCreateDTO(usuariosCreateDTO);

        // Gerar hash BCrypt da senha no serviço
        String hashSenha = passwordEncoder.encode(usuariosCreateDTO.getSenha());
        usuario.setHash_senha(hashSenha);

        usuario.setDta_Criacao(LocalDateTime.now());
        usuario.setFlg_Inativo(false);
        
        Usuario savedUsuario = usuariosRepository.save(usuario);
        
        // Associar usuário ao campus se fornecido
        if (usuariosCreateDTO.getCampusId() != null && usuariosCreateDTO.getCampusId() > 0) {
            usuariosRepository.associarUsuarioCampus(savedUsuario.getId(), usuariosCreateDTO.getCampusId());
        }
        
        return usuariosModelMapper.toCreateDTO(savedUsuario);
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
        
        Usuario existingUsuario = usuariosRepository.findById(id);
        if (existingUsuario == null) {
            throw new ResourceNotFoundException("Usuário não encontrado com ID: " + id);
        }
        
        if (Boolean.TRUE.equals(existingUsuario.getFlg_Inativo())) {
            throw new BusinessException("Não é possível atualizar um usuário inativo");
        }
        
        if (StringUtils.hasText(usuariosUpdateDTO.getEmail()) && 
            !usuariosUpdateDTO.getEmail().equals(existingUsuario.getEmail())) {
            Usuario usuarioComMesmoEmail = usuariosRepository.findByEmail(usuariosUpdateDTO.getEmail());
            if (usuarioComMesmoEmail != null && !usuarioComMesmoEmail.getId().equals(id)) {
                throw new BusinessException("Já existe outro usuário com o email: " + usuariosUpdateDTO.getEmail());
            }
        }
        
        if (StringUtils.hasText(usuariosUpdateDTO.getCpf()) && 
            !usuariosUpdateDTO.getCpf().equals(existingUsuario.getCpf())) {
            List<Usuario> usuariosComMesmoCpf = usuariosRepository.findAll().stream()
                .filter(u -> usuariosUpdateDTO.getCpf().equals(u.getCpf()) && !u.getId().equals(id))
                .toList();
            
            if (!usuariosComMesmoCpf.isEmpty()) {
                throw new BusinessException("Já existe outro usuário com o CPF informado");
            }
        }
        
        usuariosModelMapper.updateFromDTO(existingUsuario, usuariosUpdateDTO);
        
        Usuario updatedUsuario = usuariosRepository.save(existingUsuario);
        return usuariosModelMapper.toUpdateDTO(updatedUsuario);
    }

    @Override
    @CacheEvict(value = "usuarios", allEntries = true)
    public boolean deleteUsuario(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID do usuário deve ser válido");
        }
        
        Usuario usuario = usuariosRepository.findById(id);
        if (usuario == null) {
            throw new ResourceNotFoundException("Usuário não encontrado com ID: " + id);
        }
        
        if (Boolean.TRUE.equals(usuario.getFlg_Inativo())) {
            throw new BusinessException("O usuário já está inativo");
        }
        
        usuario.setFlg_Inativo(true);
        usuario.setDta_Remocao(LocalDateTime.now());
        usuariosRepository.save(usuario);
        
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
        Usuario usuario = null;
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
        usuario.setHash_senha(hashSenha);
        
        Usuario updatedUsuario = usuariosRepository.save(usuario);
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
        Usuario usuario = usuariosRepository.findByEmail(loginRequestDTO.getEmail());
        if (usuario == null) {
            throw new BusinessException("Email ou senha inválidos");
        }

        // Verificar se o usuário está ativo
        if (usuario.getFlg_Inativo() != null && usuario.getFlg_Inativo()) {
            throw new BusinessException("Usuário inativo");
        }

        // Verificar senha usando BCrypt
        if (usuario.getHash_senha() == null || usuario.getHash_senha().trim().isEmpty()) {
            throw new BusinessException("Usuário não possui senha cadastrada. Use o endpoint de redefinir senha.");
        }

        boolean senhaValida = passwordEncoder.matches(loginRequestDTO.getSenha(), usuario.getHash_senha());
        if (!senhaValida) {
            throw new BusinessException("Email ou senha inválidos");
        }

        // Buscar campus ativo do usuário
        String campusNome = usuariosRepository.getCampusNomeAtivoByUsuarioId(usuario.getId());

        // Buscar roles ativas do usuário
        List<UsuarioRole> usuarioRoles = usuarioRoleRepository.findByUsuarioId(usuario.getId());
        String rolesString = usuarioRoles.stream()
            .filter(ur -> ur.getFlg_Inativo() == null || !ur.getFlg_Inativo()) // Apenas roles ativas
            .map(UsuarioRole::getRole_id)
            .map(roleId -> {
                Role role = roleRepository.findById(roleId);
                return role != null && (role.getFlgInativo() == null || !role.getFlgInativo()) 
                    ? role.getNome() 
                    : null;
            })
            .filter(roleNome -> roleNome != null)
            .collect(Collectors.joining(","));
        
        // Se não houver roles, usar "User" como padrão
        if (rolesString == null || rolesString.trim().isEmpty()) {
            rolesString = "User";
        }

        // Gerar token JWT
        String token = jwtTokenService.generateToken(
            usuario.getEmail(),
            usuario.getNomeCompleto() != null ? usuario.getNomeCompleto() : "",
            rolesString,
            String.valueOf(usuario.getId())
        );

        long expiresIn = jwtExpiryInMinutes * 60L;

        // Registrar device token se fornecido (opcional)
        if (loginRequestDTO.getDeviceToken() != null && 
            !loginRequestDTO.getDeviceToken().trim().isEmpty() &&
            loginRequestDTO.getPlataforma() != null && 
            !loginRequestDTO.getPlataforma().trim().isEmpty()) {
            try {
                deviceTokenService.registerOrUpdateDeviceToken(
                    usuario.getId(),
                    loginRequestDTO.getDeviceToken().trim(),
                    loginRequestDTO.getPlataforma().trim()
                );
            } catch (Exception e) {
                // Não interrompe o login se falhar ao registrar device token
                // Apenas loga o erro
                System.err.println("Erro ao registrar device token durante login: " + e.getMessage());
            }
        }

        // Criar resposta com token e informações do usuário
        return new AuthResponseDTO(
            token,
            "Bearer",
            expiresIn,
            usuario.getId(),
            usuario.getNomeCompleto() != null ? usuario.getNomeCompleto() : "",
            usuario.getEmail(),
            rolesString,
            campusNome
        );
    }
}
