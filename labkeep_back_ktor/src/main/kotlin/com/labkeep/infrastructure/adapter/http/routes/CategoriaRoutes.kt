package com.labkeep.infrastructure.adapter.http.routes

import com.labkeep.application.dto.*
import com.labkeep.domain.model.Categoria
import com.labkeep.domain.port.input.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.categoriaRoutes(
    listar: ListarCategoriasUseCase,
    obtener: ObtenerCategoriaUseCase,
    crear: CrearCategoriaUseCase,
    actualizar: ActualizarCategoriaUseCase,
    eliminar: EliminarCategoriaUseCase
) {
    route("/categorias") {

        get {
            val result = listar.ejecutar().map { it.toResponse() }
            call.respond(result)
        }

        get("/{id}") {
            val id = call.parameters["id"]!!.toInt()
            call.respond(obtener.ejecutar(id).toResponse())
        }

        post {
            val req = call.receive<CategoriaRequest>()
            crear.ejecutar(req.nombre)
            call.respond(HttpStatusCode.Created, MensajeResponse("Categoría creada"))
        }

        put("/{id}") {
            val id = call.parameters["id"]!!.toInt()
            val req = call.receive<CategoriaRequest>()
            val ok = actualizar.ejecutar(id, req.nombre)
            if (ok) call.respond(MensajeResponse("Categoría actualizada"))
            else call.respond(HttpStatusCode.NotFound, ErrorResponse("Categoría no encontrada"))
        }

        delete("/{id}") {
            val id = call.parameters["id"]!!.toInt()
            val ok = eliminar.ejecutar(id)
            if (ok) call.respond(MensajeResponse("Categoría eliminada"))
            else call.respond(HttpStatusCode.NotFound, ErrorResponse("Categoría no encontrada"))
        }
    }
}

private fun Categoria.toResponse() = CategoriaResponse(id = id, nombre = nombre)
