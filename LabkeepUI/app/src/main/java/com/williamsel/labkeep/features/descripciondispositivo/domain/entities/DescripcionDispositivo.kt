package com.williamsel.labkeep.features.descripciondispositivo.domain.entities

data class DescripcionDispositivo(
    val id: Int,
    val nombre: String,
    val categoria: String,
    val estado: String,
    val imagenUrl: String?,
    val fechaCreacion: String,
    val historial: List<String>
)