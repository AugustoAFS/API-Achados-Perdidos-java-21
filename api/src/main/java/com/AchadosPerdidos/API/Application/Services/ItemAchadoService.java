package com.AchadosPerdidos.API.Application.Services;

import com.AchadosPerdidos.API.Application.DTOs.ItemAchado.ItemAchadoDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemAchado.ItemAchadoListDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemAchado.ItemAchadoCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemAchado.ItemAchadoUpdateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Fotos.FotosCreateDTO;
import com.AchadosPerdidos.API.Application.Mapper.ItemAchadoModelMapper;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IItemAchadoService;
import com.AchadosPerdidos.API.Domain.Entity.ItemAchado;
import com.AchadosPerdidos.API.Domain.Entity.Itens;
import com.AchadosPerdidos.API.Domain.Entity.Foto;
import com.AchadosPerdidos.API.Domain.Entity.FotoItem;
import com.AchadosPerdidos.API.Domain.Enum.Tipo_ItemEnum;
import com.AchadosPerdidos.API.Domain.Repository.ItemAchadoRepository;
import com.AchadosPerdidos.API.Domain.Repository.ItensRepository;
import com.AchadosPerdidos.API.Domain.Repository.FotosRepository;
import com.AchadosPerdidos.API.Domain.Repository.FotoItemRepository;
import com.AchadosPerdidos.API.Domain.Repository.UsuariosRepository;
import com.AchadosPerdidos.API.Domain.Repository.LocalRepository;
import com.AchadosPerdidos.API.Exeptions.BusinessException;
import com.AchadosPerdidos.API.Exeptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ItemAchadoService implements IItemAchadoService {

    @Autowired
    private ItemAchadoRepository itemAchadoRepository;

    @Autowired
    private ItemAchadoModelMapper itemAchadoModelMapper;
    
    @Autowired
    private ItensRepository itensRepository;
    
    @Autowired
    private FotosRepository fotosRepository;
    
    @Autowired
    private FotoItemRepository fotoItemRepository;
    
    @Autowired
    private UsuariosRepository usuariosRepository;
    
    @Autowired
    private LocalRepository localRepository;

    @Override
    @Cacheable(value = "itensAchados", key = "'all'")
    public ItemAchadoListDTO getAllItensAchados() {
        return itemAchadoModelMapper.toListDTO(itemAchadoRepository.findAll());
    }

    @Override
    @Cacheable(value = "itensAchados", key = "#id")
    public ItemAchadoDTO getItemAchadoById(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID do item achado deve ser válido");
        }
        
        ItemAchado itemAchado = itemAchadoRepository.findById(id);
        if (itemAchado == null) {
            throw new ResourceNotFoundException("Item achado não encontrado com ID: " + id);
        }
        return itemAchadoModelMapper.toDTO(itemAchado);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"itensAchados", "itens"}, allEntries = true)
    public ItemAchadoDTO createItemAchado(ItemAchadoCreateDTO createDTO) {
        if (createDTO == null) {
            throw new IllegalArgumentException("Dados do item achado não podem ser nulos");
        }
        
        // Validações dos dados do item
        if (createDTO.getNome() == null || createDTO.getNome().trim().isEmpty()) {
            throw new BusinessException("ItemAchado", "criar", "Nome do item é obrigatório");
        }
        
        if (createDTO.getDescricao() == null || createDTO.getDescricao().trim().isEmpty()) {
            throw new BusinessException("ItemAchado", "criar", "Descrição do item é obrigatória");
        }
        
        if (createDTO.getLocalId() == null || createDTO.getLocalId() <= 0) {
            throw new BusinessException("ItemAchado", "criar", "ID do local é obrigatório e deve ser válido");
        }
        
        if (createDTO.getUsuarioRelatorId() == null || createDTO.getUsuarioRelatorId() <= 0) {
            throw new BusinessException("ItemAchado", "criar", "ID do usuário relator é obrigatório e deve ser válido");
        }
        
        // Verificar se o usuário existe
        if (usuariosRepository.findById(createDTO.getUsuarioRelatorId()) == null) {
            throw new ResourceNotFoundException("Usuário", "ID", createDTO.getUsuarioRelatorId());
        }
        
        // Verificar se o local existe
        if (localRepository.findById(createDTO.getLocalId()) == null) {
            throw new ResourceNotFoundException("Local", "ID", createDTO.getLocalId());
        }
        
        // 1. Criar o Item primeiro
        Itens item = new Itens();
        item.setNome(createDTO.getNome());
        item.setDescricao(createDTO.getDescricao());
        item.setTipoItem(Tipo_ItemEnum.ACHADO);
        item.setLocal_id(createDTO.getLocalId());
        item.setUsuario_relator_id(createDTO.getUsuarioRelatorId());
        item.setDtaCriacao(LocalDateTime.now());
        item.setFlgInativo(false);
        
        Itens savedItem = itensRepository.save(item);
        
        // 2. Criar as fotos do item (se houver)
        if (createDTO.getFotos() != null && !createDTO.getFotos().isEmpty()) {
            for (FotosCreateDTO fotoDTO : createDTO.getFotos()) {
                Foto foto = new Foto();
                foto.setUrl(fotoDTO.getUrl());
                foto.setProvedorArmazenamento(fotoDTO.getProvedorArmazenamento() != null ? 
                    fotoDTO.getProvedorArmazenamento().name() : "local");
                foto.setChaveArmazenamento(fotoDTO.getChaveArmazenamento());
                foto.setNomeArquivoOriginal(fotoDTO.getNomeArquivoOriginal());
                foto.setTamanhoArquivoBytes(fotoDTO.getTamanhoArquivoBytes());
                foto.setDtaCriacao(LocalDateTime.now());
                foto.setFlgInativo(false);
                
                Foto savedFoto = fotosRepository.save(foto);
                
                // 3. Associar a foto ao item
                FotoItem fotoItem = new FotoItem();
                fotoItem.setItemId(savedItem.getId());
                fotoItem.setFotoId(savedFoto.getId());
                fotoItem.setDtaCriacao(LocalDateTime.now());
                fotoItem.setFlgInativo(false);
                
                fotoItemRepository.save(fotoItem);
            }
        }
        
        // 4. Criar o registro na tabela itens_achados
        ItemAchado itemAchado = new ItemAchado();
        itemAchado.setId(savedItem.getId()); // O ID é o mesmo do item
        itemAchado.setEncontrado_em(createDTO.getEncontradoEm() != null ? 
            createDTO.getEncontradoEm() : LocalDateTime.now());
        itemAchado.setDtaCriacao(LocalDateTime.now());
        itemAchado.setFlgInativo(false);
        
        ItemAchado savedItemAchado = itemAchadoRepository.save(itemAchado);
        return itemAchadoModelMapper.toDTO(savedItemAchado);
    }

    @Override
    @CacheEvict(value = "itensAchados", allEntries = true)
    public ItemAchadoDTO updateItemAchado(Integer id, ItemAchadoUpdateDTO updateDTO) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID do item achado deve ser válido");
        }
        
        if (updateDTO == null) {
            throw new IllegalArgumentException("Dados de atualização não podem ser nulos");
        }
        
        ItemAchado existingItemAchado = itemAchadoRepository.findById(id);
        if (existingItemAchado == null) {
            throw new ResourceNotFoundException("Item achado não encontrado com ID: " + id);
        }
        
        if (existingItemAchado.getDtaRemocao() != null) {
            throw new BusinessException("ItemAchado", "atualizar", "Não é possível atualizar um item achado que já foi removido");
        }
        
        itemAchadoModelMapper.updateFromDTO(existingItemAchado, updateDTO);
        
        ItemAchado updatedItemAchado = itemAchadoRepository.save(existingItemAchado);
        return itemAchadoModelMapper.toDTO(updatedItemAchado);
    }

    @Override
    @CacheEvict(value = "itensAchados", allEntries = true)
    public boolean deleteItemAchado(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID do item achado deve ser válido");
        }
        
        ItemAchado itemAchado = itemAchadoRepository.findById(id);
        if (itemAchado == null) {
            throw new ResourceNotFoundException("Item achado não encontrado com ID: " + id);
        }
        
        if (Boolean.TRUE.equals(itemAchado.getFlgInativo())) {
            throw new BusinessException("ItemAchado", "deletar", "O item achado já está inativo");
        }
        
        itemAchado.setFlgInativo(true);
        itemAchado.setDtaRemocao(LocalDateTime.now());
        itemAchadoRepository.save(itemAchado);
        
        return true;
    }

    @Override
    @Cacheable(value = "itensAchados", key = "'active'")
    public ItemAchadoListDTO getActiveItensAchados() {
        return itemAchadoModelMapper.toListDTO(itemAchadoRepository.findActive());
    }

    @Override
    @Cacheable(value = "itensAchados", key = "'item_' + #itemId")
    public ItemAchadoDTO getItemAchadoByItemId(Integer itemId) {
        if (itemId == null || itemId <= 0) {
            throw new IllegalArgumentException("ID do item deve ser válido");
        }
        
        ItemAchado itemAchado = itemAchadoRepository.findByItemId(itemId);
        if (itemAchado == null) {
            throw new ResourceNotFoundException("Item achado não encontrado para o item com ID: " + itemId);
        }
        return itemAchadoModelMapper.toDTO(itemAchado);
    }
}
