package com.labkeep.infrastructure.adapter.http.routes

import com.labkeep.application.dto.*
import com.labkeep.domain.port.input.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.usuarioRoutes(
    registrar: RegistrarUsuarioUseCase,
    login: LoginUsuarioUseCase
) {
    route("/usuarios") {

        /**
         * @description Registrar un nuevo usuario
         */
        post("/registro") {
            val req = call.receive<RegistrarUsuarioRequest>()
            registrar.ejecutar(req.correo, req.contrasena)
            call.respond(HttpStatusCode.Created, MensajeResponse("Usuario registrado exitosamente"))
        }

        /**
         * @description Iniciar sesión
         */
        post("/login") {
            val req = call.receive<LoginRequest>()
            val usuario = login.ejecutar(req.correo, req.contrasena)
            call.respond(HttpStatusCode.OK, LoginResponse(
                mensaje   = "Login exitoso",
                usuarioId = usuario.id,
                correo    = usuario.correo
            ))
        }
    }
}
