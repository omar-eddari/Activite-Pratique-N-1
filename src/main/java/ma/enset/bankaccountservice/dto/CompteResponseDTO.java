package ma.enset.bankaccountservice.dto;

import ma.enset.bankaccountservice.enums.TypeCompte;
import lombok.*;

import java.time.LocalDate;

/** Données renvoyées au client. */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CompteResponseDTO {
    private String id;
    private LocalDate dateCreation;
    private Double solde;
    private String devise;
    private TypeCompte type;
    private Long clientId;
    private String nomClient;
}
