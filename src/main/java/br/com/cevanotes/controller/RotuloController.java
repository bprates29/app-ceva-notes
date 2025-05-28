package br.com.cevanotes.controller;

import io.javalin.Javalin;

public class RotuloController {
    public void registrarRotas(Javalin app) {
        app.get("/rotulos", ctx -> ctx.result("Listagem de rótulos"));
    }
}
