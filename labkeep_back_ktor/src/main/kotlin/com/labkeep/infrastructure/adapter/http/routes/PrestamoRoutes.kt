package com.labkeep.infrastructure.adapter.http.routes

import com.labkeep.application.dto.*
import com.labkeep.domain.model.Prestamo
import com.labkeep.domain.port.input.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.prestamoRoutes(
    listar: ListarPrestamosUseCase,
    historial: HistorialDispositivoUseCase,
    prestar: PrestarDispositivoUseCase,
    devolver: DevolverDispositivoUseCase
) {
    route("/prestamos") {

        get {
            call.respond(listar.ejecutar().map { it.toResponse() })
        }

        get("/dispositivo/{dispositivoId}") {
            val dId = call.parameters["dispositivoId"]!!.toInt()
            call.respond(historial.ejecutar(dId).map { it.toResponse() })
        }

        post("/prestar") {
            val req = call.receive<PrestarRequest>()
            val prestamo = prestar.ejecutar(req.dispositivoId, req.usuarioId)
            call.respond(HttpStatusCode.Created, IdResponse(
                id      = prestamo.id,
                mensaje = "Préstamo registrado exitosamente"
            ))
        }

        put("/devolver/{dispositivoId}") {
            val dId = call.parameters["dispositivoId"]!!.toInt()
            val ok = devolver.ejecutar(dId)
            if (ok) call.respond(MensajeResponse("Devolución registrada"))
            else call.respond(HttpStatusCode.BadRequest, ErrorResponse("No se pudo registrar la devolución"))
        }
    }
}

private fun Prestamo.toResponse() = PrestamoResponse(
    id                = id,
    dispositivoId     = dispositivoId,
    dispositivoNombre = dispositivoNombre,
    usuarioId         = usuarioId,
    usuarioCorreo     = usuarioCorreo,
    fechaPrestamo     = fechaPrestamo?.toString(),
    fechaDevolucion   = fechaDevolucion?.toString()
)
