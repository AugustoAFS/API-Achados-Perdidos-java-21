package com.AchadosPerdidos.API.Application.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class S3Service {

    @Autowired
    private S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    /**
     * Faz upload de um arquivo para o S3
     * @param fileContent Conteúdo do arquivo em bytes
     * @param fileName Nome original do arquivo
     * @param contentType Tipo MIME do arquivo
     * @param folder Pasta no S3 (opcional)
     * @return URL do arquivo no S3
     */
    public String uploadFile(byte[] fileContent, String fileName, String contentType, String folder) {
        try {
            // Gerar chave única para o arquivo
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            String uniqueId = UUID.randomUUID().toString();
            String fileExtension = getFileExtension(fileName);
            String s3Key = folder != null ? 
                folder + "/" + timestamp + "/" + uniqueId + fileExtension :
                timestamp + "/" + uniqueId + fileExtension;

            // Configurar metadados
            Map<String, String> metadata = new HashMap<>();
            metadata.put("original-name", fileName);
            metadata.put("upload-date", LocalDateTime.now().toString());
            metadata.put("content-type", contentType);

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3Key)
                    .contentType(contentType)
                    .contentLength((long) fileContent.length)
                    .metadata(metadata)
                    .build();

            // Fazer upload
            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(
                    new ByteArrayInputStream(fileContent), fileContent.length));

            // Retornar URL do arquivo
            return generateFileUrl(s3Key);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao fazer upload do arquivo para S3: " + e.getMessage(), e);
        }
    }

    /**
     * Faz upload de uma foto específica
     * @param fileContent Conteúdo do arquivo em bytes
     * @param fileName Nome original do arquivo
     * @param contentType Tipo MIME do arquivo
     * @param userId ID do usuário
     * @param itemId ID do item (opcional)
     * @param isProfilePhoto Se é foto de perfil
     * @return Objeto com informações do upload
     */
    public S3UploadResult uploadPhoto(byte[] fileContent, String fileName, String contentType, 
                                    Integer userId, Integer itemId, boolean isProfilePhoto) {
        try {
            String folder = isProfilePhoto ? "profile-photos" : "item-photos";
            String s3Key = uploadFile(fileContent, fileName, contentType, folder);
            
            return new S3UploadResult(
                s3Key,
                generateFileUrl(s3Key),
                fileName,
                contentType,
                (long) fileContent.length,
                userId,
                itemId,
                isProfilePhoto
            );
        } catch (Exception e) {
            throw new RuntimeException("Erro ao fazer upload da foto: " + e.getMessage(), e);
        }
    }

    /**
     * Baixa um arquivo do S3
     * @param s3Key Chave do arquivo no S3
     * @return Conteúdo do arquivo
     */
    public byte[] downloadFile(String s3Key) {
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3Key)
                    .build();

            return s3Client.getObject(getObjectRequest).readAllBytes();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao baixar arquivo do S3: " + e.getMessage(), e);
        }
    }

    /**
     * Verifica se um arquivo existe no S3
     * @param s3Key Chave do arquivo no S3
     * @return true se o arquivo existe
     */
    public boolean fileExists(String s3Key) {
        try {
            HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3Key)
                    .build();

            s3Client.headObject(headObjectRequest);
            return true;
        } catch (NoSuchKeyException e) {
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao verificar existência do arquivo: " + e.getMessage(), e);
        }
    }

    /**
     * Deleta um arquivo do S3
     * @param s3Key Chave do arquivo no S3
     * @return true se o arquivo foi deletado com sucesso
     */
    public boolean deleteFile(String s3Key) {
        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3Key)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar arquivo do S3: " + e.getMessage(), e);
        }
    }

    /**
     * Gera URL pública do arquivo
     * @param s3Key Chave do arquivo no S3
     * @return URL pública
     */
    private String generateFileUrl(String s3Key) {
        return String.format("https://%s.s3.amazonaws.com/%s", bucketName, s3Key);
    }

    /**
     * Extrai extensão do arquivo
     * @param fileName Nome do arquivo
     * @return Extensão do arquivo
     */
    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        return lastDotIndex > 0 ? fileName.substring(lastDotIndex) : "";
    }

    /**
     * Classe para retornar resultado do upload
     */
    public static class S3UploadResult {
        private final String s3Key;
        private final String fileUrl;
        private final String originalName;
        private final String contentType;
        private final Long fileSize;
        private final Integer userId;
        private final Integer itemId;
        private final boolean isProfilePhoto;

        public S3UploadResult(String s3Key, String fileUrl, String originalName, String contentType, 
                             Long fileSize, Integer userId, Integer itemId, boolean isProfilePhoto) {
            this.s3Key = s3Key;
            this.fileUrl = fileUrl;
            this.originalName = originalName;
            this.contentType = contentType;
            this.fileSize = fileSize;
            this.userId = userId;
            this.itemId = itemId;
            this.isProfilePhoto = isProfilePhoto;
        }

        // Getters
        public String getS3Key() { return s3Key; }
        public String getFileUrl() { return fileUrl; }
        public String getOriginalName() { return originalName; }
        public String getContentType() { return contentType; }
        public Long getFileSize() { return fileSize; }
        public Integer getUserId() { return userId; }
        public Integer getItemId() { return itemId; }
        public boolean isProfilePhoto() { return isProfilePhoto; }
    }
}
