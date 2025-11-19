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

    @GetMapping("/{id}")
    @Operation(summary = "Buscar foto por ID", description = "Retorna os detalhes de uma foto específica pelo seu ID")
    public ResponseEntity<FotosDTO> getFotoById(
            @Parameter(description = "ID da foto a ser buscada") @PathVariable int id) {
        FotosDTO foto = fotosService.getFotoById(id);
        if (foto != null) {
            return ResponseEntity.ok(foto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Criar nova foto", description = "Cria um novo registro de foto no banco de dados. Para upload de arquivo, use os endpoints /upload")
    public ResponseEntity<FotosDTO> createFoto(@RequestBody FotosDTO fotosDTO) {
        FotosDTO createdFoto = fotosService.createFoto(fotosDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFoto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar foto", description = "Atualiza os metadados de uma foto existente (URL, nome do arquivo, etc.)")
    public ResponseEntity<FotosDTO> updateFoto(
            @Parameter(description = "ID da foto a ser atualizada") @PathVariable int id, 
            @RequestBody FotosDTO fotosDTO) {
        FotosDTO updatedFoto = fotosService.updateFoto(id, fotosDTO);
        if (updatedFoto != null) {
            return ResponseEntity.ok(updatedFoto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar foto (soft delete)", description = "Marca uma foto como inativa no banco de dados. Não remove o arquivo do S3")
    public ResponseEntity<Void> deleteFoto(
            @Parameter(description = "ID da foto a ser deletada") @PathVariable int id) {
        boolean deleted = fotosService.deleteFoto(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ========== BUSCAS E FILTROS ==========

    @GetMapping("/active")
    @Operation(summary = "Listar fotos ativas", description = "Retorna apenas as fotos que estão ativas (não foram deletadas)")
    public ResponseEntity<FotosListDTO> getActiveFotos() {
        FotosListDTO activeFotos = fotosService.getActiveFotos();
        return ResponseEntity.ok(activeFotos);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Buscar fotos por usuário", description = "Retorna todas as fotos associadas a um usuário específico (perfil e itens)")
    public ResponseEntity<FotosListDTO> getFotosByUser(
            @Parameter(description = "ID do usuário") @PathVariable int userId) {
        FotosListDTO fotos = fotosService.getFotosByUser(userId);
        return ResponseEntity.ok(fotos);
    }

    @GetMapping("/item/{itemId}")
    @Operation(summary = "Buscar fotos por item", description = "Retorna todas as fotos associadas a um item específico")
    public ResponseEntity<FotosListDTO> getFotosByItem(
            @Parameter(description = "ID do item") @PathVariable int itemId) {
        FotosListDTO fotos = fotosService.getFotosByItem(itemId);
        return ResponseEntity.ok(fotos);
    }

    @GetMapping("/profile/{userId}")
    @Operation(summary = "Buscar fotos de perfil do usuário", description = "Retorna todas as fotos de perfil de um usuário específico")
    public ResponseEntity<FotosListDTO> getProfilePhotos(
            @Parameter(description = "ID do usuário") @PathVariable int userId) {
        FotosListDTO fotos = fotosService.getProfilePhotos(userId);
        return ResponseEntity.ok(fotos);
    }

    @GetMapping("/item-photos/{itemId}")
    @Operation(summary = "Buscar fotos de item (alternativo)", description = "Método alternativo para buscar fotos de um item. Funcionalidade similar a /item/{itemId}")
    public ResponseEntity<FotosListDTO> getItemPhotos(
            @Parameter(description = "ID do item") @PathVariable int itemId) {
        FotosListDTO fotos = fotosService.getItemPhotos(itemId);
        return ResponseEntity.ok(fotos);
    }

    @GetMapping("/main-item-photo/{itemId}")
    @Operation(summary = "Buscar foto principal do item", description = "Retorna a foto principal (primeira foto) de um item específico")
    public ResponseEntity<FotosDTO> getMainItemPhoto(
            @Parameter(description = "ID do item") @PathVariable int itemId) {
        FotosDTO foto = fotosService.getMainItemPhoto(itemId);
        if (foto != null) {
            return ResponseEntity.ok(foto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/profile-photo/{userId}")
    @Operation(summary = "Buscar foto de perfil principal", description = "Retorna a foto de perfil principal (ativa) de um usuário específico")
    public ResponseEntity<FotosDTO> getProfilePhoto(
            @Parameter(description = "ID do usuário") @PathVariable int userId) {
        FotosDTO foto = fotosService.getProfilePhoto(userId);
        if (foto != null) {
            return ResponseEntity.ok(foto);
        } else {
            return ResponseEntity.notFound().build();
        }
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
        description = "Faz upload genérico de uma foto para o S3. " +
                     "Pode ser usado para foto de perfil ou de item, dependendo dos parâmetros fornecidos. " +
                     "Se itemId for fornecido, a foto será associada ao item. " +
                     "Se isProfilePhoto for true, será tratada como foto de perfil. " +
                     "Aceita formatos: JPEG, PNG, GIF, WEBP"
    )
    public ResponseEntity<?> uploadPhoto(
            @Parameter(description = "Arquivo de imagem a ser enviado") @RequestParam("file") MultipartFile file,
            @Parameter(description = "ID do usuário proprietário da foto") @RequestParam("userId") Integer userId,
            @Parameter(description = "ID do item (opcional, se fornecido associa a foto ao item)") @RequestParam(value = "itemId", required = false) Integer itemId,
            @Parameter(description = "Indica se é foto de perfil (padrão: false)") @RequestParam(value = "isProfilePhoto", defaultValue = "false") boolean isProfilePhoto) {
        try {
            // Validações básicas
            if (file == null || file.isEmpty()) {
                return ResponseEntity.badRequest()
                    .body("Arquivo não pode estar vazio");
            }
            
            if (userId == null || userId <= 0) {
                return ResponseEntity.badRequest()
                    .body("userId é obrigatório e deve ser maior que zero");
            }

            FotosDTO foto = fotosServiceImpl.uploadPhoto(file, userId, itemId, isProfilePhoto);
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
            
            // Buscar informações da foto para definir o content-type
            FotosDTO foto = fotosService.getFotoById(id);
            String contentType = "image/jpeg"; // Default
            String fileName = foto != null ? foto.getNomeArquivoOriginal() : null;
            if (fileName != null) {
                String lower = fileName.toLowerCase();
                if (lower.endsWith(".png")) {
                    contentType = "image/png";
                } else if (lower.endsWith(".gif")) {
                    contentType = "image/gif";
                } else if (lower.endsWith(".webp")) {
                    contentType = "image/webp";
                }
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(contentType));
            headers.setContentLength(photoData.length);
            headers.set("Content-Disposition", "inline; filename=\"" + (fileName != null ? fileName : "photo") + "\"");

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
