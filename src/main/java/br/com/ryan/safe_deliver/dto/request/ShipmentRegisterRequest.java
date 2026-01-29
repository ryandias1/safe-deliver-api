package br.com.ryan.safe_deliver.dto.request;

import br.com.ryan.safe_deliver.enums.ShipmentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ShipmentRegisterRequest(
        @NotBlank(message = "Campo descrição não pode estar vazio") String description,
        @NotNull ShipmentType type,
        @NotNull UUID hospitalDestinationId
) {
}
