package com.williamsel.labkeep.features.descripciondispositivo.domain.entities

data class DescripcionDispositivo(
    val id: Int,
    val nombre: String,
    val categoria: String,
    val estado: String,
    val fechaRegistro: String,
    val historial: List<String>
)