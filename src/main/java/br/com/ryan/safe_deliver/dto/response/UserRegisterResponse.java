package br.com.ryan.safe_deliver.dto.response;

import br.com.ryan.safe_deliver.enums.Role;

import java.util.UUID;

public record UserRegisterResponse(
        UUID id,
        String name,
        String email,
        String password,
        Role role,
        UUID hospitalId
) {
}
