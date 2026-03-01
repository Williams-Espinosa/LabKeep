package com.labkeep

import com.labkeep.infrastructure.adapter.http.plugins.*
import com.labkeep.infrastructure.adapter.http.routes.*
import com.labkeep.infrastructure.config.AppContainer
import com.labkeep.infrastructure.config.DatabaseConfig
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {

    DatabaseConfig.init()

    configureSerialization()
    configureCors()
    configureStatusPages()
    configureSwagger()

    val c = AppContainer

    routing {
        usuarioRoutes(c.registrarUsuario, c.loginUsuario)
        categoriaRoutes(c.listarCategorias, c.obtenerCategoria, c.crearCategoria, c.actualizarCategoria, c.eliminarCategoria)
        dispositivoRoutes(c.listarDispositivos, c.obtenerDispositivo, c.crearDispositivo, c.actualizarDispositivo, c.actualizarImagen, c.cambiarEstado, c.eliminarDispositivo)
        prestamoRoutes(c.listarPrestamos, c.historialDispositivo, c.prestarDispositivo, c.devolverDispositivo)
    }
}
