package com.AchadosPerdidos.API.Application.Services;

import com.AchadosPerdidos.API.Application.DTOs.Item.ItemDTO;
import com.AchadosPerdidos.API.Application.DTOs.Item.ItemListDTO;
import com.AchadosPerdidos.API.Application.DTOs.Item.ItemCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Item.ItemUpdateDTO;
import com.AchadosPerdidos.API.Application.Mapper.ItensMapper;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IItensService;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IJWTService;
import com.AchadosPerdidos.API.Application.Services.Interfaces.INotificationService;
import com.AchadosPerdidos.API.Domain.Entity.Itens;
import com.AchadosPerdidos.API.Domain.Enum.Status_Item;
import com.AchadosPerdidos.API.Domain.Enum.Tipo_Item;
import com.AchadosPerdidos.API.Domain.Repository.ItensRepository;
import com.AchadosPerdidos.API.Domain.Repository.UsuariosRepository;
import com.AchadosPerdidos.API.Exeptions.BusinessException;
import com.AchadosPerdidos.API.Exeptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ItensService implements IItensService {

    private static final Logger logger = LoggerFactory.getLogger(ItensService.class);
    private static final int MAX_FOTOS_POR_ITEM = 3;

    @Autowired
    private ItensRepository itensRepository;

    @Autowired
    private ItensMapper itensMapper;
    
    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private FotosService fotosService;

    @Autowired
    private IJWTService jwtService;

    @Lazy
    @Autowired
    private INotificationService notificationService;

    @Override
    @Cacheable(value = "itens", key = "'all'")
    public ItemListDTO getAllItens() {
        // Retorna apenas itens ativos
        List<Itens> itens = itensRepository.findActive();
        return itensMapper.toListDTO(itens);
    }

    @Override
    @Cacheable(value = "itens", key = "#id")
    public ItemDTO getItemById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID do item deve ser válido");
        }
        
        Itens itens = itensRepository.findById(id);
        if (itens == null) {
            throw new ResourceNotFoundException("Item não encontrado com ID: " + id);
        }
        return itensMapper.toDTO(itens);
    }

    @Override
    @CacheEvict(value = "itens", allEntries = true)
    public ItemDTO createItem(ItemCreateDTO createDTO) {
        if (createDTO == null) {
            throw new IllegalArgumentException("Dados do item não podem ser nulos");
        }
        
        validarItemParaCriacao(createDTO);
        
        Itens itens = itensMapper.fromCreateDTO(createDTO);
        itens.setDtaCriacao(LocalDateTime.now());
        itens.setFlgInativo(false);
        itens.setStatus_item(Status_Item.ATIVO);

        Itens savedItens = itensRepository.save(itens);
        return itensMapper.toDTO(savedItens);
    }

    @Override
    @CacheEvict(value = "itens", allEntries = true)
    public ItemDTO updateItem(int id, ItemUpdateDTO updateDTO) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID do item deve ser válido");
        }
        
        if (updateDTO == null) {
            throw new IllegalArgumentException("Dados de atualização não podem ser nulos");
        }
        
        Itens existingItens = itensRepository.findById(id);
        if (existingItens == null) {
            throw new ResourceNotFoundException("Item não encontrado com ID: " + id);
        }
        
        if (existingItens.getDtaRemocao() != null) {
            throw new BusinessException("Item", "atualizar", "Não é possível atualizar um item que já foi removido");
        }
        
        itensMapper.updateFromDTO(existingItens, updateDTO);
        
        Itens updatedItens = itensRepository.save(existingItens);
        return itensMapper.toDTO(updatedItens);
    }

    @Override
    @CacheEvict(value = "itens", allEntries = true)
    public boolean deleteItem(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID do item deve ser válido");
        }
        
        Itens itens = itensRepository.findById(id);
        if (itens == null) {
            throw new ResourceNotFoundException("Item não encontrado com ID: " + id);
        }
        
        if (Boolean.TRUE.equals(itens.getFlgInativo())) {
            throw new BusinessException("Item", "deletar", "O item já está inativo");
        }
        
        itens.setFlgInativo(true);
        itens.setDtaRemocao(LocalDateTime.now());
        itensRepository.save(itens);
        
        return true;
    }

    @Override
    @Cacheable(value = "itens", key = "'user_' + #userId")
    public ItemListDTO getItensByUser(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("ID do usuário deve ser válido");
        }
        
        List<Itens> itens = itensRepository.findByUser(userId);
        return itensMapper.toListDTO(itens);
    }

    @Override
    @Cacheable(value = "itens", key = "'campus_' + #campusId")
    public ItemListDTO getItensByCampus(int campusId) {
        if (campusId <= 0) {
            throw new IllegalArgumentException("ID do campus deve ser válido");
        }
        
        List<Itens> itens = itensRepository.findByCampus(campusId);
        return itensMapper.toListDTO(itens);
    }
    
    /**
     * Cria um item ACHADO com fotos
     * - Valida token JWT e extrai usuário relator
     * - Valida que pelo menos uma foto foi enviada (obrigatório para itens achados)
     * - Valida máximo de 3 fotos
     * - Cria o item
     * - Faz upload das fotos para S3 e vincula ao item
     * - Envia notificação
     */
    @Override
    @CacheEvict(value = "itens", allEntries = true)
    public ItemDTO createItemAchadoComFotos(ItemCreateDTO itemCreateDTO, MultipartFile[] files, String token) {
        logger.info("Iniciando criação de item ACHADO com fotos");

        // 1. Validar token e extrair usuário
        Integer usuarioRelatorId = validarTokenEExtrairUsuario(token);

        // 2. Validar que pelo menos uma foto foi enviada (obrigatório para itens achados)
        validarFotosObrigatorias(files);

        // 3. Validar máximo de fotos
        validarMaximoFotos(files);

        // 4. Configurar tipo e usuário relator
        itemCreateDTO.setTipoItem(Tipo_Item.ACHADO);
        itemCreateDTO.setUsuarioRelatorId(usuarioRelatorId);

        // 5. Criar item
        ItemDTO createdItem = createItem(itemCreateDTO);
        logger.info("Item ACHADO criado com ID: {}", createdItem.getId());

        // 6. Fazer upload das fotos
        uploadFotosParaItem(files, usuarioRelatorId, createdItem.getId());

        // 7. Enviar notificação
        enviarNotificacaoItemCriado(createdItem);

        logger.info("Item ACHADO com fotos criado com sucesso - ID: {}", createdItem.getId());
        return createdItem;
    }

    /**
     * Cria um item PERDIDO com fotos (opcional)
     * - Valida token JWT e extrai usuário relator
     * - Fotos são opcionais para itens perdidos
     * - Se houver fotos, valida máximo de 3
     * - Cria o item
     * - Faz upload das fotos para S3 e vincula ao item (se houver)
     * - Envia notificação
     */
    @Override
    @CacheEvict(value = "itens", allEntries = true)
    public ItemDTO createItemPerdidoComFotos(ItemCreateDTO itemCreateDTO, MultipartFile[] files, String token) {
        logger.info("Iniciando criação de item PERDIDO com fotos");

        // 1. Validar token e extrair usuário
        Integer usuarioRelatorId = validarTokenEExtrairUsuario(token);

        // 2. Se houver fotos, validar máximo
        if (files != null && files.length > 0) {
            validarMaximoFotos(files);
        }

        // 3. Configurar tipo e usuário relator
        itemCreateDTO.setTipoItem(Tipo_Item.PERDIDO);
        itemCreateDTO.setUsuarioRelatorId(usuarioRelatorId);

        // 4. Criar item
        ItemDTO createdItem = createItem(itemCreateDTO);
        logger.info("Item PERDIDO criado com ID: {}", createdItem.getId());

        // 5. Fazer upload das fotos (se houver)
        if (files != null && files.length > 0) {
            uploadFotosParaItem(files, usuarioRelatorId, createdItem.getId());
        }

        // 6. Enviar notificação
        enviarNotificacaoItemCriado(createdItem);

        logger.info("Item PERDIDO criado com sucesso - ID: {}", createdItem.getId());
        return createdItem;
    }

    // ========== MÉTODOS PRIVADOS DE VALIDAÇÃO E PROCESSAMENTO ==========

    /**
     * Valida o token JWT e extrai o ID do usuário relator
     */
    private Integer validarTokenEExtrairUsuario(String token) {
        if (token == null || token.trim().isEmpty()) {
            logger.error("Token JWT não fornecido");
            throw new BusinessException("Autenticação", "validar", "Token JWT é obrigatório");
        }

        String userIdStr = jwtService.getUserIdFromToken(token);
        if (userIdStr == null || userIdStr.trim().isEmpty()) {
            logger.error("Não foi possível extrair userId do token");
            throw new BusinessException("Autenticação", "validar", "Token JWT inválido ou expirado");
        }

        try {
            return Integer.valueOf(userIdStr);
        } catch (NumberFormatException e) {
            logger.error("userId extraído do token não é um número válido: {}", userIdStr);
            throw new BusinessException("Autenticação", "validar", "ID do usuário inválido no token");
        }
    }

    /**
     * Valida que pelo menos uma foto foi enviada (obrigatório para itens achados)
     */
    private void validarFotosObrigatorias(MultipartFile[] files) {
        if (files == null || files.length == 0) {
            logger.error("Nenhuma foto fornecida para item achado");
            throw new BusinessException("Item", "criar", "Pelo menos uma foto é obrigatória para itens achados");
        }

        // Verificar se existe pelo menos um arquivo válido
        boolean hasValidFile = false;
        for (MultipartFile file : files) {
            if (file != null && !file.isEmpty()) {
                hasValidFile = true;
                break;
            }
        }

        if (!hasValidFile) {
            logger.error("Nenhuma foto válida fornecida para item achado");
            throw new BusinessException("Item", "criar", "Pelo menos uma foto válida é obrigatória para itens achados");
        }
    }

    /**
     * Valida que no máximo 3 fotos foram enviadas
     */
    private void validarMaximoFotos(MultipartFile[] files) {
        if (files == null) return;

        int validFilesCount = 0;
        for (MultipartFile file : files) {
            if (file != null && !file.isEmpty()) {
                validFilesCount++;
            }
        }

        if (validFilesCount > MAX_FOTOS_POR_ITEM) {
            logger.error("Tentativa de enviar {} fotos, máximo permitido: {}", validFilesCount, MAX_FOTOS_POR_ITEM);
            throw new BusinessException("Item", "criar",
                String.format("Máximo de %d fotos permitidas por item", MAX_FOTOS_POR_ITEM));
        }
    }

    /**
     * Faz upload das fotos para S3 e vincula ao item
     */
    private void uploadFotosParaItem(MultipartFile[] files, Integer usuarioId, Integer itemId) {
        if (files == null || files.length == 0) return;

        int uploadedCount = 0;
        StringBuilder errorMessages = new StringBuilder();

        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            if (file != null && !file.isEmpty()) {
                try {
                    logger.debug("Fazendo upload da foto {} de {} para item ID: {}", i + 1, files.length, itemId);
                    fotosService.uploadItemPhoto(file, usuarioId, itemId);
                    uploadedCount++;
                    logger.info("Foto {} de {} enviada com sucesso para item ID: {}", uploadedCount, files.length, itemId);
                } catch (Exception e) {
                    String errorMsg = String.format("Foto %d: %s", i + 1, e.getMessage());
                    logger.error("Erro ao fazer upload da foto {} para item ID: {} - {}", i + 1, itemId, e.getMessage(), e);
                    errorMessages.append(errorMsg).append("; ");

                    if (uploadedCount == 0) {
                        throw new BusinessException("Upload de Foto", "fazer upload",
                            "Erro ao fazer upload da foto: " + e.getMessage());
                    }
                }
            }
        }

        if (uploadedCount == 0) {
            logger.error("Nenhuma foto foi enviada com sucesso para o item ID: {}", itemId);
            String errors = errorMessages.toString();
            throw new BusinessException("Upload de Fotos", "fazer upload",
                "Erro ao fazer upload das fotos. Detalhes: " + (errors.isEmpty() ? "Nenhum arquivo válido fornecido" : errors));
        }

        if (errorMessages.length() > 0) {
            logger.warn("Algumas fotos falharam ao fazer upload para item ID: {} - {}", itemId, errorMessages);
        }

        logger.info("✅ {} foto(s) enviada(s) com sucesso para item ID: {}", uploadedCount, itemId);
    }

    /**
     * Envia notificação de item criado de forma assíncrona
     */
    private void enviarNotificacaoItemCriado(ItemDTO item) {
        try {
            notificationService.sendItemFoundNotification(item.getId(), item.getUsuarioRelatorId());
            logger.debug("Notificação enviada para item ID: {}", item.getId());
        } catch (Exception e) {
            // Não falha a criação do item se notificação falhar
            logger.error("Erro ao enviar notificação para item ID: {}", item.getId(), e);
        }
    }

    private void validarItemParaCriacao(ItemCreateDTO createDTO) {
        if (!StringUtils.hasText(createDTO.getNome())) {
            throw new BusinessException("Item", "criar", "Nome é obrigatório");
        }
        
        if (createDTO.getNome().length() < 3) {
            throw new BusinessException("Item", "criar", "Nome deve ter no mínimo 3 caracteres");
        }
        
        if (!StringUtils.hasText(createDTO.getDescricao())) {
            throw new BusinessException("Item", "criar", "Descrição é obrigatória");
        }
        
        if (createDTO.getTipoItem() == null) {
            throw new BusinessException("Item", "criar", "Tipo do item é obrigatório");
        }
        
        if (createDTO.getDescLocalItem() == null || createDTO.getDescLocalItem().trim().isEmpty()) {
            throw new BusinessException("Item", "criar", "Descrição do local é obrigatória");
        }
        
        if (createDTO.getUsuarioRelatorId() == null || createDTO.getUsuarioRelatorId() <= 0) {
            throw new BusinessException("Item", "criar", "ID do usuário relator é obrigatório e deve ser válido");
        }
        
        // Verificar se o usuário existe
        if (createDTO.getUsuarioRelatorId() != null) {
            if (usuariosRepository.findById(createDTO.getUsuarioRelatorId()) == null) {
                throw new ResourceNotFoundException("Usuário", "ID", createDTO.getUsuarioRelatorId());
            }
        }
    }
}
