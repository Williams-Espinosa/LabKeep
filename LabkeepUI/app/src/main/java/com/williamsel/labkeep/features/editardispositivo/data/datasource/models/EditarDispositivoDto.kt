package com.williamsel.labkeep.features.editardispositivo.data.datasource.models

data class EditarDispositivoDto(
    val id: Int,
    val nombre: String?,
    val categoriaId: Int?,
    val categoriaNombre: String?,
    val estado: String?,
    val imagenUrl: String?,
    val fechaCreacion: List<Int>?
)