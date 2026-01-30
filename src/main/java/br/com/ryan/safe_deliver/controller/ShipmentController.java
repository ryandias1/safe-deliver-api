package br.com.ryan.safe_deliver.controller;

import br.com.ryan.safe_deliver.dto.request.SetDriverRequest;
import br.com.ryan.safe_deliver.dto.request.SetStatusRequest;
import br.com.ryan.safe_deliver.dto.request.ShipmentRegisterRequest;
import br.com.ryan.safe_deliver.dto.response.ShipmentRegisterResponse;
import br.com.ryan.safe_deliver.service.ShipmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.access.AccessDeniedException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/shipment")
public class ShipmentController {
    @Autowired
    private ShipmentService shipmentService;

    @PreAuthorize("hasRole('HOSPITAL_MANAGER')")
    @PostMapping
    public ResponseEntity<ShipmentRegisterResponse> registerShipment(@Valid @RequestBody ShipmentRegisterRequest shipmentRegisterRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(shipmentService.registerShipment(shipmentRegisterRequest));
    }

    @PreAuthorize("hasRole('HOSPITAL_MANAGER')")
    @PatchMapping("/{id}/assign")
    public ResponseEntity<ShipmentRegisterResponse> setDriver(@PathVariable UUID id, @Valid @RequestBody SetDriverRequest driverRequest) throws AccessDeniedException {
        return ResponseEntity.status(HttpStatus.OK).body(shipmentService.setDriver(driverRequest, id));
    }

    @PreAuthorize("hasRole('DRIVER')")
    @PatchMapping("/{id}/status")
    public ResponseEntity<ShipmentRegisterResponse> setStatus(@PathVariable UUID id, @Valid @RequestBody SetStatusRequest setStatusRequest) throws AccessDeniedException {
        return ResponseEntity.status(HttpStatus.OK).body(shipmentService.setStatus(setStatusRequest, id));
    }

    @PreAuthorize("hasAnyRole('HOSPITAL_MANAGER', 'DRIVER')")
    @GetMapping
    public ResponseEntity<List<ShipmentRegisterResponse>> getAllShipments() {
        return ResponseEntity.status(HttpStatus.OK).body(shipmentService.getAllShipments());
    }
}
