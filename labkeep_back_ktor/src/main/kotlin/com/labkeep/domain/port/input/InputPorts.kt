package com.labkeep.domain.port.input

import com.labkeep.domain.model.*

// ── Usuario ───────────────────────────────────────────────────────────────────
interface RegistrarUsuarioUseCase {
    fun ejecutar(correo: String, contrasena: String)
}

interface LoginUsuarioUseCase {
    fun ejecutar(correo: String, contrasena: String): Usuario
}

// ── Categoria ─────────────────────────────────────────────────────────────────
interface ListarCategoriasUseCase   { fun ejecutar(): List<Categoria> }
interface ObtenerCategoriaUseCase   { fun ejecutar(id: Int): Categoria }
interface CrearCategoriaUseCase     { fun ejecutar(nombre: String) }
interface ActualizarCategoriaUseCase{ fun ejecutar(id: Int, nombre: String): Boolean }
interface EliminarCategoriaUseCase  { fun ejecutar(id: Int): Boolean }

// ── Dispositivo ───────────────────────────────────────────────────────────────
interface ListarDispositivosUseCase   { fun ejecutar(query: String?): List<Dispositivo> }
interface ObtenerDispositivoUseCase   { fun ejecutar(id: Int): Dispositivo }
interface CrearDispositivoUseCase     { fun ejecutar(nombre: String, categoriaId: Int, imagen: ByteArray?, nombreArchivo: String?): Dispositivo }
interface ActualizarDispositivoUseCase{ fun ejecutar(id: Int, nombre: String, categoriaId: Int, estado: String): Boolean }
interface ActualizarImagenUseCase     { fun ejecutar(id: Int, imagen: ByteArray, nombreArchivo: String): String }
interface CambiarEstadoUseCase        { fun ejecutar(id: Int, estado: String): Boolean }
interface EliminarDispositivoUseCase  { fun ejecutar(id: Int): Boolean }

// ── Prestamo ──────────────────────────────────────────────────────────────────
interface ListarPrestamosUseCase      { fun ejecutar(): List<Prestamo> }
interface HistorialDispositivoUseCase { fun ejecutar(dispositivoId: Int): List<Prestamo> }
interface PrestarDispositivoUseCase   { fun ejecutar(dispositivoId: Int, usuarioId: Int): Prestamo }
interface DevolverDispositivoUseCase  { fun ejecutar(dispositivoId: Int): Boolean }
