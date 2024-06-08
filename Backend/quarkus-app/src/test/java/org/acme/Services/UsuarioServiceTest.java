package org.acme.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;

import org.acme.Entities.Usuario;
import org.acme.Repositories.UsuarioRepository;
import org.acme.Services.UsuarioService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.quarkus.hibernate.orm.panache.PanacheQuery;

class UsuarioServiceTest {

    static UsuarioRepository mockRepository;
    static UsuarioService service;

    @BeforeAll
    static void setup() {
        mockRepository = Mockito.mock(UsuarioRepository.class);
        service = new UsuarioService(mockRepository);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(mockRepository);
    }

    @Test
    @SuppressWarnings("unchecked")
    void devePermitirCadastrarUsuario() throws Exception {
        // Arrange
        var usuario = new Usuario("Teste1", "teste");
        var qtdUsuarios = 0;
        PanacheQuery<Usuario> query = Mockito.mock(PanacheQuery.class);

        // Act
        Mockito.when(mockRepository.find("nome", usuario.getNome())).thenReturn(query);
        when(query.list()).thenReturn(List.of(new Usuario("Teste2", "teste")));

        service.cadastrar(usuario);
        qtdUsuarios = mockRepository.listAll().size();

        // Assert
        assertEquals(qtdUsuarios, mockRepository.listAll().size());
    }

    @Test
    @SuppressWarnings("unchecked")
    void naoDevePermitirCadastrarUsuario() throws Exception {
        // Arrange
        var usuario = new Usuario("Teste1", "teste");
        PanacheQuery<Usuario> query = Mockito.mock(PanacheQuery.class);
        UsuarioService mockservice = new UsuarioService(mockRepository);

        // Act
        Mockito.when(mockRepository.find("nome", usuario.getNome())).thenReturn(query);
        when(query.list()).thenReturn(List.of(usuario));

        // Assert
        var exception = assertThrows(Exception.class, () -> mockservice.cadastrar(usuario)).getMessage();

        assertNotNull(exception);
        assertEquals("Usuário já cadastrado.", exception);
    }

    @Test
    @SuppressWarnings("unchecked")
    void deveAutenticarUmUsuario() {
        // Arrange
        var usuario = new Usuario("Teste1", "teste");
        PanacheQuery<Usuario> query = Mockito.mock(PanacheQuery.class);

        // Act
        Mockito.when(mockRepository.find("nome", usuario.getNome())).thenReturn(query);
        when(query.firstResult()).thenReturn(usuario);

        // Assert
        assertTrue(service.autenticar(usuario.getNome(), "teste"));
    }

    @Test
    @SuppressWarnings("unchecked")
    void naoDeveAutenticarOUsuarioCasoNaoEstejaNoBanco() {
        // Arrange
        var usuario = new Usuario("Teste1", "teste");
        PanacheQuery<Usuario> query = Mockito.mock(PanacheQuery.class);

        // Act
        Mockito.when(mockRepository.find("nome", usuario.getNome())).thenReturn(query);
        when(query.firstResult()).thenReturn(null);

        // Assert
        assertFalse(service.autenticar(usuario.getNome(), "teste"));
    }

    @Test
    @SuppressWarnings("unchecked")
    void naoDeveAutenticarOUsuarioCasoSenhaErrada() {
        // Arrange
        var usuario = new Usuario("Teste1", "teste");
        PanacheQuery<Usuario> query = Mockito.mock(PanacheQuery.class);

        // Act
        Mockito.when(mockRepository.find("nome", usuario.getNome())).thenReturn(query);
        when(query.firstResult()).thenReturn(usuario);

        // Assert
        assertFalse(service.autenticar(usuario.getNome(), "teste1"));
    }
}
