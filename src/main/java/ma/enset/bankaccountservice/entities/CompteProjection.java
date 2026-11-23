package ma.enset.bankaccountservice.entities;

import ma.enset.bankaccountservice.enums.TypeCompte;
import org.springframework.data.rest.core.config.Projection;

/** Projection Spring Data REST : GET /data/comptes?projection=p1 */
@Projection(types = Compte.class, name = "p1")
public interface CompteProjection {
    String getId();
    Double getSolde();
    TypeCompte getType();
}
