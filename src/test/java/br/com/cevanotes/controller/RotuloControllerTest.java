package br.com.cevanotes.controller;

import br.com.cevanotes.dto.RotuloDTO;
import br.com.cevanotes.model.Rotulo;
import br.com.cevanotes.service.RotuloService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.Javalin;
import io.javalin.http.NotFoundResponse;
import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.Assertions;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class RotuloControllerTest {

    @Mock
    private RotuloService rotuloService;

    @InjectMocks
    private RotuloController rotuloController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // JavaTimeModule: Ensina o Jackson como lidar com datas modernas do Java 8+
    }

    @Test
    void deveListarTodosOsRotulos() {
        List<Rotulo> rotulos = Arrays.asList(
                criarRotuloMock(1, "Heineken", "Lager", 5.0, "Heineken"),
                criarRotuloMock(2, "Stella Artois", "Pilsner", 4.8, "Stella Artois"));
        when(rotuloService.listar()).thenReturn(rotulos);

        JavalinTest.test(criarAppComRotas(), (server, client) -> {
            var response = client.get("/rotulos");
            assertEquals(200, response.code());

            Assertions.assertNotNull(response.body());
            List<Rotulo> responseRotulos = objectMapper.readValue(
                    response.body().string(),
                    new TypeReference<>() {
                    }
            );

            assertEquals(2, responseRotulos.size());
            assertEquals("Heineken", responseRotulos.get(0).getNome());
            assertEquals("Stella Artois", responseRotulos.get(1).getNome());
        });

        verify(rotuloService).listar();
    }

    @Test
    void deveRetornarArrayVazioQuandoNaoHouverRotulos() {
        when(rotuloService.listar()).thenReturn(List.of());

        JavalinTest.test(criarAppComRotas(), (server, client) -> {
            var response = client.get("/rotulos");
            assertEquals(200, response.code());

            Assertions.assertNotNull(response.body());
            List<Rotulo> responseRotulos = objectMapper.readValue(
                    response.body().string(),
                    new TypeReference<>() {
                    }
            );

            assertTrue(responseRotulos.isEmpty());
        });
    }

    private Javalin criarAppComRotas() {
        Javalin app = Javalin.create();
        rotuloController.registrarRotas(app);
        return app;
    }

    @Test
    void deveBuscarRotuloPorId() {
        Rotulo rotulo = criarRotuloMock(1, "Guinness", "Stout", 4.2, "Guinness");
        when(rotuloService.buscarPorId(1)).thenReturn(rotulo);

        // When & Then
        JavalinTest.test(criarAppComRotas(), (server, client) -> {
            var response = client.get("/rotulos/1");
            assertEquals(200, response.code());

            Rotulo responseRotulo = objectMapper.readValue(
                    response.body().string(),
                    Rotulo.class
            );

            assertEquals(1, responseRotulo.getId());
            assertEquals("Guinness", responseRotulo.getNome());
            assertEquals("Stout", responseRotulo.getEstilo());
            assertEquals(4.2, responseRotulo.getTeorAlcoolico());
        });

        verify(rotuloService).buscarPorId(1);
    }

    private Rotulo criarRotuloMock(Integer id, String nome, String estilo, double teor, String cervejaria) {
        Rotulo rotulo = new Rotulo();
        rotulo.setId(id);
        rotulo.setNome(nome);
        rotulo.setEstilo(estilo);
        rotulo.setTeorAlcoolico(teor);
        rotulo.setCervejaria(cervejaria);
        rotulo.setDataCadastro(LocalDate.now());
        return rotulo;
    }

    @Test
    void deveRetornar404AoBuscarRotuloInexistente() {
        when(rotuloService.buscarPorId(999)).thenThrow(new NotFoundResponse("Rótulo não encontrado"));

        JavalinTest.test(criarAppComRotas(), (server, client) -> {
            var response = client.get("/rotulos/999");
            assertEquals(404, response.code());
        });
    }

    @Test
    void deveRetornar400ComIdInvalido() {
        JavalinTest.test(criarAppComRotas(), (server, client) -> {
            var response = client.get("/rotulos/abc");
            assertEquals(400, response.code());
            assertTrue(response.body().string().contains("ID inválido"));
        });
    }

    private RotuloDTO criarRotuloDTO(String nome, String estilo, double teor, String cervejaria) {
        RotuloDTO dto = new RotuloDTO();
        dto.setNome(nome);
        dto.setEstilo(estilo);
        dto.setTeorAlcoolico(teor);
        dto.setCervejaria(cervejaria);
        return dto;
    }

    @Test
    void deveCriarNovoRotulo() {
        // Given
        RotuloDTO dto = criarRotuloDTO("Corona", "Lager", 4.5, "Corona");
        Rotulo rotuloSalvo = criarRotuloMock(3, "Corona", "Lager", 4.5, "Corona");

        when(rotuloService.salvar(any(RotuloDTO.class))).thenReturn(rotuloSalvo);

        // When & Then
        JavalinTest.test(criarAppComRotas(), (server, client) -> {
            String jsonBody = objectMapper.writeValueAsString(dto);
            var response = client.post("/rotulos", jsonBody);

            assertEquals(201, response.code());

            Rotulo responseRotulo = objectMapper.readValue(
                    response.body().string(),
                    Rotulo.class
            );

            assertEquals(3, responseRotulo.getId());
            assertEquals("Corona", responseRotulo.getNome());
            assertEquals("Lager", responseRotulo.getEstilo());
            assertEquals(4.5, responseRotulo.getTeorAlcoolico());
        });

        verify(rotuloService).salvar(any(RotuloDTO.class));
    }

    static Stream<Arguments> dadosInvalidosParaCriacaoDeRotulo() {
        return Stream.of(
            Arguments.of("", "Lager", 4.5, "Corona", "Nome é obrigatório"),
            Arguments.of("Corona", "", 4.5, "Corona", "Estilo é obrigatório"),
            Arguments.of("Corona", "Lager", -1.0, "Corona", "Teor alcoólico deve ser positivo")
        );
    }

    @ParameterizedTest
    @MethodSource("dadosInvalidosParaCriacaoDeRotulo")
    void deveRetornar400AoCriarRotuloComDadosInvalidos(String nome, String estilo, double teor, String cervejaria, String mensagemErroEsperada) {
        // Given
        RotuloDTO dto = criarRotuloDTO(nome, estilo, teor, cervejaria);

        // When & Then
        JavalinTest.test(criarAppComRotas(), (server, client) -> {
            String jsonBody = objectMapper.writeValueAsString(dto);
            var response = client.post("/rotulos", jsonBody);

            assertEquals(400, response.code());
            assertTrue(response.body().string().contains(mensagemErroEsperada));
        });

        verify(rotuloService, never()).salvar(any());
    }

    @Test
    void deveAtualizarRotulo() {
        RotuloDTO dto = criarRotuloDTO("Budweiser Atualizada", "Lager", 5.0, "Budweiser");
        Rotulo rotuloAtualizado = criarRotuloMock(1, "Budweiser Atualizada", "Lager", 5.0, "Budweiser");

        when(rotuloService.atualizar(eq(1), any(RotuloDTO.class))).thenReturn(rotuloAtualizado);

        JavalinTest.test(criarAppComRotas(), (server, client) -> {
            String jsonBody = objectMapper.writeValueAsString(dto);
            var response = client.put("/rotulos/1", jsonBody);

            assertEquals(200, response.code());

            Rotulo responseRotulo = objectMapper.readValue(
                    response.body().string(),
                    Rotulo.class
            );

            assertEquals("Budweiser Atualizada", responseRotulo.getNome());
        });

        verify(rotuloService).atualizar(eq(1), any(RotuloDTO.class));
    }

    @Test
    void deveDeletarRotulo() {
        doNothing().when(rotuloService).deletar(1);

        JavalinTest.test(criarAppComRotas(), (server, client) -> {
            var response = client.delete("/rotulos/1");
            assertEquals(204, response.code());
            assertEquals("", response.body().string());
        });

        verify(rotuloService).deletar(1);
    }

    @Test
    void deveRetornar404AoDeletarRotuloInexistente() {
        doThrow(new NotFoundResponse("Rótulo não encontrado")).when(rotuloService).deletar(999);

        JavalinTest.test(criarAppComRotas(), (server, client) -> {
            var response = client.delete("/rotulos/999");
            assertEquals(404, response.code());
        });
    }
}