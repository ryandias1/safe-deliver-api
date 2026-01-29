package br.com.ryan.safe_deliver.repository;

import br.com.ryan.safe_deliver.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuditLogRepository extends JpaRepository<AuditLog, UUID> {
}
