package br.com.ryan.safe_deliver.dto.response;

import br.com.ryan.safe_deliver.enums.ShipmentType;
import br.com.ryan.safe_deliver.enums.Status;

import java.util.UUID;

public record ShipmentRegisterResponse(
        UUID id,
        String descption,
        ShipmentType type,
        Status status,
        HospitalRegisterResponse hospitalOrigin,
        HospitalRegisterResponse hospitalDestination,
        UserRegisterResponse motorista
) {
}
