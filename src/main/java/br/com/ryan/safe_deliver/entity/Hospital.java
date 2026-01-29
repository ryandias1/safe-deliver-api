package br.com.ryan.safe_deliver.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.Manager;

import java.util.UUID;

@Entity
@Table(name = "hospitais")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String CNPJ;

    @OneToOne
    @JoinColumn(name = "manager_id")
    @JsonBackReference
    private User manager;
}
