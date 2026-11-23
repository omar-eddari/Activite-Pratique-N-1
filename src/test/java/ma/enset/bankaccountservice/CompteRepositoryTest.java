package ma.enset.bankaccountservice;

import ma.enset.bankaccountservice.entities.Client;
import ma.enset.bankaccountservice.entities.Compte;
import ma.enset.bankaccountservice.enums.TypeCompte;
import ma.enset.bankaccountservice.repositories.ClientRepository;
import ma.enset.bankaccountservice.repositories.CompteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/** 4. Test de la couche DAO. */
@DataJpaTest
class CompteRepositoryTest {

    @Autowired CompteRepository compteRepository;
    @Autowired ClientRepository clientRepository;

    private Compte nouveauCompte(TypeCompte type, Client client) {
        return Compte.builder().id(UUID.randomUUID().toString()).dateCreation(LocalDate.now())
                .solde(1000.0).devise("MAD").type(type).client(client).build();
    }

    @Test
    void crudEtRequetesDerivees() {
        // On vide les données insérées par le CommandLineRunner
        compteRepository.deleteAll();
        Client client = clientRepository.save(Client.builder().nom("Test").build());
        Compte c1 = compteRepository.save(nouveauCompte(TypeCompte.COURANT, client));
        compteRepository.save(nouveauCompte(TypeCompte.EPARGNE, client));

        assertThat(compteRepository.findAll()).hasSize(2);
        assertThat(compteRepository.findById(c1.getId())).get()
                .extracting(Compte::getSolde).isEqualTo(1000.0);
        assertThat(compteRepository.findByType(TypeCompte.EPARGNE)).hasSize(1);
        assertThat(compteRepository.findByClientId(client.getId())).hasSize(2);

        c1.setSolde(5000.0);
        compteRepository.save(c1);
        assertThat(compteRepository.findById(c1.getId()).orElseThrow().getSolde()).isEqualTo(5000.0);

        compteRepository.deleteById(c1.getId());
        assertThat(compteRepository.count()).isEqualTo(1);
    }
}
