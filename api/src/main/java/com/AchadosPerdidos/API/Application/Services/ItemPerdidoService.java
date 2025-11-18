package com.AchadosPerdidos.API.Application.Services;

import com.AchadosPerdidos.API.Application.DTOs.ItemPerdido.ItemPerdidoDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemPerdido.ItemPerdidoListDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemPerdido.ItemPerdidoCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.ItemPerdido.ItemPerdidoUpdateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Fotos.FotosCreateDTO;
import com.AchadosPerdidos.API.Application.Mapper.ItemPerdidoModelMapper;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IItemPerdidoService;
import com.AchadosPerdidos.API.Domain.Entity.ItemPerdido;
import com.AchadosPerdidos.API.Domain.Entity.Itens;
import com.AchadosPerdidos.API.Domain.Entity.Foto;
import com.AchadosPerdidos.API.Domain.Entity.FotoItem;
import com.AchadosPerdidos.API.Domain.Enum.Tipo_ItemEnum;
import com.AchadosPerdidos.API.Domain.Repository.ItemPerdidoRepository;
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
public class ItemPerdidoService implements IItemPerdidoService {

    @Autowired
    private ItemPerdidoRepository itemPerdidoRepository;

    @Autowired
    private ItemPerdidoModelMapper itemPerdidoModelMapper;
    
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
    @Cacheable(value = "itensPerdidos", key = "'all'")
    public ItemPerdidoListDTO getAllItensPerdidos() {
        return itemPerdidoModelMapper.toListDTO(itemPerdidoRepository.findAll());
    }

    @Override
    @Cacheable(value = "itensPerdidos", key = "#id")
    public ItemPerdidoDTO getItemPerdidoById(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID do item perdido deve ser válido");
        }
        
        ItemPerdido itemPerdido = itemPerdidoRepository.findById(id);
        if (itemPerdido == null) {
            throw new ResourceNotFoundException("Item perdido não encontrado com ID: " + id);
        }
        return itemPerdidoModelMapper.toDTO(itemPerdido);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"itensPerdidos", "itens"}, allEntries = true)
    public ItemPerdidoDTO createItemPerdido(ItemPerdidoCreateDTO createDTO) {
        if (createDTO == null) {
            throw new IllegalArgumentException("Dados do item perdido não podem ser nulos");
        }
        
        // Validações dos dados do item
        if (createDTO.getNome() == null || createDTO.getNome().trim().isEmpty()) {
            throw new BusinessException("ItemPerdido", "criar", "Nome do item é obrigatório");
        }
        
        if (createDTO.getDescricao() == null || createDTO.getDescricao().trim().isEmpty()) {
            throw new BusinessException("ItemPerdido", "criar", "Descrição do item é obrigatória");
        }
        
        if (createDTO.getLocalId() == null || createDTO.getLocalId() <= 0) {
            throw new BusinessException("ItemPerdido", "criar", "ID do local é obrigatório e deve ser válido");
        }
        
        if (createDTO.getUsuarioRelatorId() == null || createDTO.getUsuarioRelatorId() <= 0) {
            throw new BusinessException("ItemPerdido", "criar", "ID do usuário relator é obrigatório e deve ser válido");
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
        item.setTipoItem(Tipo_ItemEnum.PERDIDO);
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
        
        // 4. Criar o registro na tabela itens_perdidos
        ItemPerdido itemPerdido = new ItemPerdido();
        itemPerdido.setId(savedItem.getId()); // O ID é o mesmo do item
        itemPerdido.setPerdido_em(createDTO.getPerdidoEm() != null ? 
            createDTO.getPerdidoEm() : LocalDateTime.now());
        itemPerdido.setDta_Criacao(LocalDateTime.now());
        itemPerdido.setFlg_Inativo(false);
        
        ItemPerdido savedItemPerdido = itemPerdidoRepository.save(itemPerdido);
        return itemPerdidoModelMapper.toDTO(savedItemPerdido);
    }

    @Override
    @CacheEvict(value = "itensPerdidos", allEntries = true)
    public ItemPerdidoDTO updateItemPerdido(Integer id, ItemPerdidoUpdateDTO updateDTO) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID do item perdido deve ser válido");
        }
        
        if (updateDTO == null) {
            throw new IllegalArgumentException("Dados de atualização não podem ser nulos");
        }
        
        ItemPerdido existingItemPerdido = itemPerdidoRepository.findById(id);
        if (existingItemPerdido == null) {
            throw new ResourceNotFoundException("Item perdido não encontrado com ID: " + id);
        }
        
        if (existingItemPerdido.getDta_Remocao() != null) {
            throw new BusinessException("ItemPerdido", "atualizar", "Não é possível atualizar um item perdido que já foi removido");
        }
        
        itemPerdidoModelMapper.updateFromDTO(existingItemPerdido, updateDTO);
        
        ItemPerdido updatedItemPerdido = itemPerdidoRepository.save(existingItemPerdido);
        return itemPerdidoModelMapper.toDTO(updatedItemPerdido);
    }

    @Override
    @CacheEvict(value = "itensPerdidos", allEntries = true)
    public boolean deleteItemPerdido(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID do item perdido deve ser válido");
        }
        
        ItemPerdido itemPerdido = itemPerdidoRepository.findById(id);
        if (itemPerdido == null) {
            throw new ResourceNotFoundException("Item perdido não encontrado com ID: " + id);
        }
        
        if (Boolean.TRUE.equals(itemPerdido.getFlg_Inativo())) {
            throw new BusinessException("ItemPerdido", "deletar", "O item perdido já está inativo");
        }
        
        itemPerdido.setFlg_Inativo(true);
        itemPerdido.setDta_Remocao(LocalDateTime.now());
        itemPerdidoRepository.save(itemPerdido);
        
        return true;
    }

    @Override
    @Cacheable(value = "itensPerdidos", key = "'active'")
    public ItemPerdidoListDTO getActiveItensPerdidos() {
        return itemPerdidoModelMapper.toListDTO(itemPerdidoRepository.findActive());
    }

    @Override
    @Cacheable(value = "itensPerdidos", key = "'item_' + #itemId")
    public ItemPerdidoDTO getItemPerdidoByItemId(Integer itemId) {
        if (itemId == null || itemId <= 0) {
            throw new IllegalArgumentException("ID do item deve ser válido");
        }
        
        ItemPerdido itemPerdido = itemPerdidoRepository.findByItemId(itemId);
        if (itemPerdido == null) {
            throw new ResourceNotFoundException("Item perdido não encontrado para o item com ID: " + itemId);
        }
        return itemPerdidoModelMapper.toDTO(itemPerdido);
    }
}
