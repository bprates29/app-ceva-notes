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
    }
}
