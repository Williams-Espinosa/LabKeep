package com.williamsel.labkeep.features.inventario.domain.entities

data class Inventario(
    val id: Int,
    val nombre: String,
    val categoria: String,
    val estado: String,
    val imagenUrl: String?,
    val fechaCreacion: String?
)