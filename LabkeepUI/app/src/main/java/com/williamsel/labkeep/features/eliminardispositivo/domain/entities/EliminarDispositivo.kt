package com.williamsel.labkeep.features.eliminardispositivo.domain.entities

data class EliminarDispositivo(
    val id: Int,
    val nombre: String,
    val categoria: String,
    val estado: String,
    val imagenUrl: String?
)