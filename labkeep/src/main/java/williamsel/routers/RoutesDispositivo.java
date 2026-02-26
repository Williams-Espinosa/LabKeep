package williamsel.routers;

import io.javalin.Javalin;
import williamsel.controller.DispositivoController;

public class RoutesDispositivo {


    private final DispositivoController controller;

    public RoutesDispositivo(DispositivoController controller) {
        this.controller = controller;
    }

    public void register(Javalin app) {

        app.get("/dispositivos",                      controller::listar);
        app.get("/dispositivos/{id}",                 controller::getById);
        app.post("/dispositivos",                     controller::crear);
        app.put("/dispositivos/{id}",                 controller::actualizar);
        app.put("/dispositivos/{id}/imagen",          controller::actualizarImagen);
        app.patch("/dispositivos/{id}/estado",        controller::cambiarEstado);
        app.delete("/dispositivos/{id}",              controller::eliminar);
    }
}
