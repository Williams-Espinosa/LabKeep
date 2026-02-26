package williamsel.routers;

import io.javalin.Javalin;
import williamsel.controller.PrestamoController;

public class RoutesPrestamos {


    private final PrestamoController controller;

    public RoutesPrestamos(PrestamoController controller) {
        this.controller = controller;
    }

    public void register(Javalin app) {
        app.get("/prestamos",                                      controller::listar);
        app.get("/prestamos/dispositivo/{dispositivoId}",          controller::historialPorDispositivo);
        app.post("/prestamos/prestar",                             controller::prestar);
        app.put("/prestamos/devolver/{dispositivoId}",             controller::devolver);
    }

}
