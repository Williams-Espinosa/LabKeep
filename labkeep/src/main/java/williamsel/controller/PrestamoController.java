package williamsel.controller;

import williamsel.model.Prestamo;
import williamsel.service.PrestamoService;

import java.util.Map;
import io.javalin.http.Context;


public class PrestamoController {

    private final PrestamoService service;

    public PrestamoController(PrestamoService service) {
        this.service = service;
    }

    public void listar(Context ctx) {
        try {
            ctx.json(service.listar());
        } catch (Exception e) {
            ctx.status(500).json(Map.of("error", e.getMessage()));
        }
    }

    public void historialPorDispositivo(Context ctx) {
        try {
            int dispositivoId = Integer.parseInt(ctx.pathParam("dispositivoId"));
            ctx.json(service.historialPorDispositivo(dispositivoId));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("error", e.getMessage()));
        }
    }

    public void prestar(Context ctx) {
        try {
            int usuarioId = ctx.attribute("usuarioId");
            PrestarRequest req = ctx.bodyAsClass(PrestarRequest.class);

            Prestamo p = service.prestar(req.dispositivo_id, usuarioId);
            ctx.status(201).json(Map.of(
                    "mensaje", "Préstamo registrado",
                    "prestamo_id", p.getId()
            ));
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(Map.of("error", e.getMessage()));
        } catch (IllegalStateException e) {
            ctx.status(409).json(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("error", e.getMessage()));
        }
    }

    public void devolver(Context ctx) {
        try {
            int dispositivoId = Integer.parseInt(ctx.pathParam("dispositivoId"));
            boolean ok = service.devolver(dispositivoId);
            if (ok) ctx.json(Map.of("mensaje", "Devolución registrada"));
            else ctx.status(400).json(Map.of("error", "No se pudo registrar la devolución"));
        } catch (IllegalStateException e) {
            ctx.status(409).json(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("error", e.getMessage()));
        }
    }

    public static class PrestarRequest {
        public int dispositivo_id;
    }
}
