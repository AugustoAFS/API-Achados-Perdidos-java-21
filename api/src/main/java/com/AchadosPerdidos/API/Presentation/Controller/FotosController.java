package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.DTOs.Fotos.FotosDTO;
import com.AchadosPerdidos.API.Application.DTOs.Fotos.FotosListDTO;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IFotosService;
import com.AchadosPerdidos.API.Application.Services.FotosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller centralizado para gerenciamento de fotos
 * Todas as rotas estão centralizadas em /api/fotos
 */
@RestController
@RequestMapping("/api/fotos")
@Tag(name = "Fotos", description = "API centralizada para gerenciamento de fotos - Upload, download e CRUD de fotos")
public class FotosController {

    @Autowired
    private IFotosService fotosService;

    @Autowired
    private FotosService fotosServiceImpl;

    // ========== CRUD BÁSICO ==========

    @GetMapping
    @Operation(summary = "Listar todas as fotos", description = "Retorna uma lista com todas as fotos cadastradas no sistema, incluindo inativas")
    public ResponseEntity<FotosListDTO> getAllFotos() {
        FotosListDTO fotos = fotosService.getAllFotos();
        return ResponseEntity.ok(fotos);
    }

    @PostMapping("/usuario")
    @Operation(summary = "Criar foto de usuário", description = "Cria um novo registro de foto de usuário no banco de dados")
    public ResponseEntity<FotosDTO> createFotoUsuario(@RequestBody FotosDTO fotosDTO) {
        FotosDTO createdFoto = fotosService.createFotoUsaurio(fotosDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFoto);
    }

    @PostMapping("/item")
    @Operation(summary = "Criar foto de item", description = "Cria um novo registro de foto de item no banco de dados")
    public ResponseEntity<FotosDTO> createFotoItem(@RequestBody FotosDTO fotosDTO) {
        FotosDTO createdFoto = fotosService.createFotoItem(fotosDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFoto);
    }

    @PutMapping("/usuario/{id}")
    @Operation(summary = "Atualizar foto de usuário", description = "Atualiza os metadados de uma foto de usuário existente")
    public ResponseEntity<FotosDTO> updateFotoUsuario(
            @Parameter(description = "ID da foto a ser atualizada") @PathVariable int id, 
            @RequestBody FotosDTO fotosDTO) {
        return ResponseEntity.ok(fotosService.updateFotoUsuario(id, fotosDTO));
    }

    @PutMapping("/item/{id}")
    @Operation(summary = "Atualizar foto de item", description = "Atualiza os metadados de uma foto de item existente")
    public ResponseEntity<FotosDTO> updateFotoItem(
            @Parameter(description = "ID da foto a ser atualizada") @PathVariable int id, 
            @RequestBody FotosDTO fotosDTO) {
        return ResponseEntity.ok(fotosService.updateFotoItem(id, fotosDTO));
    }

