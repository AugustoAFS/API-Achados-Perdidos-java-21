package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.DTOs.Item.ItemDTO;
import com.AchadosPerdidos.API.Application.DTOs.Item.ItemListDTO;
import com.AchadosPerdidos.API.Application.DTOs.Item.ItemCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Item.ItemUpdateDTO;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IItensService;
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


    @GetMapping
    @Operation(summary = "Listar todos os itens")
    public ResponseEntity<ItemListDTO> getAllItens() {
        return ResponseEntity.ok(itensService.getAllItens());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar item por ID")
    public ResponseEntity<ItemDTO> getItemById(@PathVariable int id) {
        return ResponseEntity.ok(itensService.getItemById(id));
    }

    @GetMapping("/campus/{campusId}")
    @Operation(summary = "Buscar itens por campus", description = "Retorna todos os itens de um campus específico")
    public ResponseEntity<ItemListDTO> getItensByCampus(
            @Parameter(description = "ID do campus") @PathVariable int campusId) {
        return ResponseEntity.ok(itensService.getItensByCampus(campusId));
    }

    @PostMapping(value = "/achados", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
        summary = "Criar item achado com foto(s)",
        description = "Cria um novo item achado com uma ou mais fotos. O usuário relator é obtido automaticamente do token JWT."
    )
    public ResponseEntity<ItemDTO> createItemAchado(
            @Parameter(description = "Dados do item (JSON)") @RequestPart("item") ItemCreateDTO itemCreateDTO,
            @Parameter(description = "Arquivo(s) de imagem do item (obrigatório)") @RequestPart("files") MultipartFile[] files,
            HttpServletRequest request) {

        String token = extractTokenFromRequest(request);
        ItemDTO createdItem = itensService.createItemAchadoComFotos(itemCreateDTO, files, token);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
    }

    @PostMapping(value = "/perdidos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
        summary = "Criar item perdido com foto(s)",
        description = "Cria um novo item perdido com uma ou mais fotos (opcional). O usuário relator é obtido automaticamente do token JWT."
    )
    public ResponseEntity<ItemDTO> createItemPerdido(
            @Parameter(description = "Dados do item (JSON)") @RequestPart("item") ItemCreateDTO itemCreateDTO,
            @Parameter(description = "Arquivo(s) de imagem do item (opcional)") @RequestPart(value = "files", required = false) MultipartFile[] files,
            HttpServletRequest request) {

        String token = extractTokenFromRequest(request);
        ItemDTO createdItem = itensService.createItemPerdidoComFotos(itemCreateDTO, files, token);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Atualizar item")
    public ResponseEntity<ItemDTO> updateItem(
            @PathVariable int id,
            @RequestBody ItemUpdateDTO itemUpdateDTO) {
        return ResponseEntity.ok(itensService.updateItem(id, itemUpdateDTO));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Deletar item (soft delete)")
    public ResponseEntity<Void> deleteItem(@PathVariable int id) {
        itensService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

}
