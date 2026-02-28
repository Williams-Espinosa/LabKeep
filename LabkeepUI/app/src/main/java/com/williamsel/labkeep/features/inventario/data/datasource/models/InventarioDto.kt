package com.williamsel.labkeep.features.inventario.data.datasource.models

data class InventarioDto(
    val id: Int,
    val nombre: String,
    val categoriaId: Int,
    val categoriaNombre: String?,
    val estado: String,
    val imagenUrl: String?,
    val fechaCreacion: List<Int>?
)