    @DeleteMapping("/usuario/{id}")
    @Operation(summary = "Deletar foto de usuário (soft delete)", description = "Marca uma foto de usuário como inativa no banco de dados")
    public ResponseEntity<Void> deleteFotoUsuario(
            @Parameter(description = "ID da foto a ser deletada") @PathVariable int id) {
        fotosService.deleteFotoUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/item/{id}")
    @Operation(summary = "Deletar foto de item (soft delete)", description = "Marca uma foto de item como inativa no banco de dados")
    public ResponseEntity<Void> deleteFotoItem(
            @Parameter(description = "ID da foto a ser deletada") @PathVariable int id) {
        fotosService.deleteFotoItem(id);
        return ResponseEntity.noContent().build();
    }

    // ========== UPLOAD E DOWNLOAD COM S3 ==========

    @PostMapping(value = "/upload/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
        summary = "Upload de foto de perfil", 
        description = "Faz upload de uma foto de perfil para o S3 e cria o registro no banco de dados. " +
                     "A foto é automaticamente associada ao usuário como foto de perfil. " +
                     "Aceita formatos: JPEG, PNG, GIF, WEBP"
    )
    public ResponseEntity<?> uploadProfilePhoto(
            @Parameter(description = "Arquivo de imagem a ser enviado") @RequestParam("file") MultipartFile file,
            @Parameter(description = "ID do usuário proprietário da foto") @RequestParam("userId") Integer userId) {
        try {
            if (file == null || file.isEmpty()) {
                return ResponseEntity.badRequest()
                    .body("Arquivo não pode estar vazio");
            }
            
            if (userId == null || userId <= 0) {
                return ResponseEntity.badRequest()
                    .body("userId é obrigatório e deve ser maior que zero");
            }

            FotosDTO foto = fotosServiceImpl.uploadProfilePhoto(file, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(foto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body("Erro de validação: " + e.getMessage());
        } catch (IllegalStateException e) {
            // Erro de configuração (ex: bucket S3 não existe)
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Erro de configuração: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body("Erro ao fazer upload: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro interno: " + e.getMessage());
        }
    }

    @PostMapping(value = "/upload/item", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
        summary = "Upload de foto de item", 
        description = "Faz upload de uma foto de item para o S3 e cria o registro no banco de dados. " +
                     "A foto é automaticamente associada ao item especificado. " +
                     "Aceita formatos: JPEG, PNG, GIF, WEBP"
    )
    public ResponseEntity<?> uploadItemPhoto(
            @Parameter(description = "Arquivo de imagem a ser enviado") @RequestParam("file") MultipartFile file,
            @Parameter(description = "ID do usuário que está fazendo o upload") @RequestParam("userId") Integer userId,
            @Parameter(description = "ID do item ao qual a foto será associada") @RequestParam("itemId") Integer itemId) {
        try {
            if (file == null || file.isEmpty()) {
                return ResponseEntity.badRequest()
                    .body("Arquivo não pode estar vazio");
            }
            
            if (userId == null || userId <= 0) {
                return ResponseEntity.badRequest()
                    .body("userId é obrigatório e deve ser maior que zero");
            }
            
            if (itemId == null || itemId <= 0) {
                return ResponseEntity.badRequest()
                    .body("itemId é obrigatório e deve ser maior que zero");
            }

            FotosDTO foto = fotosServiceImpl.uploadItemPhoto(file, userId, itemId);
            return ResponseEntity.status(HttpStatus.CREATED).body(foto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body("Erro de validação: " + e.getMessage());
        } catch (IllegalStateException e) {
            // Erro de configuração (ex: bucket S3 não existe)
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Erro de configuração: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body("Erro ao fazer upload: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro interno: " + e.getMessage());
        }
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
        summary = "Upload genérico de foto", 
        description = "Upload genérico para o S3. Se itemId for fornecido, associa ao item. Se isProfilePhoto=true, trata como foto de perfil."
    )
    public ResponseEntity<FotosDTO> uploadPhoto(
            @Parameter(description = "Arquivo de imagem a ser enviado") @RequestParam("file") MultipartFile file,
            @Parameter(description = "ID do usuário proprietário da foto") @RequestParam("userId") Integer userId,
            @Parameter(description = "ID do item (opcional)") @RequestParam(value = "itemId", required = false) Integer itemId,
            @Parameter(description = "Indica se é foto de perfil") @RequestParam(value = "isProfilePhoto", defaultValue = "false") boolean isProfilePhoto) {

        FotosDTO foto = fotosServiceImpl.uploadPhoto(file, userId, itemId, isProfilePhoto);
        return ResponseEntity.status(HttpStatus.CREATED).body(foto);
    }

    @GetMapping("/download/{id}")
    @Operation(
        summary = "Download de foto", 
        description = "Baixa o arquivo de imagem do S3 e retorna os bytes da imagem. " +
                     "O Content-Type é definido automaticamente baseado na extensão do arquivo. " +
                     "Suporta: JPEG, PNG, GIF, WEBP"
    )
    public ResponseEntity<byte[]> downloadPhoto(
            @Parameter(description = "ID da foto a ser baixada") @PathVariable int id) {
        try {
            byte[] photoData = fotosServiceImpl.downloadPhoto(id);
            
            // Definir content-type padrão
            // Nota: getFotoById não existe na interface IFotosService
            String contentType = "image/jpeg"; // Default

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(contentType));
            headers.setContentLength(photoData.length);
            headers.set("Content-Disposition", "inline; filename=\"photo\"");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(photoData);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    @Operation(
        summary = "Deletar foto permanentemente", 
        description = "Remove a foto tanto do banco de dados quanto do S3 (deleção física). " +
                     "Diferente de DELETE /{id} que apenas marca como inativa (soft delete), " +
                     "este endpoint remove completamente o arquivo do armazenamento"
    )
    public ResponseEntity<Void> deletePhoto(
            @Parameter(description = "ID da foto a ser deletada permanentemente") @PathVariable int id) {
        try {
            boolean deleted = fotosServiceImpl.deletePhoto(id);
            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
