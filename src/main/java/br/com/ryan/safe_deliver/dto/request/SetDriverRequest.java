package br.com.ryan.safe_deliver.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SetDriverRequest(
        @NotNull(message = "Campo driverId em branco") UUID driverID
) {
}
