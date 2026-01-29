package br.com.ryan.safe_deliver.entity;

import br.com.ryan.safe_deliver.enums.ShipmentType;
import br.com.ryan.safe_deliver.enums.Status;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "shipments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String descption;
    private ShipmentType type;
    private Status status;

    @ManyToOne
    @JoinColumn(name = "hospital_origin_id")
    @JsonBackReference
    private Hospital hospitalOrigin;

    @ManyToOne
    @JoinColumn(name = "hospital_destination_id")
    @JsonBackReference
    private Hospital hospitalDestination;

    @ManyToOne
    @JoinColumn(name = "motorista_id")
    @JsonBackReference
    private User motorista;
}
