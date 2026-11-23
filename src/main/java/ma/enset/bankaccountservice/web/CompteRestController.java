package ma.enset.bankaccountservice.web;

import ma.enset.bankaccountservice.dto.CompteRequestDTO;
import ma.enset.bankaccountservice.dto.CompteResponseDTO;
import ma.enset.bankaccountservice.services.CompteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comptes")
@RequiredArgsConstructor
@Tag(name = "Comptes", description = "Gestion des comptes bancaires")
public class CompteRestController {

    private final CompteService compteService;

    @GetMapping
    @Operation(summary = "Lister tous les comptes")
    public List<CompteResponseDTO> comptes() {
        return compteService.listerComptes();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter un compte")
    public CompteResponseDTO compte(@PathVariable String id) {
        return compteService.consulterCompte(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer un compte")
    public CompteResponseDTO ajouter(@RequestBody CompteRequestDTO dto) {
        return compteService.ajouterCompte(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un compte (champs non nuls uniquement)")
    public CompteResponseDTO modifier(@PathVariable String id, @RequestBody CompteRequestDTO dto) {
        return compteService.modifierCompte(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer un compte")
    public void supprimer(@PathVariable String id) {
        compteService.supprimerCompte(id);
    }
}
