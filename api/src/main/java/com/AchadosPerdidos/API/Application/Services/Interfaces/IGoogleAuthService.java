package com.AchadosPerdidos.API.Application.Services.Interfaces;

import com.AchadosPerdidos.API.Application.DTOs.Auth.GoogleUserDTO;

public interface IGoogleAuthService {

    String generateAuthorizationUrl();

    GoogleUserDTO exchangeCodeForUserInfo(String authorizationCode);

    String getRedirectUrl();
}