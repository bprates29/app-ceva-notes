package br.com.cevanotes.service;

import br.com.cevanotes.dto.RotuloDTO;
import br.com.cevanotes.model.Rotulo;
import br.com.cevanotes.repository.RotuloRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // 👉 Ativa o suporte ao Mockito no JUnit 5
class RotuloServiceTest {

    @Mock
    RotuloRepository repository; // será injetado no serviço

    @InjectMocks
    RotuloService service; // terá o mock injetado automaticamente

    @Test
    void testSalvarRotulo() {
        RotuloDTO dto = new RotuloDTO();
        dto.setNome("IPA Puro Malte");
        dto.setEstilo("IPA");
        dto.setTeorAlcoolico(6.7);
        dto.setCervejaria("Cervejaria Alpha");

        Rotulo rotuloSalvo = new Rotulo();
        rotuloSalvo.setId(1);
        rotuloSalvo.setNome(dto.getNome());
        rotuloSalvo.setEstilo(dto.getEstilo());
        rotuloSalvo.setTeorAlcoolico(dto.getTeorAlcoolico());
        rotuloSalvo.setCervejaria(dto.getCervejaria());
        rotuloSalvo.setDataCadastro(LocalDate.now());

        when(repository.insert(rotuloSalvo)).thenReturn(1);
        when(repository.findById(1)).thenReturn(Optional.of(rotuloSalvo));

        Rotulo result = service.salvar(dto);

        assertEquals(dto.getNome(), result.getNome());
        assertEquals(dto.getEstilo(), result.getEstilo());
        assertEquals(dto.getTeorAlcoolico(), result.getTeorAlcoolico());
        assertEquals(dto.getCervejaria(), result.getCervejaria());
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

    @Test
    void testNaoAtualizarCamposQuandoDTOVazio() {
        RotuloDTO dto = new RotuloDTO();
        dto.setNome("");
        dto.setEstilo("");
        dto.setTeorAlcoolico(0);
        dto.setCervejaria("");

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

        Rotulo atualizado = service.atualizar(2, dto);

        assertEquals(expectedRotulo, atualizado);
    }
//
//    @Test
//    void testAtualizarRotuloNaoExistente() {
//        RotuloDTO dto = new RotuloDTO();
//        dto.setNome("Fake");
//        dto.setEstilo("Fake");
//        dto.setTeorAlcoolico(0.0);
//        dto.setCervejaria("None");
//
//        when(repository.findById(999)).thenReturn(Optional.empty());
//
//        assertThrows(io.javalin.http.NotFoundResponse.class, () -> {
//            service.atualizar(999, dto);
//        });
//    }
//
//    @Test
//    void testBuscarPorIdExistente() {
//        Rotulo rotulo = new Rotulo();
//        rotulo.setId(10);
//        rotulo.setNome("Stout Forte");
//
//        when(repository.findById(10)).thenReturn(Optional.of(rotulo));
//
//        Rotulo resultado = service.buscarPorId(10);
//
//        assertNotNull(resultado);
//        assertEquals("Stout Forte", resultado.getNome());
//    }
//
//    @Test
//    void testBuscarPorIdNaoExistente() {
//        when(repository.findById(999)).thenReturn(Optional.empty());
//
//        assertThrows(io.javalin.http.NotFoundResponse.class, () -> {
//            service.buscarPorId(999);
//        });
//    }
}
