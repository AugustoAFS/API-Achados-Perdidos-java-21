package com.AchadosPerdidos.API.Application.DTOs.Auth;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para dados do usuario do Google")

public class GoogleUserDTO{
    @Schema(description = "ID do usuario no Google", example = "123456789")
    private String id;

    @Schema(description = "Email do usuario", example = "joao@gmail.com")
    private String email;

    @Schema(description = "Nome do usuario", example = "Joao Silva")
    private String name;

    @Schema(description = "URL da foto de perfil", example = "https://lh3.googleusercontent.com/...")
    private String picture;

    @Schema(description = "Se o email foi verificado pelo Google", example = "true")
    private boolean verified_email;

    public GoogleUserDTO() {
        // Construtor padrao para frameworks de serializacao
    }

    public GoogleUserDTO(String id, String email, String name, String picture, boolean verified_email) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.picture = picture;
        this.verified_email = verified_email;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
    
    public String getPicture() {
        return picture;
    }

    public boolean getVerified_email() {
        return verified_email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setVerified_email(boolean verified_email) {
        this.verified_email = verified_email;
    }
}