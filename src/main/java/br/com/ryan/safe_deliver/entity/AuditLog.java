package br.com.ryan.safe_deliver.entity;

import br.com.ryan.safe_deliver.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "audits")
@Getter
@Setter
@NoArgsConstructor
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String affectedEntity;
    private String action;
    private UUID whoActionId;
    private Status oldValue;
    private Status newValue;
}
