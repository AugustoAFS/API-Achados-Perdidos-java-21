package com.AchadosPerdidos.API.Application.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;


/**
 * Configuração do AWS S3 para upload e download de arquivos
 * Esta classe configura o cliente S3 com as credenciais AWS
 * Funciona tanto localmente quanto em produção
 */
@Configuration
public class S3Config {
    
    // Chave de acesso AWS (Access Key ID) - com valor padrão para desenvolvimento
    @Value("${aws.s3.access-key:default-access-key}")
    private String accessKey;

    // Chave secreta AWS (Secret Access Key) - com valor padrão para desenvolvimento
    @Value("${aws.s3.secret-key:default-secret-key}")
    private String secretKey;

    // Região AWS onde está o bucket (ex: us-east-1, sa-east-1) - com valor padrão
    @Value("${aws.s3.region:us-east-1}")
    private String region;

    // URL do endpoint S3 (opcional - usado para MinIO ou outros serviços S3-compatíveis)
    @Value("${aws.s3.endpoint-url:}")
    private String endpointUrl;

    @Bean
    @Primary
    public S3Client s3Client() {
        // Criar credenciais AWS com Access Key e Secret Key
        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(accessKey, secretKey);
        
        // Configurar o cliente S3 com suporte a endpoint customizado
        var builder = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .region(Region.of(region));
        
        // Se endpoint customizado for fornecido, usar ele (útil para MinIO, LocalStack, etc.)
        if (endpointUrl != null && !endpointUrl.trim().isEmpty()) {
            builder.endpointOverride(java.net.URI.create(endpointUrl));
        }
        
        return builder.build();
    }
} 