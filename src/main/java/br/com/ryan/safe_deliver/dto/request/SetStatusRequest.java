package br.com.ryan.safe_deliver.dto.request;

import br.com.ryan.safe_deliver.enums.Status;
import jakarta.validation.constraints.NotNull;

public record SetStatusRequest(
        @NotNull(message = "Campo status em branco") Status status
) {
}
