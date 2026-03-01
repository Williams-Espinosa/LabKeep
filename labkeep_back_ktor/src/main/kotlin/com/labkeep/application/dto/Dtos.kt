package com.labkeep.application.dto

import kotlinx.serialization.Serializable

// ── Usuario ───────────────────────────────────────────────────────────────────
@Serializable data class RegistrarUsuarioRequest(val correo: String, val contrasena: String)
@Serializable data class LoginRequest(val correo: String, val contrasena: String)
@Serializable data class LoginResponse(val mensaje: String, val usuarioId: Int, val correo: String)

// ── Categoria ─────────────────────────────────────────────────────────────────
@Serializable data class CategoriaRequest(val nombre: String)
@Serializable data class CategoriaResponse(val id: Int, val nombre: String)

// ── Dispositivo ───────────────────────────────────────────────────────────────
@Serializable
data class DispositivoResponse(
    val id: Int,
    val nombre: String,
    val categoriaId: Int,
    val categoriaNombre: String?,
    val estado: String,
    val imagenUrl: String?,
    val fechaCreacion: String?
)

@Serializable data class ActualizarDispositivoRequest(val nombre: String, val categoriaId: Int, val estado: String)
@Serializable data class CambiarEstadoRequest(val estado: String)

// ── Prestamo ──────────────────────────────────────────────────────────────────
@Serializable
data class PrestamoResponse(
    val id: Int,
    val dispositivoId: Int,
    val dispositivoNombre: String?,
    val usuarioId: Int,
    val usuarioCorreo: String?,
    val fechaPrestamo: String?,
    val fechaDevolucion: String?
)

@Serializable data class PrestarRequest(val dispositivoId: Int, val usuarioId: Int)

// ── Genéricos ─────────────────────────────────────────────────────────────────
@Serializable data class MensajeResponse(val mensaje: String)
@Serializable data class ErrorResponse(val error: String)
@Serializable data class IdResponse(val id: Int, val mensaje: String)
