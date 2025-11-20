package com.AchadosPerdidos.API.Application.Config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Getter
@Configuration
public class S3Config {

    @Value("${AWS_S3_ACCESS_KEY:default-access-key}")
    private String accessKey;

    @Value("${AWS_S3_SECRET_KEY:default-secret-key}")
    private String secretKey;

    @Value("${AWS_S3_BUCKET:}")
    private String bucketName;

    @Value("${AWS_S3_REGION:us-east-1}")
    private String region;

    @Value("${AWS_S3_ENDPOINT_URL:}")
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

    @Bean
    public String s3BucketName() {
        return bucketName;
    }

    @Bean
    public S3Presigner s3Presigner() {
        // Criar credenciais AWS com Access Key e Secret Key
        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(accessKey, secretKey);
        
        // Configurar o presigner S3 com suporte a endpoint customizado
        var builder = S3Presigner.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .region(Region.of(region));
        
        // Se endpoint customizado for fornecido, usar ele (útil para MinIO, LocalStack, etc.)
        if (endpointUrl != null && !endpointUrl.trim().isEmpty()) {
            builder.endpointOverride(java.net.URI.create(endpointUrl));
        }
        
        return builder.build();
    }
} 