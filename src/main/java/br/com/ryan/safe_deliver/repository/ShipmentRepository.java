package br.com.ryan.safe_deliver.repository;

import br.com.ryan.safe_deliver.entity.Hospital;
import br.com.ryan.safe_deliver.entity.Shipment;
import br.com.ryan.safe_deliver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    List<Shipment> findByHospital(Hospital hospital);

    User findByMotorista(User motorista);
}
