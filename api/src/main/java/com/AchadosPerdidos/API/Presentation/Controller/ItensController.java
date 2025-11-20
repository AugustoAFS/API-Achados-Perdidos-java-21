package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.DTOs.Item.ItemDTO;
import com.AchadosPerdidos.API.Application.DTOs.Item.ItemListDTO;
import com.AchadosPerdidos.API.Application.DTOs.Item.ItemCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Item.ItemUpdateDTO;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IItensService;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IJWTService;
import com.AchadosPerdidos.API.Application.Services.Interfaces.INotificationService;
import com.AchadosPerdidos.API.Application.Services.FotosService;
import com.AchadosPerdidos.API.Domain.Enum.Tipo_Item;
import com.AchadosPerdidos.API.Exeptions.BusinessException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/itens")
@Tag(name = "Itens", description = "API para gerenciamento de itens achados/perdidos/doados")
public class ItensController {

    @Autowired
    private IItensService itensService;

    @Autowired
    private INotificationService notificationService;

    @Autowired
    private IJWTService jwtService;

    @Autowired
    private FotosService fotosService;

    @GetMapping
    @Operation(summary = "Listar todos os itens")
    public ResponseEntity<ItemListDTO> getAllItens() {
        ItemListDTO itens = itensService.getAllItens();
        return ResponseEntity.ok(itens);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar item por ID")
    public ResponseEntity<ItemDTO> getItemById(@PathVariable int id) {
        ItemDTO item = itensService.getItemById(id);
        return ResponseEntity.ok(item);
    }

    @PostMapping
    @Operation(summary = "Criar novo item", description = "Cria um novo item. O usuário relator é obtido automaticamente do token JWT.")
    public ResponseEntity<ItemDTO> createItem(@RequestBody ItemCreateDTO itemCreateDTO, HttpServletRequest request) {
        try {
            // Extrair token JWT do header Authorization
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            
            String token = authHeader.substring(7);
            
            // Obter userId do token
            String userIdStr = jwtService.getUserIdFromToken(token);
            if (userIdStr == null || userIdStr.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            
            Integer usuarioRelatorId = Integer.valueOf(userIdStr);
            
            // Definir usuário relator no DTO
            itemCreateDTO.setUsuarioRelatorId(usuarioRelatorId);
            
            // Criar item
            ItemDTO createdItem = itensService.createItem(itemCreateDTO);
            
            // Envia notificação automática quando item é criado
            if (createdItem != null) {
                notificationService.notifyItemFound(createdItem.getId(), createdItem.getUsuarioRelatorId());
            }
            
            return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar item")
    public ResponseEntity<ItemDTO> updateItem(@PathVariable int id, @RequestBody ItemUpdateDTO itemUpdateDTO) {
        ItemDTO updatedItem = itensService.updateItem(id, itemUpdateDTO);
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar item (soft delete)")
    public ResponseEntity<Void> deleteItem(@PathVariable int id) {
        boolean deleted = itensService.deleteItem(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/active")
    @Operation(summary = "Listar itens ativos")
    public ResponseEntity<ItemListDTO> getActiveItens() {
        ItemListDTO activeItens = itensService.getActiveItens();
        return ResponseEntity.ok(activeItens);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Buscar itens por usuário")
    public ResponseEntity<ItemListDTO> getItensByUser(@PathVariable int userId) {
        ItemListDTO itens = itensService.getItensByUser(userId);
        return ResponseEntity.ok(itens);
    }

    @GetMapping("/campus/{campusId}")
    @Operation(summary = "Buscar itens por campus")
    public ResponseEntity<ItemListDTO> getItensByCampus(@PathVariable int campusId) {
        ItemListDTO itens = itensService.getItensByCampus(campusId);
        return ResponseEntity.ok(itens);
    }


    @GetMapping("/search")
    @Operation(summary = "Buscar itens por termo")
    public ResponseEntity<ItemListDTO> searchItens(@RequestParam String term) {
        ItemListDTO itens = itensService.searchItens(term);
        return ResponseEntity.ok(itens);
    }

    @GetMapping("/tipo/{tipo}")
    @Operation(summary = "Buscar itens por tipo", description = "Tipos disponíveis: PERDIDO, ACHADO, DOADO")
    public ResponseEntity<ItemListDTO> getItensByTipo(@PathVariable String tipo) {
        ItemListDTO itens = itensService.getItensByTipo(tipo);
        return ResponseEntity.ok(itens);
    }

    @GetMapping("/perdidos")
    @Operation(summary = "Buscar itens perdidos", description = "Retorna uma lista de todos os itens perdidos ativos")
    public ResponseEntity<ItemListDTO> getItensPerdidos() {
        ItemListDTO itens = itensService.getItensByTipo("PERDIDO");
        return ResponseEntity.ok(itens);
    }

    @GetMapping("/achados")
    @Operation(summary = "Buscar itens achados", description = "Retorna uma lista de todos os itens achados ativos")
    public ResponseEntity<ItemListDTO> getItensAchados() {
        ItemListDTO itens = itensService.getItensByTipo("ACHADO");
        return ResponseEntity.ok(itens);
    }

    @GetMapping("/doados")
    @Operation(summary = "Buscar itens doados", description = "Retorna uma lista de todos os itens doados ativos")
    public ResponseEntity<ItemListDTO> getItensDoados() {
        ItemListDTO itens = itensService.getItensByTipo("DOADO");
        return ResponseEntity.ok(itens);
    }

    @PostMapping(value = "/perdidos", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Criar item perdido", description = "Cria um novo item perdido. O usuário relator é obtido automaticamente do token JWT.")
    public ResponseEntity<ItemDTO> createItemPerdido(@RequestBody ItemCreateDTO itemCreateDTO, HttpServletRequest request) {
        try {
            // Definir tipo como PERDIDO
            itemCreateDTO.setTipoItem(Tipo_Item.PERDIDO);
            
            // Extrair token JWT do header Authorization
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            
            String token = authHeader.substring(7);
            
            // Obter userId do token
            String userIdStr = jwtService.getUserIdFromToken(token);
            if (userIdStr == null || userIdStr.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            
            Integer usuarioRelatorId = Integer.valueOf(userIdStr);
            itemCreateDTO.setUsuarioRelatorId(usuarioRelatorId);
            
            // Criar item
            ItemDTO createdItem = itensService.createItem(itemCreateDTO);
            
            // Envia notificação automática quando item é criado
            if (createdItem != null) {
                notificationService.notifyItemFound(createdItem.getId(), createdItem.getUsuarioRelatorId());
            }
            
            return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(value = "/perdidos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Criar item perdido com foto(s)", description = "Cria um novo item perdido com uma ou mais fotos em uma única requisição. O usuário relator é obtido automaticamente do token JWT.")
    public ResponseEntity<ItemDTO> createItemPerdidoWithPhoto(
            @Parameter(description = "Dados do item (JSON)") @RequestPart("item") ItemCreateDTO itemCreateDTO,
            @Parameter(description = "Arquivo(s) de imagem do item (pode enviar múltiplas fotos)") @RequestPart(value = "files", required = false) MultipartFile[] files,
            HttpServletRequest request) {
        try {
            // Definir tipo como PERDIDO
            itemCreateDTO.setTipoItem(Tipo_Item.PERDIDO);
            
            // Extrair token JWT do header Authorization
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            
            String token = authHeader.substring(7);
            
            // Obter userId do token
            String userIdStr = jwtService.getUserIdFromToken(token);
            if (userIdStr == null || userIdStr.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            
            Integer usuarioRelatorId = Integer.valueOf(userIdStr);
            itemCreateDTO.setUsuarioRelatorId(usuarioRelatorId);
            
            // Criar item
            ItemDTO createdItem = itensService.createItem(itemCreateDTO);
            
            // Se houver fotos, fazer upload e associar ao item
            if (files != null && files.length > 0 && createdItem != null) {
                for (MultipartFile file : files) {
                    if (file != null && !file.isEmpty()) {
                        try {
                            fotosService.uploadItemPhoto(file, usuarioRelatorId, createdItem.getId());
                        } catch (Exception e) {
                            // Log erro mas não falha a criação do item
                            // O item já foi criado, apenas esta foto falhou
                            // A exceção já foi logada no FotosService
                        }
                    }
                }
            }
            
            // Envia notificação automática quando item é criado
            if (createdItem != null) {
                notificationService.notifyItemFound(createdItem.getId(), createdItem.getUsuarioRelatorId());
            }
            
            return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(value = "/achados", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Criar item achado com foto(s)", description = "Cria um novo item achado com uma ou mais fotos. Pelo menos uma foto é obrigatória. O usuário relator é obtido automaticamente do token JWT.")
    public ResponseEntity<ItemDTO> createItemAchado(
            @Parameter(description = "Dados do item (JSON)") @RequestPart("item") ItemCreateDTO itemCreateDTO,
            @Parameter(description = "Arquivo(s) de imagem do item (obrigatório - pode enviar múltiplas fotos)") @RequestPart("files") MultipartFile[] files,
            HttpServletRequest request) {
        try {
            // Validar se pelo menos uma foto foi enviada (obrigatória para itens achados)
            if (files == null || files.length == 0 || files[0] == null || files[0].isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
            }
            
            // Definir tipo como ACHADO
            itemCreateDTO.setTipoItem(Tipo_Item.ACHADO);
            
            // Extrair token JWT do header Authorization
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            
            String token = authHeader.substring(7);
            
            // Obter userId do token
            String userIdStr = jwtService.getUserIdFromToken(token);
            if (userIdStr == null || userIdStr.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            
            Integer usuarioRelatorId = Integer.valueOf(userIdStr);
            itemCreateDTO.setUsuarioRelatorId(usuarioRelatorId);
            
            // Criar item
            ItemDTO createdItem = itensService.createItem(itemCreateDTO);
            
            // Fazer upload das fotos e associar ao item (obrigatório)
            if (createdItem != null) {
                boolean atLeastOneUploaded = false;
                for (MultipartFile file : files) {
                    if (file != null && !file.isEmpty()) {
                        try {
                            fotosService.uploadItemPhoto(file, usuarioRelatorId, createdItem.getId());
                            atLeastOneUploaded = true;
                        } catch (Exception e) {
                            // Log erro mas continua tentando outras fotos
                        }
                    }
                }
                // Se nenhuma foto foi enviada com sucesso, retornar erro
                if (!atLeastOneUploaded) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                }
            }
            
            // Envia notificação automática quando item é criado
            if (createdItem != null) {
                notificationService.notifyItemFound(createdItem.getId(), createdItem.getUsuarioRelatorId());
            }
            
            return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
