package br.com.cevanotes.service;

import br.com.cevanotes.dto.RotuloDTO;
import br.com.cevanotes.model.Rotulo;
import br.com.cevanotes.repository.RotuloRepository;
import br.com.cevanotes.utils.RotuloBuilder;
import br.com.cevanotes.utils.RotuloDTOBuilder;
import io.javalin.http.NotFoundResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // 👉 Ativa o suporte ao Mockito no JUnit 5
class RotuloServiceTest {

    @Mock
    RotuloRepository repository; // será injetado no serviço

    @InjectMocks
    RotuloService service; // terá o mock injetado automaticamente


    // ID padrão para testes
    private static final int ID_EXISTENTE = 2;

    // Dados para reutilização
    private Rotulo rotuloExistente;
    private RotuloDTO rotuloDTO;
    private LocalDate dataAtual;

    @BeforeEach
    void setUp() {
        dataAtual = LocalDate.now();
        rotuloExistente = RotuloBuilder.umRotulo()
                .comId(ID_EXISTENTE)
                .comNome("Antigo")
                .comEstilo("Lager")
                .comTeorAlcoolico(4.5)
                .comCervejaria("Old Brewery")
                .comDataCadastro(dataAtual)
                .build();

        rotuloDTO = RotuloDTOBuilder.umRotuloDTO().build();
    }

    @Test
    void testSalvarRotulo() {
        Rotulo rotuloSalvo = RotuloBuilder.umRotulo()
                .comNome(rotuloDTO.getNome())
                .comEstilo(rotuloDTO.getEstilo())
                .comTeorAlcoolico(rotuloDTO.getTeorAlcoolico())
                .comCervejaria(rotuloDTO.getCervejaria())
                .build();

        when(repository.insert(rotuloSalvo)).thenReturn(1);
        when(repository.findById(1)).thenReturn(Optional.of(rotuloSalvo));

        Rotulo result = service.salvar(rotuloDTO);

        assertEquals(rotuloDTO.getNome(), result.getNome());
        assertEquals(rotuloDTO.getEstilo(), result.getEstilo());
        assertEquals(rotuloDTO.getTeorAlcoolico(), result.getTeorAlcoolico());
        assertEquals(rotuloDTO.getCervejaria(), result.getCervejaria());
    }


    @Test
    void testAtualizarRotuloExistente() {
        RotuloDTO dto = new RotuloDTO();
        dto.setNome("Weiss Tropical");
        dto.setEstilo("Trigo");
        dto.setTeorAlcoolico(5.2);
        dto.setCervejaria("Brauhaus");

        Rotulo existente = new Rotulo();
        existente.setId(2);
        existente.setNome("Antigo");
        existente.setEstilo("Lager");
        existente.setTeorAlcoolico(4.5);
        existente.setCervejaria("Old Brewery");

        var expectedRotulo = new Rotulo(
                2,
                dto.getNome(),
                dto.getEstilo(),
                dto.getTeorAlcoolico(),
                dto.getCervejaria(),
                LocalDate.now()
        );

        when(repository.findById(2)).thenReturn(Optional.of(existente));
        doNothing().when(repository).update(expectedRotulo);

        Rotulo atualizado = service.atualizar(2, dto);

        assertEquals(expectedRotulo, atualizado);
    }

    @ParameterizedTest(name = "Cenário {index}: {0}")
    @MethodSource("fornecerCasosParaNaoAtualizacao")
    void testNaoAtualizarCamposQuandoEntradaInvalida(String cenario, String nome, String estilo,
                                                     double teorAlcoolico, String cervejaria) {
        // Arrange
        RotuloDTO dto = new RotuloDTO();
        dto.setNome(nome);
        dto.setEstilo(estilo);
        dto.setTeorAlcoolico(teorAlcoolico);
        dto.setCervejaria(cervejaria);

        Rotulo existente = new Rotulo();
        existente.setId(2);
        existente.setNome("Antigo");
        existente.setEstilo("Lager");
        existente.setTeorAlcoolico(4.5);
        existente.setCervejaria("Old Brewery");

        var expectedRotulo = new Rotulo(
                2,
                existente.getNome(),
                existente.getEstilo(),
                existente.getTeorAlcoolico(),
                existente.getCervejaria(),
                LocalDate.now()
        );

        when(repository.findById(2)).thenReturn(Optional.of(existente));
        doNothing().when(repository).update(expectedRotulo);

        // Act
        Rotulo atualizado = service.atualizar(2, dto);

        // Assert
        assertEquals(expectedRotulo, atualizado);
    }

