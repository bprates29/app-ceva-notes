package br.com.cevanotes.controller;

import br.com.cevanotes.dto.RotuloDTO;
import br.com.cevanotes.model.Rotulo;
import br.com.cevanotes.service.RotuloService;
import io.javalin.Javalin;

public class RotuloController {
    private final RotuloService service;

    public RotuloController(RotuloService service) {
        this.service = service;
    }

    public void registrarRotas(Javalin app) {
        app.get("/rotulos", ctx -> ctx.json(service.listar()));
        app.get("/rotulos/{id}", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                ctx.json(service.buscarPorId(id));
            } catch (NumberFormatException e) {
                ctx.status(400).result("ID inválido. Use um número inteiro.");
            }
        });
        app.post("/rotulos", ctx -> {
            RotuloDTO dto = ctx.bodyValidator(RotuloDTO.class)
                    .check(d -> d.getNome() != null && !d.getNome().isBlank(), "Nome é obrigatório")
                    .check(d -> d.getEstilo() != null && !d.getEstilo().isBlank(), "Estilo é obrigatório")
                    .check(d -> d.getTeorAlcoolico() >= 0, "Teor alcoólico deve ser positivo")
                    .get();

            Rotulo novo = service.salvar(dto);
            ctx.status(201).json(novo);
        });
    }


}
