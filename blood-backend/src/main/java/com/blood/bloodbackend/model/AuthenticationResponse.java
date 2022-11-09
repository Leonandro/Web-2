package com.blood.bloodbackend.model;

import com.blood.bloodbackend.model.DTO.UserLoginResponseDTO;

public class AuthenticationResponse {
    UserLoginResponseDTO response;

    public AuthenticationResponse(UserLoginResponseDTO response) {
        this.response = response;
    }

    public UserLoginResponseDTO getResponse() {
        return response;
    }

    public void setResponse(UserLoginResponseDTO response) {
        this.response = response;
    }
}
