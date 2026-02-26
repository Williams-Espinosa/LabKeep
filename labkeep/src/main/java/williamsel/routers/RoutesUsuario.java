package williamsel.routers;

import io.javalin.Javalin;
import williamsel.controller.UsuarioController;


public class RoutesUsuario {
    private final UsuarioController controller;

    public RoutesUsuario(UsuarioController controller) {
        this.controller = controller;
    }

    public void register(Javalin app) {
        app.post("/registro", controller::registrar);
        app.post("/login", controller::login);
    }
}