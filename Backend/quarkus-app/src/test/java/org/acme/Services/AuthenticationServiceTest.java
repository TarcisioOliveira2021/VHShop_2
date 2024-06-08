package org.acme.Services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;

import org.acme.Entities.Usuario;
import org.acme.Repositories.UsuarioRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.quarkus.hibernate.orm.panache.PanacheQuery;

class AuthenticationServiceTest {

    static UsuarioService service;
    static AuthenticationService autenticador;
    static UsuarioRepository repository;

    @BeforeAll
    static void setup() {
        repository = Mockito.mock(UsuarioRepository.class);
        service = new UsuarioService(repository);
        autenticador = new AuthenticationService(service);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(repository);
    }

    @Test
    void deveRetornarTrueQuandoLoginESenhaValidos() {
        // Arrange
        String login = "usuario";
        String senha = "senha";
        PanacheQuery<Usuario> query = Mockito.mock(PanacheQuery.class);

        // Act
        Mockito.when(repository.find("nome", login)).thenReturn(query);
        when(query.firstResult()).thenReturn(new Usuario(login, senha));
        boolean autenticado = autenticador.autenticado(login, senha);

        // Assert
        assertTrue(autenticado);
    }

    @Test
    void deveRetornarFalseQuandoLoginESenhaInvalidos() {
        // Arrange
        String login = "usuario";
        String senha = "senha1";
        PanacheQuery<Usuario> query = Mockito.mock(PanacheQuery.class);

        // Act
        Mockito.when(repository.find("nome", login)).thenReturn(query);
        when(query.firstResult()).thenReturn(new Usuario(login, "senha"));
        boolean autenticado = autenticador.autenticado(login, senha);

        // Assert
        assertFalse(autenticado);
    }

    @Test
    void deveRetornarFalseQuandoUsuarioNaoEncontradoNoBanco() {
        // Arrange
        String login = "usuario";
        String senha = "senha1";
        PanacheQuery<Usuario> query = Mockito.mock(PanacheQuery.class);

        // Act
        Mockito.when(repository.find("nome", login)).thenReturn(query);
        when(query.firstResult()).thenReturn(null);
        boolean autenticado = autenticador.autenticado(login, senha);

        // Assert
        assertFalse(autenticado);
    }
}
