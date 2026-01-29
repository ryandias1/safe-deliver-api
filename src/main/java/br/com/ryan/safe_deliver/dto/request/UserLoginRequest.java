package br.com.ryan.safe_deliver.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserLoginRequest(
        @NotBlank(message = "Campo email em branco")
        String email,

        @NotBlank(message = "Campo senha em branco")
        String password
) {
}
