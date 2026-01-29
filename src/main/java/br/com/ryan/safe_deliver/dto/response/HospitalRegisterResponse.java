package br.com.ryan.safe_deliver.dto.response;

import java.util.UUID;

public record HospitalRegisterResponse(
        UUID id,
        String name,
        String CNPJ
) {
}
