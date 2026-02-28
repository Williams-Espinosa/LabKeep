package com.williamsel.labkeep.features.editardispositivo.data.datasource.mapper

import com.williamsel.labkeep.features.editardispositivo.data.datasource.models.EditarDispositivoDto
import com.williamsel.labkeep.features.editardispositivo.domain.entities.EditarDispositivo

fun EditarDispositivoDto.toDomain() = EditarDispositivo(
    id = id,
    nombre = nombre ?: "Sin nombre",
    categoria = categoriaNombre ?: "Sin categoría",
    categoriaId = categoriaId ?: 0,
    estado = estado ?: "Sin estado",
    imagenUrl = imagenUrl,
    fechaCreacion = fechaCreacion?.joinToString("/") ?: "Sin fecha"
)

fun EditarDispositivo.toDto() = EditarDispositivoDto(
    id = id,
    nombre = nombre,
    categoriaId = categoriaId,
    categoriaNombre = categoria,
    estado = estado,
    imagenUrl = imagenUrl,
    fechaCreacion = null
)