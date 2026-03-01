package com.labkeep.infrastructure.adapter.http.plugins

import com.labkeep.application.dto.ErrorResponse
import com.labkeep.domain.model.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPages() {
    install(StatusPages) {

        exception<NotFoundException> { call, ex ->
            call.respond(HttpStatusCode.NotFound, ErrorResponse(ex.message ?: "No encontrado"))
        }

        exception<ConflictException> { call, ex ->
            call.respond(HttpStatusCode.Conflict, ErrorResponse(ex.message ?: "Conflicto"))
        }

        exception<ValidationException> { call, ex ->
            call.respond(HttpStatusCode.BadRequest, ErrorResponse(ex.message ?: "Error de validación"))
        }

        exception<UnauthorizedException> { call, ex ->
            call.respond(HttpStatusCode.Unauthorized, ErrorResponse(ex.message ?: "No autorizado"))
        }

        exception<NumberFormatException> { call, _ ->
            call.respond(HttpStatusCode.BadRequest, ErrorResponse("El ID debe ser un número entero"))
        }

        exception<IllegalArgumentException> { call, ex ->
            call.respond(HttpStatusCode.BadRequest, ErrorResponse(ex.message ?: "Argumento inválido"))
        }

        exception<Throwable> { call, ex ->
            call.application.log.error("Error no controlado", ex)
            call.respond(HttpStatusCode.InternalServerError, ErrorResponse("Error interno del servidor"))
        }
    }
}
