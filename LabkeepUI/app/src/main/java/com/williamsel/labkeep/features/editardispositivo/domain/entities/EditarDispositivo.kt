package com.williamsel.labkeep.features.editardispositivo.domain.entities

data class EditarDispositivo(
    val id: Int,
    val nombre: String,
    val categoria: String,
    val categoriaId: Int,
    val estado: String,
    val imagenUrl: String?,
    val fechaCreacion: String?
)