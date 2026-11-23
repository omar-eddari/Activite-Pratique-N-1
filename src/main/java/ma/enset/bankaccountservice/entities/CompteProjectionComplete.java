package ma.enset.bankaccountservice.entities;

import ma.enset.bankaccountservice.enums.TypeCompte;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDate;

/** Projection avec le nom du client : GET /data/comptes?projection=complet */
@Projection(types = Compte.class, name = "complet")
public interface CompteProjectionComplete {
    String getId();
    LocalDate getDateCreation();
    Double getSolde();
    String getDevise();
    TypeCompte getType();
    @Value("#{target.client?.nom}")
    String getNomClient();
}
