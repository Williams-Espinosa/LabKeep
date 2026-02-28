package com.williamsel.labkeep.features.descripciondispositivo.data.datasource.models

data class DescripcionDispositivoDto(
    val id: Int,
    val nombre: String,
    val categoria: String,
    val estado: String,
    val fechaRegistro: String,
    val historial: List<String>
)