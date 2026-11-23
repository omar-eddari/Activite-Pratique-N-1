package ma.enset.bankaccountservice.mappers;

import ma.enset.bankaccountservice.dto.CompteRequestDTO;
import ma.enset.bankaccountservice.dto.CompteResponseDTO;
import ma.enset.bankaccountservice.entities.Compte;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CompteMapper {

    public CompteResponseDTO fromCompte(Compte compte) {
        CompteResponseDTO dto = new CompteResponseDTO();
        BeanUtils.copyProperties(compte, dto);
        if (compte.getClient() != null) {
            dto.setClientId(compte.getClient().getId());
            dto.setNomClient(compte.getClient().getNom());
        }
        return dto;
    }

    /** Copie les champs simples ; le client est résolu par la couche service. */
    public Compte fromRequestDTO(CompteRequestDTO dto) {
        Compte compte = new Compte();
        BeanUtils.copyProperties(dto, compte);
        return compte;
    }
}
