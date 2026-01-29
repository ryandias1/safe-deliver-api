package br.com.ryan.safe_deliver.repository;

import br.com.ryan.safe_deliver.entity.Hospital;
import br.com.ryan.safe_deliver.entity.Shipment;
import br.com.ryan.safe_deliver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ShipmentRepository extends JpaRepository<Shipment, UUID> {
    List<Shipment> findByHospitalOrigin(Hospital hospitalOrigin);

    List<Shipment> findByMotorista(User motorista);
}
