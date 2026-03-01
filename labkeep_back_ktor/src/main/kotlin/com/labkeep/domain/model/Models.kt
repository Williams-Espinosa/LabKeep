package com.labkeep.domain.model

import kotlinx.datetime.LocalDateTime

data class Usuario(
    val id: Int = 0,
    val correo: String,
    val contrasena: String
)

data class Categoria(
    val id: Int = 0,
    val nombre: String
)

data class Dispositivo(
    val id: Int = 0,
    val nombre: String,
    val categoriaId: Int,
    val categoriaNombre: String? = null,
    val estado: EstadoDispositivo = EstadoDispositivo.DISPONIBLE,
    val imagenUrl: String? = null,
    val imagenPublicId: String? = null,
    val fechaCreacion: LocalDateTime? = null
)

enum class EstadoDispositivo(val valor: String) {
    DISPONIBLE("disponible"),
    PRESTADO("prestado");

    companion object {
        fun from(valor: String): EstadoDispositivo =
            entries.find { it.valor == valor }
                ?: throw IllegalArgumentException("Estado inválido: $valor")
    }
}

data class Prestamo(
    val id: Int = 0,
    val dispositivoId: Int,
    val usuarioId: Int,
    val fechaPrestamo: LocalDateTime? = null,
    val fechaDevolucion: LocalDateTime? = null,
    val dispositivoNombre: String? = null,
    val usuarioCorreo: String? = null
)
