package ma.enset.bankaccountservice.dto;

import ma.enset.bankaccountservice.enums.TypeCompte;
import lombok.*;

/** Données envoyées par le client pour créer / modifier un compte. */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CompteRequestDTO {
    private Double solde;
    private String devise;
    private TypeCompte type;
    private Long clientId;
}
