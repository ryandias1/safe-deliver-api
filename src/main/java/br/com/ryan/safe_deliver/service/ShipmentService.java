package br.com.ryan.safe_deliver.service;

import br.com.ryan.safe_deliver.dto.request.SetDriverRequest;
import br.com.ryan.safe_deliver.dto.request.SetStatusRequest;
import br.com.ryan.safe_deliver.dto.request.ShipmentRegisterRequest;
import br.com.ryan.safe_deliver.dto.response.HospitalRegisterResponse;
import br.com.ryan.safe_deliver.dto.response.ShipmentRegisterResponse;
import br.com.ryan.safe_deliver.dto.response.UserRegisterResponse;
import br.com.ryan.safe_deliver.entity.AuditLog;
import br.com.ryan.safe_deliver.entity.Hospital;
import br.com.ryan.safe_deliver.entity.Shipment;
import br.com.ryan.safe_deliver.entity.User;
import br.com.ryan.safe_deliver.enums.Role;
import br.com.ryan.safe_deliver.enums.Status;
import br.com.ryan.safe_deliver.repository.AuditLogRepository;
import br.com.ryan.safe_deliver.repository.HospitalRepository;
import br.com.ryan.safe_deliver.repository.ShipmentRepository;
import br.com.ryan.safe_deliver.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ShipmentService {
    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuditLogRepository auditLogRepository;

    public ShipmentRegisterResponse registerShipment(ShipmentRegisterRequest shipmentRegisterRequest) {
        User user = getLoggedUser();
        Hospital hospitalDest = hospitalRepository.findById(shipmentRegisterRequest.hospitalDestinationId()).orElseThrow(()-> new EntityNotFoundException("Hospital de Destino não encontrado"));
        Shipment shipment = new Shipment();
        shipment.setDescption(shipmentRegisterRequest.description());
        shipment.setType(shipmentRegisterRequest.type());
        shipment.setStatus(Status.PENDENTE);
        shipment.setHospitalOrigin(user.getHospital());
        shipment.setHospitalDestination(hospitalDest);
        Shipment shipmentSaved = shipmentRepository.save(shipment);
        return toResponse(shipmentSaved, null);
    }

    public ShipmentRegisterResponse setDriver(SetDriverRequest setDriverRequest, UUID shipmentId) {
        User user = getLoggedUser();
        Shipment ship = shipmentRepository.findById(shipmentId).orElseThrow(() -> new EntityNotFoundException("Shipment não encontrado"));
        Hospital hospital = hospitalRepository.findById(ship.getHospitalOrigin().getId()).orElseThrow(() -> new EntityNotFoundException("Hospital não encontrado"));
        UUID hospitalId = hospital.getId();
        if (!(user.getHospital().getId().equals(hospitalId))) {
            throw new AccessDeniedException("Manager não pertence a esse hospital");
        }
        User motorista = userRepository.findById(setDriverRequest.driverID()).orElseThrow(() -> new EntityNotFoundException("Motorista não encontrado"));
        if (!motorista.getHospital().getId().equals(hospitalId)) {
            throw new IllegalArgumentException("Motorista não pertence a esse hospital");
        }
        ship.setMotorista(motorista);
        shipmentRepository.save(ship);
        return toResponse(ship, motorista);
    }

    @Transactional
    public ShipmentRegisterResponse setStatus(SetStatusRequest setStatusRequest, UUID shipmentId) throws AccessDeniedException {
        User user = getLoggedUser();
        Shipment shipment = shipmentRepository.findById(shipmentId).orElseThrow(() -> new EntityNotFoundException("Carga não encontrada"));
        Status oldValue = shipment.getStatus();
        Status newValue = setStatusRequest.status();
        if (!(shipment.getMotorista().getId().equals(user.getId()))) {
            throw new AccessDeniedException("Essa carga nao pertence a você");
        }
        validateStatus(oldValue, newValue);
        shipment.setStatus(newValue);
        shipmentRepository.save(shipment);
        auditLogRepository.save(createAuditLog(shipment, oldValue));
        return toResponse(shipment, shipment.getMotorista());
    }

    public List<ShipmentRegisterResponse> getAllShipments() {
        User user = getLoggedUser();
        List<ShipmentRegisterResponse> shipments = null;
        if (user.getRole() == Role.DRIVER) shipments = shipmentRepository.findByMotorista(user).stream().map(ship -> toResponse(ship, ship.getMotorista())).toList();
        if (user.getRole() == Role.HOSPITAL_MANAGER) shipments = shipmentRepository.findByHospitalOrigin(user.getHospital()).stream().map(ship -> toResponse(ship, ship.getMotorista())).toList();
        return shipments;
    }

    private User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    private ShipmentRegisterResponse toResponse(Shipment shipment, User motorista) {
        HospitalRegisterResponse hospitalOriginRegisterResponse = new HospitalRegisterResponse(shipment.getHospitalOrigin().getId(), shipment.getHospitalOrigin().getName(), shipment.getHospitalOrigin().getCNPJ());
        HospitalRegisterResponse hospitalDestinationRegisterResponse = new HospitalRegisterResponse(shipment.getHospitalDestination().getId(), shipment.getHospitalDestination().getName(), shipment.getHospitalDestination().getCNPJ());
        UserRegisterResponse motoristaResponse = null;
        if (motorista != null) {
            motoristaResponse = new UserRegisterResponse(motorista.getId(), motorista.getName(), motorista.getEmail(), motorista.getRole(), motorista.getHospital().getId());
        }
        return new ShipmentRegisterResponse(shipment.getId(), shipment.getDescption(), shipment.getType(), shipment.getStatus(), hospitalOriginRegisterResponse, hospitalDestinationRegisterResponse, motoristaResponse);
    }

    private AuditLog createAuditLog(Shipment shipment, Status oldValue) {
        AuditLog auditLog = new AuditLog();
        auditLog.setAffectedEntity("Shipment Id: " + shipment.getId());
        auditLog.setAction("Change status to " + shipment.getStatus());
        auditLog.setWhoActionId(shipment.getMotorista().getId());
        auditLog.setOldValue(oldValue);
        auditLog.setNewValue(shipment.getStatus());

        return auditLog;
    }

    private void validateStatus(Status currentStatus, Status newStatus) {
        boolean isValid = switch (currentStatus) {
            case Status.PENDENTE -> newStatus == Status.EM_TRANSITO;
            case Status.EM_TRANSITO -> newStatus == Status.ENTREGUE;
            case Status.ENTREGUE -> false;
        };

        if (!isValid) {
            throw new IllegalArgumentException("Transição de status inválida: " + currentStatus + " -> " + newStatus);
        }
    }
}
