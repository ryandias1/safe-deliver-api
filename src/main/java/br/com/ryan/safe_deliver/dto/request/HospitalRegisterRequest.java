package br.com.ryan.safe_deliver.dto.request;

import jakarta.validation.constraints.NotBlank;

public record HospitalRegisterRequest(
        @NotBlank(message = "Campo nome não pode ser em branco") String name,
        @NotBlank(message = "Campo CNPJ não pode ser em branco") String CNPJ
) {
}
