package williamsel.controller;

import io.javalin.http.Context;
import williamsel.model.Categoria;
import williamsel.service.CategoriaService;

import java.util.Map;
import java.util.Optional;

public class CategoriaController {

    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    public void listar(Context ctx) {
        try {
            ctx.json(service.listar());
        } catch (Exception e) {
            ctx.status(500).json(Map.of("error", e.getMessage()));
        }
    }

    public void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Optional<Categoria> opt = service.getById(id);
            if (opt.isPresent()) ctx.json(opt.get());
            else ctx.status(404).json(Map.of("error", "Categoría no encontrada"));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("error", e.getMessage()));
        }
    }

    public void crear(Context ctx) {
        try {
            Categoria c = ctx.bodyAsClass(Categoria.class);
            service.crear(c);
            ctx.status(201).json(Map.of("mensaje", "Categoría creada"));
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("error", e.getMessage()));
        }
    }

    public void actualizar(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Categoria c = ctx.bodyAsClass(Categoria.class);
            boolean ok = service.actualizar(id, c);
            if (ok) ctx.json(Map.of("mensaje", "Categoría actualizada"));
            else ctx.status(404).json(Map.of("error", "Categoría no encontrada"));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("error", e.getMessage()));
        }
    }

    public void eliminar(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            boolean ok = service.eliminar(id);
            if (ok) ctx.json(Map.of("mensaje", "Categoría eliminada"));
            else ctx.status(404).json(Map.of("error", "Categoría no encontrada"));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("error", e.getMessage()));
        }
    }
}
