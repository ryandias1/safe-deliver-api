package br.com.ryan.safe_deliver.repository;

import br.com.ryan.safe_deliver.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
}
