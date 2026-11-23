package ma.enset.bankaccountservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/** Tests d'intégration : REST, Spring Data REST, Swagger et GraphQL. */
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureGraphQlTester
class BankAccountServiceApplicationTests {

    @Autowired MockMvc mvc;
    @Autowired GraphQlTester graphQlTester;

    @Test
    void restCrud() throws Exception {
        mvc.perform(get("/api/comptes")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists());

        String body = mvc.perform(post("/api/comptes").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"solde\":1500,\"devise\":\"MAD\",\"type\":\"EPARGNE\",\"clientId\":1}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nomClient").value("Mohamed"))
                .andReturn().getResponse().getContentAsString();
        String id = body.replaceAll(".*\"id\":\"([^\"]+)\".*", "$1");

        mvc.perform(put("/api/comptes/" + id).contentType(MediaType.APPLICATION_JSON).content("{\"solde\":2000}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.solde").value(2000.0))
                .andExpect(jsonPath("$.type").value("EPARGNE"));

        mvc.perform(delete("/api/comptes/" + id)).andExpect(status().isNoContent());
        mvc.perform(get("/api/comptes/" + id)).andExpect(status().isNotFound());
    }

    @Test
    void springDataRestProjection() throws Exception {
        mvc.perform(get("/data/comptes?projection=p1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.comptes[0].solde").exists())
                .andExpect(jsonPath("$._embedded.comptes[0].devise").doesNotExist());
        mvc.perform(get("/data/comptes/search/byType?t=EPARGNE&projection=complet"))
                .andExpect(status().isOk());
    }

    @Test
    void swaggerDocs() throws Exception {
        mvc.perform(get("/v3/api-docs")).andExpect(status().isOk())
                .andExpect(jsonPath("$.paths['/api/comptes']").exists());
    }

    @Test
    void graphql() {
        graphQlTester.document("{ clients { nom comptes { id solde } } }").execute()
                .path("clients[0].nom").entity(String.class).isEqualTo("Mohamed")
                .path("clients[0].comptes").entityList(Object.class).hasSizeGreaterThan(1);

        String id = graphQlTester.document(
                        "mutation { addCompte(compte:{solde:300, devise:\"EUR\", type:COURANT, clientId:2}) { id nomClient } }")
                .execute().path("addCompte.id").entity(String.class).get();

        graphQlTester.document("{ compteById(id:\"" + id + "\") { solde nomClient } }").execute()
                .path("compteById.nomClient").entity(String.class).isEqualTo("Yassine");

        graphQlTester.document("mutation { deleteCompte(id:\"" + id + "\") }").execute()
                .path("deleteCompte").entity(Boolean.class).isEqualTo(true);

        graphQlTester.document("{ compteById(id:\"inconnu\") { id } }").execute()
                .errors().expect(e -> e.getMessage().equals("Compte inconnu introuvable"));
    }
}
