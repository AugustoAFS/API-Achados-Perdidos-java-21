package com.AchadosPerdidos.API.Application.Services;

import com.AchadosPerdidos.API.Application.DTOs.Auth.LoginRequestDTO;
import com.AchadosPerdidos.API.Application.DTOs.Auth.TokenResponseDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosListDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosUpdateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.AlunoCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.ServidorCreateDTO;
import com.AchadosPerdidos.API.Application.Mapper.UsuariosMapper;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IJWTService;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IUsuariosService;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IDeviceTokenService;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IUsuarioCampusService;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IFotoUsuarioService;
import com.AchadosPerdidos.API.Application.DTOs.UsuarioCampus.UsuarioCampusCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.FotoUsuario.FotoUsuarioCreateDTO;
import com.AchadosPerdidos.API.Exeptions.BusinessException;
import com.AchadosPerdidos.API.Exeptions.ResourceNotFoundException;
import com.AchadosPerdidos.API.Exeptions.ValidationException;
import com.AchadosPerdidos.API.Domain.Entity.Usuario;
import com.AchadosPerdidos.API.Domain.Entity.Role;
import com.AchadosPerdidos.API.Domain.Repository.UsuariosRepository;
import com.AchadosPerdidos.API.Domain.Repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Transactional
public class UsuariosService implements IUsuariosService {

    private static final Logger logger = LoggerFactory.getLogger(UsuariosService.class);

    // Regex para validações básicas
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private UsuariosMapper usuariosMapper;

    @Autowired
    private IJWTService jwtTokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IDeviceTokenService deviceTokenService;

    @Autowired
    private IUsuarioCampusService usuarioCampusService;

    @Autowired
    private IFotoUsuarioService fotoUsuarioService;

    @Autowired
    private RoleRepository roleRepository;

    @Value("${jwt.expiry-in-minutes:60}")
    private int jwtExpiryInMinutes;

    @Override
    @Cacheable(value = "usuarios", key = "'all'")
    @Transactional(readOnly = true)
    public UsuariosListDTO getAllUsuarios() {
        logger.debug("Buscando todos os usuários");
        List<Usuario> usuarios = usuariosRepository.findAll();
        logger.info("Total de usuários encontrados: {}", usuarios.size());
        return usuariosMapper.toListDTO(usuarios);
    }

    @Override
    @Cacheable(value = "usuarios", key = "#id")
    @Transactional(readOnly = true)
    public UsuariosListDTO getUsuarioById(int id) {
        logger.debug("Buscando usuário com ID: {}", id);

        if (id <= 0) {
            throw new ValidationException("ID do usuário deve ser válido");
        }

        Usuario usuario = usuariosRepository.findById(id);
        if (usuario == null) {
            logger.warn("Usuário não encontrado com ID: {}", id);
            throw new ResourceNotFoundException("Usuário não encontrado com ID: " + id);
        }

        logger.info("Usuário encontrado: {}", usuario.getEmail());
        return usuariosMapper.toListDTO(usuario);
    }

    @Override
    @Cacheable(value = "usuarios", key = "'email_' + #email")
    @Transactional(readOnly = true)
    public UsuariosDTO getUsuarioByEmail(String email) {
        logger.debug("Buscando usuário por email");

        if (!StringUtils.hasText(email)) {
            throw new ValidationException("O email é obrigatório");
        }

        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            throw new ValidationException("Formato de email inválido");
        }

        Usuario usuario = usuariosRepository.findByEmail(email);
        if (usuario == null) {
            logger.warn("Usuário não encontrado com o email informado");
            throw new ResourceNotFoundException("Usuário não encontrado com email: " + email);
        }

