package ma.enset.bankaccountservice.repositories;

import ma.enset.bankaccountservice.entities.Compte;
import ma.enset.bankaccountservice.enums.TypeCompte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource
public interface CompteRepository extends JpaRepository<Compte, String> {
    /** GET /data/comptes/search/byType?t=EPARGNE */
    @RestResource(path = "byType")
    List<Compte> findByType(@Param("t") TypeCompte type);

    List<Compte> findByClientId(@Param("clientId") Long clientId);
}
