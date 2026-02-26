package williamsel.routers;

import io.javalin.Javalin;
import williamsel.controller.CategoriaController;

public class RouteCategoria {


    private final CategoriaController controller;

    public RouteCategoria(CategoriaController controller) {
        this.controller = controller;
    }

    public void register(Javalin app) {
        app.get("/categorias",      controller::listar);
        app.get("/categorias/{id}", controller::getById);
        app.post("/categorias",     controller::crear);
        app.put("/categorias/{id}", controller::actualizar);
        app.delete("/categorias/{id}", controller::eliminar);
    }
}