        logger.info("Usuário encontrado: {}", usuario.getId());
        return usuariosMapper.toDTO(usuario);
    }

    @Override
    @CacheEvict(value = "usuarios", allEntries = true)
    public UsuariosCreateDTO createUsuario(UsuariosCreateDTO usuariosCreateDTO) {
        logger.info("Iniciando criação de novo usuário");

        if (usuariosCreateDTO == null) {
            throw new ValidationException("Dados do usuário não podem ser nulos");
        }
        
        // Validações de entrada
        validateUsuarioCreation(usuariosCreateDTO);

        // Verificação de duplicatas
        checkDuplicateEmail(usuariosCreateDTO.getEmail(), null);
        checkDuplicateCPF(usuariosCreateDTO.getCpf(), null);

        // Mapear DTO para Entidade
        Usuario usuario = usuariosMapper.fromCreateDTO(usuariosCreateDTO);

        // Definir role padrão (USER) se não fornecida
        if (usuario.getRole_id() == null) {
            Role defaultRole = getDefaultRole();
            usuario.setRole_id(defaultRole);
            logger.debug("Role padrão 'USER' atribuída ao novo usuário");
        }

        // Criptografar senha
        String hashSenha = passwordEncoder.encode(usuariosCreateDTO.getSenha());
        usuario.setHash_senha(hashSenha);

        // Definir dados de auditoria
        usuario.setDta_Criacao(LocalDateTime.now());
        usuario.setFlg_Inativo(false);
        
        // Salvar usuário
        Usuario savedUsuario = usuariosRepository.save(usuario);
        logger.info("Usuário criado com sucesso - ID: {}, Email: {}", savedUsuario.getId(), savedUsuario.getEmail());

        // Associar ao campus se fornecido
        if (usuariosCreateDTO.getCampusId() != null && usuariosCreateDTO.getCampusId() > 0) {
            try {
                UsuarioCampusCreateDTO usuarioCampusCreateDTO = new UsuarioCampusCreateDTO();
                usuarioCampusCreateDTO.setUsuarioId(savedUsuario.getId());
                usuarioCampusCreateDTO.setCampusId(usuariosCreateDTO.getCampusId());
                
                usuarioCampusService.createUsuarioCampus(usuarioCampusCreateDTO);
                logger.info("Usuário associado ao campus ID: {}", usuariosCreateDTO.getCampusId());
            } catch (Exception e) {
                logger.error("Erro ao associar usuário ao campus: {}", e.getMessage(), e);
                // Não falha a criação do usuário se a associação falhar, mas loga o erro
                // Pode ser ajustado para lançar exceção se necessário
            }
        }
        
        return usuariosMapper.toCreateDTO(savedUsuario);
    }

    /**
     * Valida os dados de criação do usuário
     */
    private void validateUsuarioCreation(UsuariosCreateDTO dto) {
        logger.debug("Validando dados de criação do usuário");

        // Validar nome completo
        if (!StringUtils.hasText(dto.getNomeCompleto()) || dto.getNomeCompleto().trim().length() < 3) {
            throw new ValidationException("O nome completo é obrigatório e deve ter pelo menos 3 caracteres");
        }

        // Validar email
        if (!StringUtils.hasText(dto.getEmail())) {
            throw new ValidationException("O email é obrigatório");
        }
        if (!EMAIL_PATTERN.matcher(dto.getEmail().trim()).matches()) {
            throw new ValidationException("Formato de email inválido");
        }

        // Validar senha
        if (!StringUtils.hasText(dto.getSenha()) || dto.getSenha().length() < 6) {
            throw new ValidationException("A senha é obrigatória e deve ter pelo menos 6 caracteres");
        }

        // Validar CPF (se fornecido)
        if (StringUtils.hasText(dto.getCpf())) {
            String cleanCpf = dto.getCpf().replaceAll("[^0-9]", "");
            if (cleanCpf.length() != 11) {
                throw new ValidationException("CPF inválido");
            }
        }
    }

    /**
     * Verifica se já existe usuário com o email informado
     */
    private void checkDuplicateEmail(String email, Integer excludeId) {
        if (!StringUtils.hasText(email)) {
            return;
        }

        Usuario usuarioExistente = usuariosRepository.findByEmail(email.trim());
        if (usuarioExistente != null && (excludeId == null || !excludeId.equals(usuarioExistente.getId()))) {
            logger.warn("Tentativa de criar/atualizar usuário com email duplicado: {}", email);
            throw new BusinessException("Já existe um usuário cadastrado com o email: " + email);
        }
    }

    /**
     * Verifica se já existe usuário com o CPF informado
     */
    private void checkDuplicateCPF(String cpf, Integer excludeId) {
        if (!StringUtils.hasText(cpf)) {
            return;
        }

        String cleanCpf = cpf.replaceAll("[^0-9]", "");
        Usuario usuarioExistente = usuariosRepository.findByCpf(cleanCpf);

        if (usuarioExistente != null && (excludeId == null || !excludeId.equals(usuarioExistente.getId()))) {
            logger.warn("Tentativa de criar/atualizar usuário com CPF duplicado");
            throw new BusinessException("Já existe um usuário cadastrado com o CPF informado");
        }
    }

    @Override
    @CacheEvict(value = "usuarios", allEntries = true)
    public UsuariosUpdateDTO updateUsuario(int id, UsuariosUpdateDTO usuariosUpdateDTO) {
        logger.info("Iniciando atualização do usuário ID: {}", id);

        if (id <= 0) {
            throw new ValidationException("ID do usuário deve ser válido");
        }

        if (usuariosUpdateDTO == null) {
            throw new ValidationException("Dados de atualização não podem ser nulos");
        }
        
        Usuario existingUsuario = usuariosRepository.findById(id);
        if (existingUsuario == null) {
            logger.warn("Tentativa de atualizar usuário não encontrado - ID: {}", id);
            throw new ResourceNotFoundException("Usuário não encontrado com ID: " + id);
        }
        
        if (Boolean.TRUE.equals(existingUsuario.getFlg_Inativo())) {
            logger.warn("Tentativa de atualizar usuário inativo - ID: {}", id);
            throw new BusinessException("Não é possível atualizar um usuário inativo");
        }
        
        // Validações parciais (apenas dos campos que foram fornecidos)
        if (StringUtils.hasText(usuariosUpdateDTO.getNomeCompleto()) && usuariosUpdateDTO.getNomeCompleto().trim().length() < 3) {
            throw new ValidationException("O nome completo deve ter pelo menos 3 caracteres");
        }

        if (StringUtils.hasText(usuariosUpdateDTO.getEmail()) && !EMAIL_PATTERN.matcher(usuariosUpdateDTO.getEmail().trim()).matches()) {
            throw new ValidationException("Formato de email inválido");
        }
        
        // Verificação de duplicatas (excluindo o próprio usuário)
        checkDuplicateEmail(usuariosUpdateDTO.getEmail(), id);
        checkDuplicateCPF(usuariosUpdateDTO.getCpf(), id);

        // Atualizar campos
        usuariosMapper.updateFromDTO(existingUsuario, usuariosUpdateDTO);
        
        Usuario updatedUsuario = usuariosRepository.save(existingUsuario);
        logger.info("Usuário atualizado com sucesso - ID: {}, Email: {}", updatedUsuario.getId(), updatedUsuario.getEmail());

        // Atualizar campus se fornecido
        if (usuariosUpdateDTO.getCampusId() != null && usuariosUpdateDTO.getCampusId() > 0) {
            try {
                // Buscar todas as associações e filtrar por usuarioId
                var allUsuarioCampus = usuarioCampusService.getAllUsuarioCampus();
                java.util.List<com.AchadosPerdidos.API.Application.DTOs.UsuarioCampus.UsuarioCampusDTO> usuarioCampusList = 
                    allUsuarioCampus != null && allUsuarioCampus.getUsuarioCampus() != null
                    ? allUsuarioCampus.getUsuarioCampus().stream()
                        .filter(uc -> uc.getUsuarioId() != null && uc.getUsuarioId().equals(updatedUsuario.getId()))
                        .collect(java.util.stream.Collectors.toList())
                    : java.util.Collections.emptyList();
                
                if (!usuarioCampusList.isEmpty()) {
                    // Desativar associações ativas existentes
                    for (com.AchadosPerdidos.API.Application.DTOs.UsuarioCampus.UsuarioCampusDTO uc : usuarioCampusList) {
                        if (uc.getCampusId() != null && !uc.getCampusId().equals(usuariosUpdateDTO.getCampusId()) && 
                            (uc.getFlgInativo() == null || !uc.getFlgInativo())) {
                            // Desativar a associação antiga
                            var updateDTO = new com.AchadosPerdidos.API.Application.DTOs.UsuarioCampus.UsuarioCampusUpdateDTO();
                            updateDTO.setFlgInativo(true);
                            usuarioCampusService.updateUsuarioCampus(updatedUsuario.getId(), uc.getCampusId(), updateDTO);
                            logger.info("Associação com campus ID {} desativada para usuário ID {}", uc.getCampusId(), updatedUsuario.getId());
                        }
                    }
                }
                
                // Verificar se já existe associação ativa com o novo campus
                boolean jaExiste = usuarioCampusList.stream()
                    .anyMatch(uc -> uc.getCampusId() != null && uc.getCampusId().equals(usuariosUpdateDTO.getCampusId()) && 
                        (uc.getFlgInativo() == null || !uc.getFlgInativo()));
                
                // Criar nova associação se não existir
                if (!jaExiste) {
                    UsuarioCampusCreateDTO usuarioCampusCreateDTO = new UsuarioCampusCreateDTO();
                    usuarioCampusCreateDTO.setUsuarioId(updatedUsuario.getId());
                    usuarioCampusCreateDTO.setCampusId(usuariosUpdateDTO.getCampusId());
                    usuarioCampusService.createUsuarioCampus(usuarioCampusCreateDTO);
                    logger.info("Usuário associado ao campus ID: {}", usuariosUpdateDTO.getCampusId());
                }
            } catch (Exception e) {
                logger.error("Erro ao atualizar campus do usuário: {}", e.getMessage(), e);
                // Não falha a atualização do usuário se a associação falhar
            }
        }

        // Atualizar foto se fornecido
        // Nota: Métodos createFotoUsuario e updateFotoUsuario não existem mais na interface IFotoUsuarioService
        // Esta funcionalidade foi removida da interface. Se necessário, implementar diretamente no repository.
        if (usuariosUpdateDTO.getFotoId() != null && usuariosUpdateDTO.getFotoId() > 0) {
            logger.warn("Atualização de foto do usuário não está disponível - métodos removidos da interface IFotoUsuarioService");
            // TODO: Implementar atualização de foto diretamente no repository se necessário
        }

        return usuariosMapper.toUpdateDTO(updatedUsuario);
    }

    @Override
    @CacheEvict(value = "usuarios", allEntries = true)
    public boolean deleteUsuario(int id) {
        logger.info("Iniciando exclusão (soft delete) do usuário ID: {}", id);

        if (id <= 0) {
            throw new ValidationException("ID do usuário deve ser válido");
        }

        Usuario usuario = usuariosRepository.findById(id);
        if (usuario == null) {
            logger.warn("Tentativa de deletar usuário não encontrado - ID: {}", id);
            throw new ResourceNotFoundException("Usuário não encontrado com ID: " + id);
        }
        
        if (Boolean.TRUE.equals(usuario.getFlg_Inativo())) {
            logger.warn("Tentativa de deletar usuário já inativo - ID: {}", id);
            throw new BusinessException("O usuário já está inativo");
        }
        
        // Soft delete
        usuario.setFlg_Inativo(true);
        usuario.setDta_Remocao(LocalDateTime.now());
        usuariosRepository.save(usuario);
        
        logger.info("Usuário inativado com sucesso - ID: {}, Email: {}", id, usuario.getEmail());
        return true;
    }

    @Override
    @CacheEvict(value = "usuarios", allEntries = true)
    public boolean redefinirSenha(String cpf, String matricula, String novaSenha) {
        logger.info("Solicitação de redefinição de senha");

        // Validação da nova senha
        if (!StringUtils.hasText(novaSenha) || novaSenha.length() < 6) {
            throw new ValidationException("A nova senha é obrigatória e deve ter pelo menos 6 caracteres");
        }

        // Validação: deve fornecer CPF ou matrícula (pelo menos um)
        if (!StringUtils.hasText(cpf) && !StringUtils.hasText(matricula)) {
            throw new ValidationException("É necessário fornecer CPF ou matrícula para redefinir a senha");
        }

        // Buscar usuário por CPF ou matrícula
        Usuario usuario = findUsuarioForPasswordReset(cpf, matricula);

        // Verificar se usuário está ativo
        if (Boolean.TRUE.equals(usuario.getFlg_Inativo())) {
            logger.warn("Tentativa de redefinir senha de usuário inativo");
            throw new BusinessException("Não é possível redefinir a senha de um usuário inativo");
        }

        // Gera um novo hash BCrypt para a senha
        String hashSenha = passwordEncoder.encode(novaSenha);
        usuario.setHash_senha(hashSenha);

        Usuario updatedUsuario = usuariosRepository.save(usuario);
        logger.info("Senha redefinida com sucesso para usuário ID: {}", usuario.getId());

        return updatedUsuario != null;
    }

    /**
     * Busca usuário para redefinição de senha por CPF ou matrícula
     */
    private Usuario findUsuarioForPasswordReset(String cpf, String matricula) {
        Usuario usuario = null;

        if (StringUtils.hasText(cpf)) {
            String cleanCpf = cpf.replaceAll("[^0-9]", "");
            usuario = usuariosRepository.findByCpf(cleanCpf);
            if (usuario == null) {
                logger.warn("Tentativa de redefinir senha com CPF não cadastrado");
                throw new BusinessException("Usuário não encontrado com o CPF fornecido");
            }
        } else if (StringUtils.hasText(matricula)) {
            usuario = usuariosRepository.findByMatricula(matricula.trim());
            if (usuario == null) {
                logger.warn("Tentativa de redefinir senha com matrícula não cadastrada");
                throw new BusinessException("Usuário não encontrado com a matrícula fornecida");
            }
        }

        return usuario;
    }

    @Override
    @Transactional(readOnly = true)
    public TokenResponseDTO login(LoginRequestDTO loginRequestDTO) {
        logger.info("Tentativa de login");

        // Validação dos campos de entrada
        validateLoginRequest(loginRequestDTO);

        String email = loginRequestDTO.getEmail_Usuario().trim();
        String senha = loginRequestDTO.getSenha_Hash();

        // Buscar usuário pelo email
        Usuario usuario = usuariosRepository.findByEmail(email);
        if (usuario == null) {
            logger.warn("Tentativa de login com email não cadastrado");
            throw new BusinessException("Email ou senha inválidos");
        }

        // Verificar se o usuário está ativo
        if (Boolean.TRUE.equals(usuario.getFlg_Inativo())) {
            logger.warn("Tentativa de login com usuário inativo: {}", email);
            throw new BusinessException("Usuário inativo. Entre em contato com o administrador.");
        }

        // Verificar senha
        if (!StringUtils.hasText(usuario.getHash_senha())) {
            logger.warn("Usuário sem senha cadastrada: {}", email);
            throw new BusinessException("Usuário não possui senha cadastrada. Use o endpoint de redefinir senha.");
        }

        boolean senhaValida = passwordEncoder.matches(senha, usuario.getHash_senha());
        if (!senhaValida) {
            logger.warn("Tentativa de login com senha inválida");
            throw new BusinessException("Email ou senha inválidos");
        }

        logger.info("Login bem-sucedido - Usuário ID: {}", usuario.getId());

        // Buscar informações complementares
        String campusNome = getCampusNome(usuario.getId());
        String rolesString = getRolesString(usuario);

        // Gerar token JWT
        String token = jwtTokenService.createToken(
            usuario.getEmail(),
            usuario.getNomeCompleto() != null ? usuario.getNomeCompleto() : "",
            rolesString,
            String.valueOf(usuario.getId())
        );

        // Registrar device token se fornecido
        registerDeviceToken(loginRequestDTO, usuario.getId());

        // Retornar apenas o token por questões de segurança
        return new TokenResponseDTO(token);
    }

    /**
     * Valida os dados de login
     */
    private void validateLoginRequest(LoginRequestDTO loginRequestDTO) {
        if (loginRequestDTO == null) {
            logger.error("LoginRequestDTO é nulo");
            throw new ValidationException("Dados de login não podem ser nulos");
        }

        logger.debug("Validando login - Email_Usuario: '{}', Senha_Hash presente: {}", 
            loginRequestDTO.getEmail_Usuario(), 
            loginRequestDTO.getSenha_Hash() != null);

        if (!StringUtils.hasText(loginRequestDTO.getEmail_Usuario())) {
            logger.error("Email_Usuario está vazio ou nulo. Valor recebido: '{}'", loginRequestDTO.getEmail_Usuario());
            throw new ValidationException("Email é obrigatório");
        }

        if (!StringUtils.hasText(loginRequestDTO.getSenha_Hash())) {
            logger.error("Senha_Hash está vazia ou nula");
            throw new ValidationException("Senha é obrigatória");
        }

        // Validar formato do email
        if (!EMAIL_PATTERN.matcher(loginRequestDTO.getEmail_Usuario().trim()).matches()) {
            logger.error("Formato de email inválido: '{}'", loginRequestDTO.getEmail_Usuario());
            throw new ValidationException("Formato de email inválido");
        }
    }

    /**
     * Obtém o nome do campus ativo do usuário
     */
    private String getCampusNome(int usuarioId) {
        try {
            String campusNome = usuariosRepository.getCampusNomeAtivoByUsuarioId(usuarioId);
            return campusNome != null ? campusNome : "";
        } catch (Exception e) {
            logger.warn("Erro ao buscar campus do usuário ID {}: {}", usuarioId, e.getMessage());
            return "";
        }
    }

    /**
     * Obtém as roles do usuário em formato string
     * Como a entidade Usuario tem apenas um Role_id, retorna o nome dessa role
     */
    private String getRolesString(Usuario usuario) {
        if (usuario.getRole_id() == null) {
            logger.debug("Usuário {} sem role definida, usando 'User' como padrão", usuario.getId());
            return "User";
        }

        try {
            Role role = usuario.getRole_id();

            // Verificar se a role está ativa
            if (role.getFlgInativo() != null && role.getFlgInativo()) {
                logger.warn("Usuário {} tem role inativa, usando 'User' como padrão", usuario.getId());
                return "User";
            }

            String roleName = role.getNome();
            return StringUtils.hasText(roleName) ? roleName : "User";

        } catch (Exception e) {
            logger.error("Erro ao buscar role do usuário {}: {}", usuario.getId(), e.getMessage());
            return "User";
        }
    }

    /**
     * Registra o device token para notificações push
     */
    private void registerDeviceToken(LoginRequestDTO loginRequestDTO, int usuarioId) {
        if (StringUtils.hasText(loginRequestDTO.getDevice_Token()) &&
            StringUtils.hasText(loginRequestDTO.getPlataforma())) {

            try {
                // Nota: registerOrUpdateDeviceToken não existe mais na interface
                // Usar createDeviceToken diretamente
                var createDTO = new com.AchadosPerdidos.API.Application.DTOs.DeviceToken.DeviceTokenCreateDTO();
                createDTO.setUsuarioId(usuarioId);
                createDTO.setToken(loginRequestDTO.getDevice_Token().trim());
                createDTO.setPlataforma(loginRequestDTO.getPlataforma().trim());
                // Nota: DeviceTokenCreateDTO não tem setFlgInativo, o padrão é false
                deviceTokenService.createDeviceToken(createDTO);
                logger.debug("Device token registrado para usuário ID: {}", usuarioId);
            } catch (Exception e) {
                // Não interrompe o login se falhar ao registrar device token
                logger.error("Erro ao registrar device token durante login: {}", e.getMessage());
            }
        }
    }


    /**
     * Cria um novo usuário do tipo ALUNO
     * Valida que a matrícula é obrigatória
     */
    @Override
    @CacheEvict(value = "usuarios", allEntries = true)
    public UsuariosCreateDTO createAluno(AlunoCreateDTO alunoDTO) {
        logger.info("Iniciando criação de novo aluno");

        if (alunoDTO == null) {
            throw new ValidationException("Dados do aluno não podem ser nulos");
        }

        // Validar matrícula obrigatória para aluno
        if (!StringUtils.hasText(alunoDTO.getMatricula())) {
            throw new ValidationException("A matrícula é obrigatória para alunos");
        }

        // Validações básicas
        if (!StringUtils.hasText(alunoDTO.getNomeCompleto()) || alunoDTO.getNomeCompleto().trim().length() < 3) {
            throw new ValidationException("O nome completo é obrigatório e deve ter pelo menos 3 caracteres");
        }

        if (!StringUtils.hasText(alunoDTO.getEmail())) {
            throw new ValidationException("O email é obrigatório");
        }
        if (!EMAIL_PATTERN.matcher(alunoDTO.getEmail().trim()).matches()) {
            throw new ValidationException("Formato de email inválido");
        }

        if (!StringUtils.hasText(alunoDTO.getSenha()) || alunoDTO.getSenha().length() < 6) {
            throw new ValidationException("A senha é obrigatória e deve ter pelo menos 6 caracteres");
        }

        // Verificação de duplicatas
        checkDuplicateEmail(alunoDTO.getEmail(), null);
        checkDuplicateMatricula(alunoDTO.getMatricula(), null);

        // Mapear DTO específico para Entidade
        Usuario usuario = usuariosMapper.fromAlunoCreateDTO(alunoDTO);

        // Definir role ALUNO
        Role alunoRole = getRoleByNome("ALUNO");
        usuario.setRole_id(alunoRole);
        logger.debug("Role 'ALUNO' atribuída ao novo aluno");

        // Criptografar senha
        String hashSenha = passwordEncoder.encode(alunoDTO.getSenha());
        usuario.setHash_senha(hashSenha);

        // Definir dados de auditoria
        usuario.setDta_Criacao(LocalDateTime.now());
        usuario.setFlg_Inativo(false);

        // Salvar usuário
        Usuario savedUsuario = usuariosRepository.save(usuario);
        logger.info("Aluno criado com sucesso - ID: {}, Email: {}, Matrícula: {}", 
            savedUsuario.getId(), savedUsuario.getEmail(), savedUsuario.getMatricula());

        // Associar ao campus se fornecido
        if (alunoDTO.getCampusId() != null && alunoDTO.getCampusId() > 0) {
            try {
                UsuarioCampusCreateDTO usuarioCampusCreateDTO = new UsuarioCampusCreateDTO();
                usuarioCampusCreateDTO.setUsuarioId(savedUsuario.getId());
                usuarioCampusCreateDTO.setCampusId(alunoDTO.getCampusId());

                usuarioCampusService.createUsuarioCampus(usuarioCampusCreateDTO);
                logger.info("Aluno associado ao campus ID: {}", alunoDTO.getCampusId());
            } catch (Exception e) {
                logger.error("Erro ao associar aluno ao campus: {}", e.getMessage(), e);
            }
        }

        return usuariosMapper.toCreateDTO(savedUsuario);
    }

    /**
     * Cria um novo usuário do tipo SERVIDOR
     * Valida que o CPF é obrigatório e tem 11 dígitos
     */
    @Override
    @CacheEvict(value = "usuarios", allEntries = true)
    public UsuariosCreateDTO createServidor(ServidorCreateDTO servidorDTO) {
        logger.info("Iniciando criação de novo servidor");

        if (servidorDTO == null) {
            throw new ValidationException("Dados do servidor não podem ser nulos");
        }

        // Validar CPF obrigatório para servidor
        if (!StringUtils.hasText(servidorDTO.getCpf())) {
            throw new ValidationException("O CPF é obrigatório para servidores");
        }

        // Validar formato do CPF (11 dígitos)
        String cleanCpf = servidorDTO.getCpf().replaceAll("[^0-9]", "");
        if (cleanCpf.length() != 11) {
            throw new ValidationException("CPF inválido. Deve conter exatamente 11 dígitos");
        }

        // Validações básicas
        if (!StringUtils.hasText(servidorDTO.getNomeCompleto()) || servidorDTO.getNomeCompleto().trim().length() < 3) {
            throw new ValidationException("O nome completo é obrigatório e deve ter pelo menos 3 caracteres");
        }

        if (!StringUtils.hasText(servidorDTO.getEmail())) {
            throw new ValidationException("O email é obrigatório");
        }
        if (!EMAIL_PATTERN.matcher(servidorDTO.getEmail().trim()).matches()) {
            throw new ValidationException("Formato de email inválido");
        }

        if (!StringUtils.hasText(servidorDTO.getSenha()) || servidorDTO.getSenha().length() < 6) {
            throw new ValidationException("A senha é obrigatória e deve ter pelo menos 6 caracteres");
        }

        // Verificação de duplicatas
        checkDuplicateEmail(servidorDTO.getEmail(), null);
        checkDuplicateCPF(cleanCpf, null);

        // Mapear DTO específico para Entidade
        Usuario usuario = usuariosMapper.fromServidorCreateDTO(servidorDTO);
        usuario.setCpf(cleanCpf); // Garantir que o CPF está limpo

        // Definir role SERVIDOR
        Role servidorRole = getRoleByNome("SERVIDOR");
        usuario.setRole_id(servidorRole);
        logger.debug("Role 'SERVIDOR' atribuída ao novo servidor");

        // Criptografar senha
        String hashSenha = passwordEncoder.encode(servidorDTO.getSenha());
        usuario.setHash_senha(hashSenha);

        // Definir dados de auditoria
        usuario.setDta_Criacao(LocalDateTime.now());
        usuario.setFlg_Inativo(false);

        // Salvar usuário
        Usuario savedUsuario = usuariosRepository.save(usuario);
        logger.info("Servidor criado com sucesso - ID: {}, Email: {}, CPF: {}", 
            savedUsuario.getId(), savedUsuario.getEmail(), savedUsuario.getCpf());

        // Associar ao campus se fornecido
        if (servidorDTO.getCampusId() != null && servidorDTO.getCampusId() > 0) {
            try {
                UsuarioCampusCreateDTO usuarioCampusCreateDTO = new UsuarioCampusCreateDTO();
                usuarioCampusCreateDTO.setUsuarioId(savedUsuario.getId());
                usuarioCampusCreateDTO.setCampusId(servidorDTO.getCampusId());

                usuarioCampusService.createUsuarioCampus(usuarioCampusCreateDTO);
                logger.info("Servidor associado ao campus ID: {}", servidorDTO.getCampusId());
            } catch (Exception e) {
                logger.error("Erro ao associar servidor ao campus: {}", e.getMessage(), e);
            }
        }

        return usuariosMapper.toCreateDTO(savedUsuario);
    }

    /**
     * Verifica se já existe usuário com a matrícula informada
     */
    private void checkDuplicateMatricula(String matricula, Integer excludeId) {
        if (!StringUtils.hasText(matricula)) {
            return;
        }

        Usuario usuarioExistente = usuariosRepository.findByMatricula(matricula.trim());
        if (usuarioExistente != null && (excludeId == null || !excludeId.equals(usuarioExistente.getId()))) {
            logger.warn("Tentativa de criar/atualizar usuário com matrícula duplicada: {}", matricula);
            throw new BusinessException("Já existe um usuário cadastrado com a matrícula: " + matricula);
        }
    }

    /**
     * Busca uma role por nome
     */
    private Role getRoleByNome(String nomeRole) {
        try {
            Optional<Role> role = roleRepository.findByNome(nomeRole);
            if (role.isPresent()) {
                logger.debug("Role '{}' encontrada", nomeRole);
                return role.get();
            }
            logger.error("Role '{}' não encontrada no sistema", nomeRole);
            throw new BusinessException("Role '" + nomeRole + "' não encontrada. Contate o administrador.");
        } catch (Exception e) {
            logger.error("Erro ao buscar role '{}': {}", nomeRole, e.getMessage(), e);
            throw new BusinessException("Erro ao buscar role '" + nomeRole + "': " + e.getMessage());
        }
    }

    /**
     * Obtém a role padrão para novos usuários
     * Tenta buscar uma role com nome "USER", caso contrário usa a primeira role ativa disponível
     */
    private Role getDefaultRole() {
        try {
            // Tenta buscar role "USER" primeiro
            Optional<Role> userRole = roleRepository.findByNome("USER");
            if (userRole.isPresent()) {
                logger.debug("Role 'USER' encontrada e atribuída como padrão");
                return userRole.get();
            }
            logger.debug("Role 'USER' não encontrada, buscando outras roles ativas");

            // Se não encontrar "USER", busca a primeira role ativa
            List<Role> activeRoles = roleRepository.findActive();
            if (!activeRoles.isEmpty()) {
                logger.info("Usando primeira role ativa disponível como padrão: {}", activeRoles.get(0).getNome());
                return activeRoles.get(0);
            }

            // Como último recurso, tenta buscar qualquer role (mesmo inativa)
            List<Role> allRoles = roleRepository.findAll();
            if (!allRoles.isEmpty()) {
                logger.warn("Nenhuma role ativa encontrada. Usando primeira role disponível (pode estar inativa): {}", allRoles.get(0).getNome());
                return allRoles.get(0);
            }

            // Se não houver roles no sistema, lança exceção
            logger.error("Nenhuma role encontrada no sistema. Não é possível criar usuários.");
            throw new BusinessException("Sistema não possui roles configuradas. Contate o administrador.");
        } catch (Exception e) {
            logger.error("Erro ao buscar role padrão: {}", e.getMessage(), e);
            throw new BusinessException("Erro ao configurar role padrão para o usuário: " + e.getMessage());
        }
    }
}
