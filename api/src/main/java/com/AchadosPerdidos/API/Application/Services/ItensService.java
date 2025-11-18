package com.AchadosPerdidos.API.Application.Services;

import com.AchadosPerdidos.API.Application.DTOs.Item.ItemDTO;
import com.AchadosPerdidos.API.Application.DTOs.Item.ItemListDTO;
import com.AchadosPerdidos.API.Application.DTOs.Item.ItemCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Item.ItemUpdateDTO;
import com.AchadosPerdidos.API.Application.Mapper.ItensModelMapper;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IItensService;
import com.AchadosPerdidos.API.Domain.Entity.Itens;
import com.AchadosPerdidos.API.Domain.Enum.Tipo_ItemEnum;
import com.AchadosPerdidos.API.Domain.Repository.ItensRepository;
import com.AchadosPerdidos.API.Domain.Repository.LocalRepository;
import com.AchadosPerdidos.API.Domain.Repository.UsuariosRepository;
import com.AchadosPerdidos.API.Exeptions.BusinessException;
import com.AchadosPerdidos.API.Exeptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ItensService implements IItensService {

    @Autowired
    private ItensRepository itensRepository;

    @Autowired
    private ItensModelMapper itensModelMapper;
    
    @Autowired
    private LocalRepository localRepository;
    
    @Autowired
    private UsuariosRepository usuariosRepository;

    @Override
    @Cacheable(value = "itens", key = "'all'")
    public ItemListDTO getAllItens() {
        List<Itens> itens = itensRepository.findAll();
        return itensModelMapper.toListDTO(itens);
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
        return itensModelMapper.toDTO(itens);
    }

    @Override
    @CacheEvict(value = "itens", allEntries = true)
    public ItemDTO createItem(ItemCreateDTO createDTO) {
        if (createDTO == null) {
            throw new IllegalArgumentException("Dados do item não podem ser nulos");
        }
        
        validarItemParaCriacao(createDTO);
        
        Itens itens = itensModelMapper.fromCreateDTO(createDTO);
        itens.setDtaCriacao(LocalDateTime.now());
        itens.setFlgInativo(false);
        
        Itens savedItens = itensRepository.save(itens);
        return itensModelMapper.toDTO(savedItens);
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
        
        itensModelMapper.updateFromDTO(existingItens, updateDTO);
        
        Itens updatedItens = itensRepository.save(existingItens);
        return itensModelMapper.toDTO(updatedItens);
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
    @Cacheable(value = "itens", key = "'active'")
    public ItemListDTO getActiveItens() {
        List<Itens> activeItens = itensRepository.findActive();
        return itensModelMapper.toListDTO(activeItens);
    }

    @Override
    @Cacheable(value = "itens", key = "'user_' + #userId")
    public ItemListDTO getItensByUser(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("ID do usuário deve ser válido");
        }
        
        List<Itens> itens = itensRepository.findByUser(userId);
        return itensModelMapper.toListDTO(itens);
    }

    @Override
    @Cacheable(value = "itens", key = "'campus_' + #campusId")
    public ItemListDTO getItensByCampus(int campusId) {
        if (campusId <= 0) {
            throw new IllegalArgumentException("ID do campus deve ser válido");
        }
        
        List<Itens> itens = itensRepository.findByCampus(campusId);
        return itensModelMapper.toListDTO(itens);
    }

    @Override
    @Cacheable(value = "itens", key = "'local_' + #localId")
    public ItemListDTO getItensByLocal(int localId) {
        if (localId <= 0) {
            throw new IllegalArgumentException("ID do local deve ser válido");
        }
        
        List<Itens> itens = itensRepository.findByLocal(localId);
        return itensModelMapper.toListDTO(itens);
    }

    @Override
    @Cacheable(value = "itens", key = "'search_' + #searchTerm")
    public ItemListDTO searchItens(String searchTerm) {
        if (!StringUtils.hasText(searchTerm)) {
            throw new IllegalArgumentException("Termo de busca não pode ser vazio");
        }
        
        List<Itens> itens = itensRepository.searchByTerm(searchTerm);
        return itensModelMapper.toListDTO(itens);
    }

    @Override
    @Cacheable(value = "itens", key = "'tipo_' + #tipo")
    public ItemListDTO getItensByTipo(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo não pode ser vazio");
        }
        
        // Validar tipo usando enum
        try {
            Tipo_ItemEnum.valueOf(tipo.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Item", "buscar", "Tipo deve ser 'PERDIDO', 'ACHADO' ou 'DOADO'");
        }
        
        String tipoUpper = tipo.toUpperCase();
        List<Itens> itens = itensRepository.findByTipo(tipoUpper);
        return itensModelMapper.toListDTO(itens);
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
        
        if (createDTO.getLocalId() == null || createDTO.getLocalId() <= 0) {
            throw new BusinessException("Item", "criar", "ID do local é obrigatório e deve ser válido");
        }
        
        if (localRepository.findById(createDTO.getLocalId()) == null) {
            throw new ResourceNotFoundException("Local", "ID", createDTO.getLocalId());
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
