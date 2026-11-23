package ma.enset.bankaccountservice.services;

import ma.enset.bankaccountservice.dto.CompteRequestDTO;
import ma.enset.bankaccountservice.dto.CompteResponseDTO;

import java.util.List;

public interface CompteService {
    List<CompteResponseDTO> listerComptes();
    CompteResponseDTO consulterCompte(String id);
    CompteResponseDTO ajouterCompte(CompteRequestDTO dto);
    CompteResponseDTO modifierCompte(String id, CompteRequestDTO dto);
    void supprimerCompte(String id);
}
