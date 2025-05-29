package br.com.cevanotes;

import br.com.cevanotes.config.DbConfig;
import br.com.cevanotes.controller.RotuloController;
import br.com.cevanotes.repository.RotuloRepository;
import br.com.cevanotes.service.RotuloService;
import io.javalin.Javalin;
import io.javalin.http.UnauthorizedResponse;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    private static final String TOKEN_SECRETO = "meu-token-123";

    public static void main(String[] args) throws SQLException, IOException {

        Javalin app = Javalin.create().start(7070);
        var dbConfig = DbConfig.createJdbi();
        var rotuloRepository = new RotuloRepository(dbConfig);
        var rotuloService = new RotuloService(rotuloRepository);

        validacaoDeAcesso(app);

        app.get("/teste", ctx -> ctx.result("API funcionando!"));
        new RotuloController(rotuloService).registrarRotas(app);
    }

    private static void validacaoDeAcesso(Javalin app) {
        app.before(ctx -> {
            String rota = ctx.path();
            if (rota.startsWith("/teste")) { //rotas publicas vamos deixar sem tokens
                return;
            }

            String tokenRecebido = ctx.header("Authorization");

            if (tokenRecebido == null || !tokenRecebido.equals(TOKEN_SECRETO)) {
                throw new UnauthorizedResponse("Token inválido ou ausente");
            }
        });
    }
}