package ma.enset.bankaccountservice.services;

import ma.enset.bankaccountservice.dto.CompteRequestDTO;
import ma.enset.bankaccountservice.dto.CompteResponseDTO;
import ma.enset.bankaccountservice.entities.Client;
import ma.enset.bankaccountservice.entities.Compte;
import ma.enset.bankaccountservice.exceptions.ClientNotFoundException;
import ma.enset.bankaccountservice.exceptions.CompteNotFoundException;
import ma.enset.bankaccountservice.mappers.CompteMapper;
import ma.enset.bankaccountservice.repositories.ClientRepository;
import ma.enset.bankaccountservice.repositories.CompteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CompteServiceImpl implements CompteService {

    private final CompteRepository compteRepository;
    private final ClientRepository clientRepository;
    private final CompteMapper compteMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CompteResponseDTO> listerComptes() {
        return compteRepository.findAll().stream().map(compteMapper::fromCompte).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CompteResponseDTO consulterCompte(String id) {
        return compteMapper.fromCompte(trouverCompte(id));
    }

    @Override
    public CompteResponseDTO ajouterCompte(CompteRequestDTO dto) {
        Compte compte = compteMapper.fromRequestDTO(dto);
        compte.setId(UUID.randomUUID().toString());
        compte.setDateCreation(LocalDate.now());
        if (compte.getSolde() == null) compte.setSolde(0.0);
        if (dto.getClientId() != null) compte.setClient(trouverClient(dto.getClientId()));
        return compteMapper.fromCompte(compteRepository.save(compte));
    }

    /** Mise à jour partielle : seuls les champs non nuls sont modifiés. */
    @Override
    public CompteResponseDTO modifierCompte(String id, CompteRequestDTO dto) {
        Compte compte = trouverCompte(id);
        if (dto.getSolde() != null) compte.setSolde(dto.getSolde());
        if (dto.getDevise() != null) compte.setDevise(dto.getDevise());
        if (dto.getType() != null) compte.setType(dto.getType());
        if (dto.getClientId() != null) compte.setClient(trouverClient(dto.getClientId()));
        return compteMapper.fromCompte(compteRepository.save(compte));
    }

    @Override
    public void supprimerCompte(String id) {
        compteRepository.delete(trouverCompte(id));
    }

    private Compte trouverCompte(String id) {
        return compteRepository.findById(id).orElseThrow(() -> new CompteNotFoundException(id));
    }

    private Client trouverClient(Long id) {
        return clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException(id));
    }
}
