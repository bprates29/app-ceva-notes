package br.com.cevanotes.service;

import br.com.cevanotes.dto.RotuloDTO;
import br.com.cevanotes.model.Rotulo;
import br.com.cevanotes.repository.RotuloRepository;
import io.javalin.http.NotFoundResponse;

import java.time.LocalDate;
import java.util.List;

public class RotuloService {
    private final RotuloRepository repository;

    public RotuloService(RotuloRepository repository) {
        this.repository = repository;
    }

    public List<Rotulo> listar() {
        return repository.findAll();
    }

    public Rotulo buscarPorId(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundResponse("Rótulo não encontrado"));
    }

    public Rotulo salvar(RotuloDTO dto) {
        Rotulo rotulo = new Rotulo();
        rotulo.setNome(dto.getNome());
        rotulo.setEstilo(dto.getEstilo());
        rotulo.setTeorAlcoolico(dto.getTeorAlcoolico());
        rotulo.setCervejaria(dto.getCervejaria());
        rotulo.setDataCadastro(LocalDate.now());

        return buscarPorId(repository.insert(rotulo));
    }

}
