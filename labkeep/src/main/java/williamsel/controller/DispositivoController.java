package williamsel.controller;

import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import williamsel.model.Dispositivo;
import williamsel.service.DispositivoService;

import java.util.Map;
import java.util.Optional;

public class DispositivoController {


    private final DispositivoService service;

    public DispositivoController(DispositivoService service) {
        this.service = service;
    }

    public void listar(Context ctx) {
        try {
            String query = ctx.queryParam("q");
            ctx.json(service.listar(query));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("error", e.getMessage()));
        }
    }

    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Optional<Dispositivo> opt = service.getById(id);
            if (opt.isPresent()) ctx.json(opt.get());
            else ctx.status(404).json(Map.of("error", "Dispositivo no encontrado"));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("error", e.getMessage()));
        }
    }

    public void crear(Context ctx) {
        try {
            String nombre      = ctx.formParam("nombre");
            String catStr      = ctx.formParam("categoria_id");
            UploadedFile imagen = ctx.uploadedFile("imagen");

            if (nombre == null || catStr == null) {
                ctx.status(400).json(Map.of("error", "nombre y categoria_id son obligatorios"));
                return;
            }

            int categoriaId = Integer.parseInt(catStr);
            Dispositivo d = service.crear(nombre, categoriaId, imagen);
            ctx.status(201).json(d);

        } catch (IllegalArgumentException e) {
            ctx.status(400).json(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).json(Map.of("error", "Error al crear dispositivo: " + e.getMessage()));
        }
    }

    public void actualizar(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Dispositivo d = ctx.bodyAsClass(Dispositivo.class);
            boolean ok = service.actualizar(id, d);
            if (ok) ctx.json(Map.of("mensaje", "Dispositivo actualizado"));
            else ctx.status(404).json(Map.of("error", "Dispositivo no encontrado"));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("error", e.getMessage()));
        }
    }

    public void actualizarImagen(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            UploadedFile imagen = ctx.uploadedFile("imagen");

            if (imagen == null) {
                ctx.status(400).json(Map.of("error", "No se envió ninguna imagen"));
                return;
            }

            String url = service.actualizarImagen(id, imagen);
            ctx.json(Map.of("mensaje", "Imagen actualizada", "url", url));

        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).json(Map.of("error", "Error al actualizar imagen: " + e.getMessage()));
        }
    }

   public void cambiarEstado(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            EstadoRequest req = ctx.bodyAsClass(EstadoRequest.class);

           boolean ok = service.cambiarEstado(id, req.estado);
            if (ok) ctx.json(Map.of("mensaje", "Estado actualizado a: " + req.estado));
            else ctx.status(404).json(Map.of("error", "Dispositivo no encontrado"));

        } catch (IllegalArgumentException e) {
            ctx.status(400).json(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("error", e.getMessage()));
        }
    }

  public void eliminar(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            boolean ok = service.eliminar(id);
            if (ok) ctx.json(Map.of("mensaje", "Dispositivo eliminado"));
            else ctx.status(404).json(Map.of("error", "Dispositivo no encontrado"));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("error", e.getMessage()));
        }
    }

    public static class EstadoRequest {
        public String estado;
    }
}
