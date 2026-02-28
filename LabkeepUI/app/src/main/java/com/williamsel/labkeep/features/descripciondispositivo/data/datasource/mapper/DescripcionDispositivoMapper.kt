package com.williamsel.labkeep.features.descripciondispositivo.data.datasource.mapper

import com.williamsel.labkeep.features.descripciondispositivo.data.datasource.models.DescripcionDispositivoDto
import com.williamsel.labkeep.features.descripciondispositivo.domain.entities.DescripcionDispositivo

fun DescripcionDispositivoDto.toDomain() = DescripcionDispositivo(
    id = id,
    nombre = nombre,
    categoria = categoriaNombre ?: "Sin categoría",
    estado = estado,
    imagenUrl = imagenUrl,
    fechaCreacion = fechaCreacion?.let {
        "%02d/%02d/%04d".format(
            it.getOrElse(2) { 1 },
            it.getOrElse(1) { 1 },
            it.getOrElse(0) { 2026 }
        )
    } ?: "",
    historial = emptyList()
)