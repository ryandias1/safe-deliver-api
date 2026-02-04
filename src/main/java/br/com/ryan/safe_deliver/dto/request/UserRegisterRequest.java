package br.com.ryan.safe_deliver.dto.request;

import br.com.ryan.safe_deliver.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserRegisterRequest(
        @NotBlank(message = "Campo name em branco") String name,
        @NotBlank(message = "Campo email em branco") String email,
        @NotBlank(message = "Campo password em branco") String password,
        @NotNull(message = "Campo role em branco") Role role,
        UUID hospital_id
) {
}
