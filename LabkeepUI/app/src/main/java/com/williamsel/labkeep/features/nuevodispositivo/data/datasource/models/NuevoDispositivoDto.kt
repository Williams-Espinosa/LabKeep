package com.williamsel.labkeep.features.nuevodispositivo.data.datasource.models

data class NuevoDispositivoDto(
    val id: Int = 0,
    val nombre: String = "",
    val categoriaId: Int = 0,
    val categoriaNombre: String = "",
    val estado: String = "",
    val imagenUrl: String = "",
    val fechaCreacion: String = ""
)