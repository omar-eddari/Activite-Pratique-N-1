package ma.enset.bankaccountservice.entities;

import ma.enset.bankaccountservice.enums.TypeCompte;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Compte {
    @Id
    private String id;
    private LocalDate dateCreation;
    private Double solde;
    private String devise;
    @Enumerated(EnumType.STRING)
    private TypeCompte type;
    @ManyToOne
    private Client client;
}
