package com.AchadosPerdidos.API.Presentation.Controller;

import com.AchadosPerdidos.API.Application.DTOs.Fotos.FotosDTO;
import com.AchadosPerdidos.API.Application.DTOs.Fotos.FotosListDTO;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IFotosService;
import com.AchadosPerdidos.API.Application.Services.FotosService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/fotos")
@CrossOrigin(origins = "*")
@Tag(name = "Fotos", description = "API para gerenciamento de fotos")
public class FotosController {

    @Autowired
    private IFotosService fotosService;

    @Autowired
    private FotosService fotosServiceImpl;

    @GetMapping
    public ResponseEntity<FotosListDTO> getAllFotos() {
        FotosListDTO fotos = fotosService.getAllFotos();
        return ResponseEntity.ok(fotos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FotosDTO> getFotoById(@PathVariable int id) {
        FotosDTO foto = fotosService.getFotoById(id);
        if (foto != null) {
            return ResponseEntity.ok(foto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<FotosDTO> createFoto(@RequestBody FotosDTO fotosDTO) {
        FotosDTO createdFoto = fotosService.createFoto(fotosDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFoto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FotosDTO> updateFoto(@PathVariable int id, @RequestBody FotosDTO fotosDTO) {
        FotosDTO updatedFoto = fotosService.updateFoto(id, fotosDTO);
        if (updatedFoto != null) {
            return ResponseEntity.ok(updatedFoto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFoto(@PathVariable int id) {
        boolean deleted = fotosService.deleteFoto(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/active")
    public ResponseEntity<FotosListDTO> getActiveFotos() {
        FotosListDTO activeFotos = fotosService.getActiveFotos();
        return ResponseEntity.ok(activeFotos);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<FotosListDTO> getFotosByUser(@PathVariable int userId) {
        FotosListDTO fotos = fotosService.getFotosByUser(userId);
        return ResponseEntity.ok(fotos);
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<FotosListDTO> getFotosByItem(@PathVariable int itemId) {
        FotosListDTO fotos = fotosService.getFotosByItem(itemId);
        return ResponseEntity.ok(fotos);
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<FotosListDTO> getProfilePhotos(@PathVariable int userId) {
        FotosListDTO fotos = fotosService.getProfilePhotos(userId);
        return ResponseEntity.ok(fotos);
    }

    @GetMapping("/item-photos/{itemId}")
    public ResponseEntity<FotosListDTO> getItemPhotos(@PathVariable int itemId) {
        FotosListDTO fotos = fotosService.getItemPhotos(itemId);
        return ResponseEntity.ok(fotos);
    }

    @GetMapping("/main-item-photo/{itemId}")
    public ResponseEntity<FotosDTO> getMainItemPhoto(@PathVariable int itemId) {
        FotosDTO foto = fotosService.getMainItemPhoto(itemId);
        if (foto != null) {
            return ResponseEntity.ok(foto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/profile-photo/{userId}")
    public ResponseEntity<FotosDTO> getProfilePhoto(@PathVariable int userId) {
        FotosDTO foto = fotosService.getProfilePhoto(userId);
        if (foto != null) {
            return ResponseEntity.ok(foto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ========== ENDPOINTS PARA UPLOAD E DOWNLOAD COM S3 ==========

    /**
     * Upload de foto de perfil
     */
    @PostMapping(value = "/upload/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FotosDTO> uploadProfilePhoto(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Integer userId) {
        try {
            FotosDTO foto = fotosServiceImpl.uploadProfilePhoto(file, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(foto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Upload de foto de item
     */
    @PostMapping(value = "/upload/item", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FotosDTO> uploadItemPhoto(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Integer userId,
            @RequestParam("itemId") Integer itemId) {
        try {
            FotosDTO foto = fotosServiceImpl.uploadItemPhoto(file, userId, itemId);
            return ResponseEntity.status(HttpStatus.CREATED).body(foto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Download de foto (retorna o arquivo)
     */
    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadPhoto(@PathVariable int id) {
        try {
            byte[] photoData = fotosServiceImpl.downloadPhoto(id);
            
            // Buscar informações da foto para definir o content-type
            FotosDTO foto = fotosService.getFotoById(id);
            String contentType = "image/jpeg"; // Default
            if (foto != null && foto.getNomeArquivoOriginal() != null) {
                String fileName = foto.getNomeArquivoOriginal().toLowerCase();
                if (fileName.endsWith(".png")) {
                    contentType = "image/png";
                } else if (fileName.endsWith(".gif")) {
                    contentType = "image/gif";
                } else if (fileName.endsWith(".webp")) {
                    contentType = "image/webp";
                }
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(contentType));
            headers.setContentLength(photoData.length);
            headers.set("Content-Disposition", "inline; filename=\"" + (foto != null ? foto.getNomeArquivoOriginal() : "photo") + "\"");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(photoData);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletar foto (remove do S3 e do banco)
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePhoto(@PathVariable int id) {
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

    /**
     * Upload genérico de foto
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FotosDTO> uploadPhoto(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Integer userId,
            @RequestParam(value = "itemId", required = false) Integer itemId,
            @RequestParam(value = "isProfilePhoto", defaultValue = "false") boolean isProfilePhoto) {
        try {
            FotosDTO foto = fotosServiceImpl.uploadPhoto(file, userId, itemId, isProfilePhoto);
            return ResponseEntity.status(HttpStatus.CREATED).body(foto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
