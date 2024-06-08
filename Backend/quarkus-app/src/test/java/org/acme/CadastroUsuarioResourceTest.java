package org.acme;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.acme.Entities.Usuario;
import org.acme.Repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.MediaType;

@QuarkusTest
class CadastroUsuarioResourceTest {

    private Usuario usuarioCriado;
    private static UsuarioRepository repository;

    @BeforeAll
    static void setUp() {
        repository = new UsuarioRepository();
    }

    @Transactional
    void tearDown() {
        UsuarioRepository repository = new UsuarioRepository();
        var usuario = repository.find("nome", usuarioCriado.getNome()).firstResult();
        repository.delete(usuario);
    }

    @Test
    void devePermitirCadastrarUsuario() {
        // Arrange
        usuarioCriado = new Usuario("Teste1", "teste");

        // Act
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(usuarioCriado)
                .when()
                .post("/cadastrarUsuario")
                .then()
                .assertThat()
                .statusCode(200);

        var usuarioSalvo = repository.find("nome", usuarioCriado.getNome()).firstResult();

        // Assert
        assertNotNull(usuarioSalvo);
        assertEquals(usuarioCriado.getNome(), usuarioSalvo.getNome());

        // TearDown
        tearDown();
    }

    @Test
    void naoDevePermitirCadastrarUsuario() {
        // Arrange
        usuarioCriado = new Usuario("testeTeste", "teste");

        // Act
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(usuarioCriado)
                .when()
                .post("/cadastrarUsuario")
                .then()
                // Assert
                .assertThat()
                .statusCode(400).and().extract().response().asString().equals("Usuário já cadastrado.");
    }
}
