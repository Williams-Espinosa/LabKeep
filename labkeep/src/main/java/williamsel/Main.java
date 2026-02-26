package williamsel;

import io.javalin.Javalin;
import io.javalin.http.Header;
import williamsel.config.DBconfig;
import williamsel.config.Inicio;

public class Main {

    public static void main(String[] args) {

        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(it -> it.anyHost());
            });
        }).start(7001);

        app.options("/*", ctx -> {
            String origin = ctx.header("Origin");
            if (origin != null) ctx.header(Header.ACCESS_CONTROL_ALLOW_ORIGIN, origin);
            ctx.header(Header.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
            ctx.header(Header.ACCESS_CONTROL_ALLOW_HEADERS, "Authorization, Content-Type");
            ctx.header(Header.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, DELETE, OPTIONS");
            ctx.status(200).result("OK");
        });

        Inicio inicio = new Inicio(DBconfig.getDataSource());
        inicio.inicioUsuario().register(app);
        inicio.inicioCategoria().register(app);
        inicio.inicioDispositivo().register(app);
        inicio.inicioPrestamo().register(app);

        System.out.println("API iniciada en http://localhost:7001");
    }
}