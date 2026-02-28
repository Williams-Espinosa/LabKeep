package com.williamsel.labkeep.features.eliminardispositivo.data.datasource.models

data class EliminarDispositivoDto(
    val id: Int,
    val nombre: String,
    val categoriaId: Int?,
    val categoriaNombre: String?,
    val estado: String,
    val imagenUrl: String?,
    val fechaCreacion: List<Int>?
)