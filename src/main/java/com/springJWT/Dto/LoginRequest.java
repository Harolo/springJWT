package com.springJWT.Dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
