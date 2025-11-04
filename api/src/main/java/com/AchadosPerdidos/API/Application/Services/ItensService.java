package com.AchadosPerdidos.API.Application.Services;

import com.AchadosPerdidos.API.Application.DTOs.Itens.ItensDTO;
import com.AchadosPerdidos.API.Application.DTOs.Itens.ItensListDTO;
import com.AchadosPerdidos.API.Application.DTOs.Itens.ItensCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Itens.ItensUpdateDTO;
import com.AchadosPerdidos.API.Application.Mapper.ItensModelMapper;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IItensService;
import com.AchadosPerdidos.API.Domain.Entity.Itens;
import com.AchadosPerdidos.API.Domain.Repository.ItensRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ItensService implements IItensService {

    @Autowired
    private ItensRepository itensRepository;

    @Autowired
    private ItensModelMapper itensModelMapper;

    @Override
    @Cacheable(value = "itens", key = "'all'")
    public ItensListDTO getAllItens() {
        List<Itens> itens = itensRepository.findAll();
        return itensModelMapper.toListDTO(itens);
    }

    @Override
    @Cacheable(value = "itens", key = "#id")
    public ItensDTO getItemById(int id) {
        Itens itens = itensRepository.findById(id);
        return itensModelMapper.toDTO(itens);
    }

    @Override
    @CacheEvict(value = "itens", allEntries = true)
    public ItensDTO createItem(ItensDTO itensDTO) {
        Itens itens = itensModelMapper.toEntity(itensDTO);
        itens.setDtaCriacao(new Date());
        if (itens.getEncontradoEm() == null) {
            itens.setEncontradoEm(new Date());
        }
        itens.setFlgInativo(false);
        
        Itens savedItens = itensRepository.save(itens);
        return itensModelMapper.toDTO(savedItens);
    }

    @Override
    @CacheEvict(value = "itens", allEntries = true)
    public ItensDTO createItemFromDTO(ItensCreateDTO createDTO) {
        Itens itens = new Itens();
        itens.setNome(createDTO.getNome());
        itens.setDescricao(createDTO.getDescricao());
        itens.setStatusItemId(createDTO.getStatusItemId());
        itens.setLocalId(createDTO.getLocalId());
        itens.setEncontradoEm(createDTO.getEncontradoEm() != null ? createDTO.getEncontradoEm() : new Date());
        itens.setDtaCriacao(new Date());
        itens.setFlgInativo(false);
        
        Itens savedItens = itensRepository.save(itens);
        return itensModelMapper.toDTO(savedItens);
    }

    @Override
    @CacheEvict(value = "itens", allEntries = true)
    public ItensDTO updateItem(int id, ItensDTO itensDTO) {
        Itens existingItens = itensRepository.findById(id);
        if (existingItens == null) {
            return null;
        }
        
        existingItens.setNome(itensDTO.getNome());
        existingItens.setDescricao(itensDTO.getDescricao());
        existingItens.setEncontradoEm(itensDTO.getEncontradoEm());
        existingItens.setFlgInativo(itensDTO.getFlgInativo());
        existingItens.setStatusItemId(itensDTO.getStatusItemId());
        existingItens.setUsuarioRelatorId(itensDTO.getUsuarioRelatorId());
        existingItens.setLocalId(itensDTO.getLocalId());
        existingItens.setDtaRemocao(itensDTO.getDtaRemocao());
        
        Itens updatedItens = itensRepository.save(existingItens);
        return itensModelMapper.toDTO(updatedItens);
    }

    @Override
    @CacheEvict(value = "itens", allEntries = true)
    public ItensDTO updateItemFromDTO(int id, ItensUpdateDTO updateDTO) {
        Itens existingItens = itensRepository.findById(id);
        if (existingItens == null) {
            return null;
        }
        
        // Atualizar apenas os campos fornecidos
        if (updateDTO.getNome() != null) {
            existingItens.setNome(updateDTO.getNome());
        }
        if (updateDTO.getDescricao() != null) {
            existingItens.setDescricao(updateDTO.getDescricao());
        }
        if (updateDTO.getFlgInativo() != null) {
            existingItens.setFlgInativo(updateDTO.getFlgInativo());
        }
        if (updateDTO.getStatusItemId() != null) {
            existingItens.setStatusItemId(updateDTO.getStatusItemId());
        }
        if (updateDTO.getLocalId() != null) {
            existingItens.setLocalId(updateDTO.getLocalId());
        }
        if (updateDTO.getEncontradoEm() != null) {
            existingItens.setEncontradoEm(updateDTO.getEncontradoEm());
        }
        
        Itens updatedItens = itensRepository.save(existingItens);
        return itensModelMapper.toDTO(updatedItens);
    }

    @Override
    @CacheEvict(value = "itens", allEntries = true)
    public boolean deleteItem(int id) {
        Itens itens = itensRepository.findById(id);
        if (itens == null) {
            return false;
        }
        
        return itensRepository.deleteById(id);
    }

    @Override
    @Cacheable(value = "itens", key = "'active'")
    public ItensListDTO getActiveItens() {
        List<Itens> activeItens = itensRepository.findActive();
        return itensModelMapper.toListDTO(activeItens);
    }

    @Override
    @Cacheable(value = "itens", key = "'status_' + #statusId")
    public ItensListDTO getItensByStatus(int statusId) {
        List<Itens> itens = itensRepository.findByStatus(statusId);
        return itensModelMapper.toListDTO(itens);
    }

    @Override
    @Cacheable(value = "itens", key = "'user_' + #userId")
    public ItensListDTO getItensByUser(int userId) {
        List<Itens> itens = itensRepository.findByUser(userId);
        return itensModelMapper.toListDTO(itens);
    }

    @Override
    @Cacheable(value = "itens", key = "'campus_' + #campusId")
    public ItensListDTO getItensByCampus(int campusId) {
        List<Itens> itens = itensRepository.findByCampus(campusId);
        return itensModelMapper.toListDTO(itens);
    }

    @Override
    @Cacheable(value = "itens", key = "'local_' + #localId")
    public ItensListDTO getItensByLocal(int localId) {
        List<Itens> itens = itensRepository.findByLocal(localId);
        return itensModelMapper.toListDTO(itens);
    }

    @Override
    @Cacheable(value = "itens", key = "'empresa_' + #empresaId")
    public ItensListDTO getItensByEmpresa(int empresaId) {
        List<Itens> itens = itensRepository.findByEmpresa(empresaId);
        return itensModelMapper.toListDTO(itens);
    }

    @Override
    @Cacheable(value = "itens", key = "'search_' + #searchTerm")
    public ItensListDTO searchItens(String searchTerm) {
        List<Itens> itens = itensRepository.searchByTerm(searchTerm);
        return itensModelMapper.toListDTO(itens);
    }

    @Override
    public List<Itens> getItemsNearDonationDeadline(int daysFromNow) {
        return itensRepository.findItemsNearDonationDeadline(daysFromNow);
    }

    @Override
    public List<Itens> getExpiredItems(int daysExpired) {
        return itensRepository.findExpiredItems(daysExpired);
    }

    @Override
    public boolean markItemAsDonated(int itemId) {
        Itens item = itensRepository.findById(itemId);
        if (item != null) {
            // Status 3 = "Doado" (conforme status_item)
            item.setStatusItemId(3);
            itensRepository.save(item);
            return true;
        }
        return false;
    }

    @Override
    public Itens getItemEntityById(int id) {
        return itensRepository.findById(id);
    }
}
