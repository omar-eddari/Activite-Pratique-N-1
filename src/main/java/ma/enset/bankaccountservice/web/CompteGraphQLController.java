package ma.enset.bankaccountservice.web;

import ma.enset.bankaccountservice.dto.CompteRequestDTO;
import ma.enset.bankaccountservice.dto.CompteResponseDTO;
import ma.enset.bankaccountservice.entities.Client;
import ma.enset.bankaccountservice.entities.Compte;
import ma.enset.bankaccountservice.exceptions.ClientNotFoundException;
import ma.enset.bankaccountservice.repositories.ClientRepository;
import ma.enset.bankaccountservice.repositories.CompteRepository;
import ma.enset.bankaccountservice.services.CompteService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CompteGraphQLController {

    private final CompteService compteService;
    private final ClientRepository clientRepository;
    private final CompteRepository compteRepository;

    @QueryMapping
    public List<CompteResponseDTO> comptesList() {
        return compteService.listerComptes();
    }

    @QueryMapping
    public CompteResponseDTO compteById(@Argument String id) {
        return compteService.consulterCompte(id);
    }

    @QueryMapping
    public List<Client> clients() {
        return clientRepository.findAll();
    }

    @QueryMapping
    public Client clientById(@Argument Long id) {
        return clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException(id));
    }

    /** Résout Client.comptes à la demande (évite une LazyInitializationException). */
    @SchemaMapping(typeName = "Client", field = "comptes")
    public List<Compte> comptesDuClient(Client client) {
        return compteRepository.findByClientId(client.getId());
    }

    @MutationMapping
    public CompteResponseDTO addCompte(@Argument CompteRequestDTO compte) {
        return compteService.ajouterCompte(compte);
    }

    @MutationMapping
    public CompteResponseDTO updateCompte(@Argument String id, @Argument CompteRequestDTO compte) {
        return compteService.modifierCompte(id, compte);
    }

    @MutationMapping
    public Boolean deleteCompte(@Argument String id) {
        compteService.supprimerCompte(id);
        return true;
    }
}
