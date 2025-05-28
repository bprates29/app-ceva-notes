package br.com.cevanotes.controller;

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
    }
}
