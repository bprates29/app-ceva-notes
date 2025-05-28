package br.com.cevanotes.controller;

import br.com.cevanotes.config.DbConfig;
import br.com.cevanotes.repository.RotuloRepository;
import io.javalin.Javalin;
import io.javalin.http.NotFoundResponse;

import java.io.IOException;
import java.sql.SQLException;

public class RotuloController {
    public void registrarRotas(Javalin app) {
        try {
            var repository = new RotuloRepository(DbConfig.createJdbi());
            app.get("/rotulos", ctx -> ctx.json(repository.findAll()));
        } catch (SQLException | IOException e) {
            throw new NotFoundResponse("Nenhum rotulo encontrado");
        }
    }
}
