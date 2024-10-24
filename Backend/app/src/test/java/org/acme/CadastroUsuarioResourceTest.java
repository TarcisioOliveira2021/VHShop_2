package org.acme;

import org.acme.Entities.Usuario;
import org.acme.Repositories.UsuarioRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
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

    @Transactional
    void persistirUsuario(Usuario usuario){
        repository.persist(usuarioCriado);
        repository.flush();
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
        persistirUsuario(usuarioCriado);

        // Act
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(usuarioCriado)
                .when()
                .post("/cadastrarUsuario")
                .then()
                .assertThat()
                .statusCode(400).and().extract().response().asString().equals("Usuário já cadastrado.");

        tearDown();
    }
}
