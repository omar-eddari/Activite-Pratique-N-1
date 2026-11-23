package ma.enset.bankaccountservice;

import ma.enset.bankaccountservice.entities.Client;
import ma.enset.bankaccountservice.entities.Compte;
import ma.enset.bankaccountservice.enums.TypeCompte;
import ma.enset.bankaccountservice.repositories.ClientRepository;
import ma.enset.bankaccountservice.repositories.CompteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class BankAccountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankAccountServiceApplication.class, args);
    }

    /** Jeu de données initial : 4 clients, 2 à 4 comptes chacun. */
    @Bean
    CommandLineRunner start(CompteRepository compteRepository, ClientRepository clientRepository) {
        return args -> {
            List.of("Mohamed", "Yassine", "Hanae", "Imane").forEach(nom ->
                    clientRepository.save(Client.builder().nom(nom).build()));

            clientRepository.findAll().forEach(client -> {
                int n = 2 + (int) (Math.random() * 3);
                for (int i = 0; i < n; i++) {
                    compteRepository.save(Compte.builder()
                            .id(UUID.randomUUID().toString())
                            .dateCreation(LocalDate.now())
                            .solde(10000 + Math.random() * 90000)
                            .devise(Math.random() > 0.5 ? "MAD" : "EUR")
                            .type(Math.random() > 0.5 ? TypeCompte.COURANT : TypeCompte.EPARGNE)
                            .client(client)
                            .build());
                }
            });
        };
    }
}