    private static Stream<Arguments> fornecerCasosParaNaoAtualizacao() {
        return Stream.of(
                // cenário, nome, estilo, teorAlcoolico, cervejaria
                Arguments.of("Todos os campos vazios", "", "", 0.0, ""),
                Arguments.of("Todos os campos nulos", null, null, 0.0, null),
                Arguments.of("Campos mistos vazios/nulos", "", null, 0.0, ""),
                Arguments.of("Campos com apenas espaços", "  ", "   ", 0.0, "  ")
        );
    }

    @Test
    void testDarErroQuandoRotuloNaoExistente() {
        RotuloDTO dto = new RotuloDTO();
        dto.setNome("Fake");
        dto.setEstilo("Fake");
        dto.setTeorAlcoolico(0.0);
        dto.setCervejaria("None");

        when(repository.findById(999)).thenReturn(Optional.empty());

        var exception = assertThrows(NotFoundResponse.class, () -> {
            service.atualizar(999, dto);
        });

        assertEquals("Rótulo não encontrado", exception.getMessage());
    }

    @Test
    void testListarRotulos() {
        List<Rotulo> rotulosEsperados = List.of(
                new Rotulo(1, "IPA", "American IPA", 6.5, "Cervejaria X", LocalDate.now()),
                new Rotulo(2, "Stout", "Imperial Stout", 8.0, "Cervejaria Y", LocalDate.now())
        );

        when(repository.findAll()).thenReturn(rotulosEsperados);
        List<Rotulo> resultado = service.listar();

        assertEquals(rotulosEsperados, resultado);
        verify(repository, times(1)).findAll();
    }

    @Test
    void testBuscarPorIdExistente() {
        int idExistente = 10;
        Rotulo rotuloEsperado = new Rotulo();
        rotuloEsperado.setId(idExistente);
        rotuloEsperado.setNome("Stout Imperial");
        rotuloEsperado.setEstilo("Stout");
        rotuloEsperado.setTeorAlcoolico(8.5);
        rotuloEsperado.setCervejaria("Cervejaria Z");
        rotuloEsperado.setDataCadastro(LocalDate.now());

        when(repository.findById(idExistente)).thenReturn(Optional.of(rotuloEsperado));

        Rotulo resultado = service.buscarPorId(idExistente);

        assertEquals(rotuloEsperado, resultado);
        verify(repository, times(1)).findById(idExistente);
    }

    @Test
    void testBuscarPorIdNaoExistente() {
        // Arrange
        int idInexistente = 999;
        when(repository.findById(idInexistente)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundResponse exception = assertThrows(NotFoundResponse.class, () -> {
            service.buscarPorId(idInexistente);
        });

        assertEquals("Rótulo não encontrado", exception.getMessage());
        verify(repository, times(1)).findById(idInexistente);
    }

    @Test
    void testDeletarRotuloExistente() {
        // Arrange
        int idExistente = 5;
        Rotulo rotulo = new Rotulo();
        rotulo.setId(idExistente);
        rotulo.setNome("Pilsen Especial");

        when(repository.findById(idExistente)).thenReturn(Optional.of(rotulo));
        doNothing().when(repository).delete(idExistente);

        // Act
        service.deletar(idExistente);

        // Assert
        verify(repository, times(1)).findById(idExistente);
        verify(repository, times(1)).delete(idExistente);
    }

    @Test
    void testDeletarRotuloNaoExistente() {
        int idInexistente = 999;
        when(repository.findById(idInexistente)).thenReturn(Optional.empty());

        NotFoundResponse exception = assertThrows(NotFoundResponse.class, () -> {
            service.deletar(idInexistente);
        });

        assertEquals("Rótulo não encontrado", exception.getMessage());
        verify(repository, times(1)).findById(idInexistente);
        verify(repository, never()).delete(anyInt());
    }
}
