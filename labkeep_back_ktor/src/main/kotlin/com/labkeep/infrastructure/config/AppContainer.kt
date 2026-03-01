package com.labkeep.infrastructure.config

import com.labkeep.application.usecase.*
import com.labkeep.infrastructure.adapter.cloudinary.CloudinaryAdapter
import com.labkeep.infrastructure.adapter.persistence.repository.*

/**
 * Contenedor de dependencias — wiring manual sin frameworks DI.
 * Aquí se conectan los puertos con sus adaptadores.
 */
object AppContainer {

    // ── Repositorios (adaptadores de salida) ────────────────────────────────
    private val usuarioRepo     = UsuarioRepositoryImpl()
    private val categoriaRepo   = CategoriaRepositoryImpl()
    private val dispositivoRepo = DispositivoRepositoryImpl()
    private val prestamoRepo    = PrestamoRepositoryImpl()
    private val imagenStorage   = CloudinaryAdapter()

    // ── Casos de uso de Usuario ─────────────────────────────────────────────
    val registrarUsuario = RegistrarUsuarioUseCaseImpl(usuarioRepo)
    val loginUsuario     = LoginUsuarioUseCaseImpl(usuarioRepo)

    // ── Casos de uso de Categoria ────────────────────────────────────────────
    val listarCategorias    = ListarCategoriasUseCaseImpl(categoriaRepo)
    val obtenerCategoria    = ObtenerCategoriaUseCaseImpl(categoriaRepo)
    val crearCategoria      = CrearCategoriaUseCaseImpl(categoriaRepo)
    val actualizarCategoria = ActualizarCategoriaUseCaseImpl(categoriaRepo)
    val eliminarCategoria   = EliminarCategoriaUseCaseImpl(categoriaRepo)

    // ── Casos de uso de Dispositivo ──────────────────────────────────────────
    val listarDispositivos    = ListarDispositivosUseCaseImpl(dispositivoRepo)
    val obtenerDispositivo    = ObtenerDispositivoUseCaseImpl(dispositivoRepo)
    val crearDispositivo      = CrearDispositivoUseCaseImpl(dispositivoRepo, imagenStorage)
    val actualizarDispositivo = ActualizarDispositivoUseCaseImpl(dispositivoRepo)
    val actualizarImagen      = ActualizarImagenUseCaseImpl(dispositivoRepo, imagenStorage)
    val cambiarEstado         = CambiarEstadoUseCaseImpl(dispositivoRepo)
    val eliminarDispositivo   = EliminarDispositivoUseCaseImpl(dispositivoRepo, imagenStorage)

    // ── Casos de uso de Prestamo ─────────────────────────────────────────────
    val listarPrestamos   = ListarPrestamosUseCaseImpl(prestamoRepo)
    val historialDispositivo = HistorialDispositivoUseCaseImpl(prestamoRepo)
    val prestarDispositivo   = PrestarDispositivoUseCaseImpl(prestamoRepo, dispositivoRepo)
    val devolverDispositivo  = DevolverDispositivoUseCaseImpl(prestamoRepo, dispositivoRepo)
}
