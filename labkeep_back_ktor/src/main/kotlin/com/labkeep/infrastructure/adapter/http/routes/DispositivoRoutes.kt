package com.labkeep.infrastructure.adapter.http.routes

import com.labkeep.application.dto.*
import com.labkeep.domain.model.Dispositivo
import com.labkeep.domain.port.input.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.dispositivoRoutes(
    listar: ListarDispositivosUseCase,
    obtener: ObtenerDispositivoUseCase,
    crear: CrearDispositivoUseCase,
    actualizar: ActualizarDispositivoUseCase,
    actualizarImagen: ActualizarImagenUseCase,
    cambiarEstado: CambiarEstadoUseCase,
    eliminar: EliminarDispositivoUseCase
) {
    route("/dispositivos") {

        get {
            val query = call.request.queryParameters["q"]
            call.respond(listar.ejecutar(query).map { it.toResponse() })
        }

        get("/{id}") {
            val id = call.parameters["id"]!!.toInt()
            call.respond(obtener.ejecutar(id).toResponse())
        }

        // Crea dispositivo con imagen opcional (multipart/form-data)
        post {
            val multipart = call.receiveMultipart()
            var nombre: String? = null
            var categoriaId: Int? = null
            var imagenBytes: ByteArray? = null
            var imagenNombre: String? = null

            multipart.forEachPart { part ->
                when (part) {
                    is PartData.FormItem -> when (part.name) {
                        "nombre"       -> nombre      = part.value
                        "categoria_id" -> categoriaId = part.value.toIntOrNull()
                    }
                    is PartData.FileItem -> {
                        imagenBytes  = part.streamProvider().readBytes()
                        imagenNombre = part.originalFileName
                    }
                    else -> {}
                }
                part.dispose()
            }

            if (nombre.isNullOrBlank() || categoriaId == null) {
                call.respond(HttpStatusCode.BadRequest, ErrorResponse("nombre y categoria_id son obligatorios"))
                return@post
            }

            val dispositivo = crear.ejecutar(nombre!!, categoriaId!!, imagenBytes, imagenNombre)
            call.respond(HttpStatusCode.Created, dispositivo.toResponse())
        }

        put("/{id}") {
            val id = call.parameters["id"]!!.toInt()
            val req = call.receive<ActualizarDispositivoRequest>()
            val ok = actualizar.ejecutar(id, req.nombre, req.categoriaId, req.estado)
            if (ok) call.respond(MensajeResponse("Dispositivo actualizado"))
            else call.respond(HttpStatusCode.NotFound, ErrorResponse("Dispositivo no encontrado"))
        }

        // Actualizar solo imagen (multipart/form-data)
        put("/{id}/imagen") {
            val id = call.parameters["id"]!!.toInt()
            val multipart = call.receiveMultipart()
            var imagenBytes: ByteArray? = null
            var imagenNombre: String? = null

            multipart.forEachPart { part ->
                if (part is PartData.FileItem) {
                    imagenBytes  = part.streamProvider().readBytes()
                    imagenNombre = part.originalFileName
                }
                part.dispose()
            }

            if (imagenBytes == null || imagenNombre == null) {
                call.respond(HttpStatusCode.BadRequest, ErrorResponse("Se requiere una imagen"))
                return@put
            }

            val url = actualizarImagen.ejecutar(id, imagenBytes!!, imagenNombre!!)
            call.respond(MensajeResponse("Imagen actualizada: $url"))
        }

        patch("/{id}/estado") {
            val id = call.parameters["id"]!!.toInt()
            val req = call.receive<CambiarEstadoRequest>()
            val ok = cambiarEstado.ejecutar(id, req.estado)
            if (ok) call.respond(MensajeResponse("Estado actualizado a: ${req.estado}"))
            else call.respond(HttpStatusCode.NotFound, ErrorResponse("Dispositivo no encontrado"))
        }

        delete("/{id}") {
            val id = call.parameters["id"]!!.toInt()
            val ok = eliminar.ejecutar(id)
            if (ok) call.respond(MensajeResponse("Dispositivo eliminado"))
            else call.respond(HttpStatusCode.NotFound, ErrorResponse("Dispositivo no encontrado"))
        }
    }
}

private fun Dispositivo.toResponse() = DispositivoResponse(
    id              = id,
    nombre          = nombre,
    categoriaId     = categoriaId,
    categoriaNombre = categoriaNombre,
    estado          = estado.valor,
    imagenUrl       = imagenUrl,
    fechaCreacion   = fechaCreacion?.toString()
)
