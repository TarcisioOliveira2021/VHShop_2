package org.acme.Security;

import static io.restassured.RestAssured.given;
import org.acme.Entities.Usuario;
import org.acme.Repositories.UsuarioRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.MediaType;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class AuthenticationResourceTest {

    public Usuario usuarioTest = new Usuario("MeuUserTest", "123456");
    private UsuarioRepository repository;

    @BeforeEach
    @Transactional
    void setUp() {
        repository = new UsuarioRepository();
        repository.persist(usuarioTest);
    }

    @AfterEach
    @Transactional
    void tearDown() {
        var usuario = repository.find("nome", usuarioTest.getNome()).firstResult();
        repository.delete(usuario);
    }

    @Test
    @Transactional
    void deveLogarComSucesso() {
        // Arrange
        Usuario usuarioLogin = new Usuario();
        usuarioLogin.setNome("MeuUserTest");
        usuarioLogin.setSenha("123456");

        // Act - Assert
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(usuarioLogin)
                .when()
                .post("/login/autenticar")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);
    }

    @Test
    @Transactional
    void naoDeveLogarCasoUsuarioNaoEstejaArmazenado() {
        // Arrange
        Usuario usuarioLogin = new Usuario();
        usuarioLogin.setNome("MeuUserTestNaoArmazenado");
        usuarioLogin.setSenha("123456");

        // Act - Assert
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(usuarioLogin)
                .when()
                .post("/login/autenticar")
                .then()
                .log().all()
                .assertThat()
                .statusCode(404)
                .and()
                .extract()
                .response()
                .asString()
                .equals("Usuário não encontrado, informe novamente seu login ou senha.");
    }

    @Test
    @Transactional
    void naoDeveLogarCasoUsuarioSenhaDiferente() {
        // Arrange
        Usuario usuarioLogin = new Usuario();
        usuarioLogin.setNome("MeuUserTest");
        usuarioLogin.setSenha("1234567");

        // Act - Assert
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(usuarioLogin)
                .when()
                .post("/login/autenticar")
                .then()
                .log().all()
                .assertThat()
                .statusCode(404)
                .and()
                .extract()
                .response()
                .asString()
                .equals("Usuário não encontrado, informe novamente seu login ou senha.");
    }
}
