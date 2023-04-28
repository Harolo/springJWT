package com.springJWT.Dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class SignupRequest {

    @NotBlank
    @Size(max = 10,message = "usuario mayor a 10 caracteres")
    @Size(min = 5, message = "usuario menor a 3 caracteres")
    private String username;

    @NotBlank
    @Size(max = 8,message = "contraseña no debe ser mayo a caracteres")
    @Size(min = 7,message = "contrseña no debe ser menor a 8 caracteres")
    private String password;

    @NotBlank
    @Email(message = "digite un email valido")
    @Size(max = 50,message = "email excede los 50 caracteres")
    private String email;

    private Set<String> role;
}
