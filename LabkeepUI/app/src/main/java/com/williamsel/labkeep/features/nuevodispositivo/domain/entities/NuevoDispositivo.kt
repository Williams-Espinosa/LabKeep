package com.williamsel.labkeep.features.nuevodispositivo.domain.entities

data class NuevoDispositivo(
    val nombre: String,
    val categoriaId: Int,
    val imagenUri: String? = null
)