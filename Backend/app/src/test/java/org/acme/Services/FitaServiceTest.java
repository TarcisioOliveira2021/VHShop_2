package org.acme.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.acme.Entities.Fita;
import org.acme.Repositories.FitaRepository;
import org.acme.Services.FitaService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class FitaServiceTest {

    static FitaRepository mockRepository;
    static FitaService service;

    @BeforeAll
    static void setup() {
        mockRepository = Mockito.mock(FitaRepository.class);
        service = new FitaService(mockRepository);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(mockRepository);
    }

    @Test
    void deveCadastrarFitaComSucesso() throws Exception {
        // Arrange
        Fita novaFita = new Fita();
        novaFita.setNome("Exterminador do Futuro");
        novaFita.setGenero("Ação");
        novaFita.setAno("1984");

        // Act
        service.Cadastrar(novaFita);

        // Assert
        Mockito.verify(mockRepository).persist(novaFita);
    }

    @Test
    void deveLancarUmaExceptionDeFitaJaCadastrada() throws Exception {
        // Arrange
        Fita novaFita = new Fita();
        novaFita.setNome("Exterminador do Futuro");
        novaFita.setGenero("Ação");
        novaFita.setAno("1984");
        FitaService mockservice = new FitaService(mockRepository);

        // Act
        Mockito.when(mockservice.ObterTodas()).thenReturn(List.of(novaFita));

        // Assert
        var exception = assertThrows(Exception.class, () -> mockservice.Cadastrar(novaFita)).getMessage();

        assertNotNull(exception);
        assertEquals("Fita já cadastrada.", exception);
    }

    @Test
    void deveObterTodasAsFitasCadastradas() {
        // Arrange-Act
        var quatidadeFitasEsperadas = mockRepository.listAll().size();
        var quantidadeFitasAtuais = service.ObterTodas().size();

        // Assert
        assertEquals(quatidadeFitasEsperadas, quantidadeFitasAtuais);
    }

    @Test
    void deveDeletarUmaFita() {
        // Arrange
        Fita novaFita = new Fita();
        novaFita.setNome("Exterminador do Futuro");
        novaFita.setGenero("Ação");
        novaFita.setAno("1984");

        // Act
        service.Deletar(novaFita.getId());

        // Assert
        Mockito.verify(mockRepository).deleteById(novaFita.getId());
    }

    @Test
    void deveAtualizarUmaFita() {
        // Arrange
        Fita novaFita = new Fita("Exterminador do Futuro", "Ação", "1984");
        novaFita.setId(3);

        // Act
        Mockito.when(mockRepository.findById(novaFita.getId())).thenReturn(novaFita);
        service.Update(novaFita);

        // Assert
        Mockito.verify(mockRepository).findById(novaFita.getId());
        Mockito.verify(mockRepository).persist(novaFita);
    }
}
