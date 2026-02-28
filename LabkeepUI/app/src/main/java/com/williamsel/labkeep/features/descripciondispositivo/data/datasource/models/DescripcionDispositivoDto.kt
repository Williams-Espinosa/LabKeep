package com.williamsel.labkeep.features.descripciondispositivo.data.datasource.models

data class DescripcionDispositivoDto(
    val id: Int,
    val nombre: String,
    val categoriaId: Int?,
    val categoriaNombre: String?,
    val estado: String,
    val imagenUrl: String?,
    val fechaCreacion: List<Int>?
)