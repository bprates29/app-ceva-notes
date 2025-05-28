package br.com.cevanotes;

import br.com.cevanotes.config.DbConfig;
import br.com.cevanotes.controller.RotuloController;
import br.com.cevanotes.repository.RotuloRepository;
import br.com.cevanotes.service.RotuloService;
import io.javalin.Javalin;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        Javalin app = Javalin.create().start(7070);
        var dbConfig = DbConfig.createJdbi();
        var rotuloRepository = new RotuloRepository(dbConfig);
        var rotuloService = new RotuloService(rotuloRepository);

        app.get("/teste", ctx -> ctx.result("API funcionando!"));
        new RotuloController(rotuloService).registrarRotas(app);
    }
}