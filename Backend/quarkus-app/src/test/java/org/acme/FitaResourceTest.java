package org.acme;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.List;

import org.acme.Entities.Fita;
import org.acme.Repositories.FitaRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.MediaType;

@QuarkusTest
class FitaResourceTest {

    public Fita fitaCriada = new Fita("Teste", "Teste", "Teste");
    private static FitaRepository repository;

    @BeforeAll
    static void setUp() {
        repository = new FitaRepository();
    }

    @Transactional
    void setUpDeleteTest() {
        repository.persist(fitaCriada);
    }

    @EnabledIf("setUpUpdateTest")
    @Transactional
    void setUpUpdateTest() {
        repository.persist(fitaCriada);
    }

    @Transactional
    void tearDown(String nome) {
        var fita = repository.find("nome", nome).firstResult();
        repository.delete(fita);
    }

    @Test
    void deveRetornarTodasAsFitas() {
        // Arrange
        setUp();
        List<Fita> fitas = repository.listAll();
        int quantidadeFitasAtual = fitas.size();

        // Act
        int tamanhoListaFitas = given()
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .get("/fita/list")
                .then()
                .assertThat()
                .extract()
                .path("size()");

        // Assert
        assertEquals(quantidadeFitasAtual, tamanhoListaFitas);
    }

    @Test
    void devePermitirCadastrarUmaFita() {
        // Arrange
        setUp();

        // Act
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fitaCriada)
                .when()
                .post("/fita/cadastrarfita")
                .then()
                .assertThat()
                .statusCode(200);

        // Assert
        var fita = repository.find("nome", fitaCriada.getNome()).firstResult();

        assertNotNull(fita);
        assertEquals(fitaCriada.getNome(), fita.getNome());

        // TearDown
        tearDown(fitaCriada.getNome());
    }

    @Test
    @Transactional
    void naoDevePermitirCadastrarUmaFita() {
        // Setup
        setUp();
        repository.persist(fitaCriada);

        // Act
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fitaCriada)
                .when()
                .post("/fita/cadastrarfita")
                .then()
                .assertThat()
                .statusCode(400)
                .and()
                .extract()
                .response()
                .asString()
                .equals("Fita já cadastrado.");

        // Teardown
        tearDown(fitaCriada.getNome());
    }

    @Test
    @Transactional
    void deveDeletarUmaFita() {
        // Setup
        setUpDeleteTest();
        String idFita = String.valueOf(fitaCriada.getId());

        // Act
        given()
                .contentType(MediaType.TEXT_PLAIN)
                .body(idFita)
                .when()
                .delete("/fita/delete")
                .then()
                .assertThat()
                .statusCode(200);

        // Teardown
        tearDown(fitaCriada.getNome());
    }

    @Test
    @Tag("setUpUpdateTest")
    void deveAtualizarUmaFita() {
        // Setup
        setUpUpdateTest();

        // Arrange - Act
        Fita fita = repository.find("nome", fitaCriada.getNome()).firstResult();
        Long idFitaAntiga = fita.getId();

        Fita fitaAtualizada = new Fita("Teste1", "Teste1", "Teste1");
        fitaAtualizada.setId(idFitaAntiga);

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fitaAtualizada)
                .when()
                .put("/fita/update")
                .then()
                .assertThat()
                .statusCode(200);

        // Assert
        var fitaAtualizadaNoBanco = repository.find("nome",
                fitaAtualizada.getNome()).firstResult();
        assertNotNull(fitaAtualizadaNoBanco);
        assertEquals(fitaAtualizada.nome, fitaAtualizada.getNome());

        // Teardown
        tearDown(fitaAtualizada.getNome());
    }

    @Test
    @Transactional
    void naoDeveAtualizarUmaFitaCasoNaoPasseNaValidacao() {

        // Arrange - Act
        Fita fita = new Fita();
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fita)
                .when()
                .put("/fita/update")
                .then()
                .assertThat()
                .statusCode(400);
    }

    @Test
    @Transactional
    void naoDeveAtualizarUmaFitaCasoOIdNaoSejaEncontrado() {

        // Arrange - Act
        Fita fita = new Fita("Teste", "Teste", "Teste");
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fita)
                .when()
                .put("/fita/update")
                .then()
                .assertThat()
                .statusCode(500);
    }
}
