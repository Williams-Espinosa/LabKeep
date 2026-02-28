package com.williamsel.labkeep.features.editardispositivo.data.datasource.mapper

import com.williamsel.labkeep.features.editardispositivo.data.datasource.models.EditarDispositivoDto
import com.williamsel.labkeep.features.editardispositivo.domain.entities.EditarDispositivo

fun EditarDispositivoDto.toDomain() = EditarDispositivo(
    id = id,
    nombre = nombre,
    categoria = categoria,
    estado = estado
)

fun EditarDispositivo.toDto() = EditarDispositivoDto(
    id = id,
    nombre = nombre,
    categoria = categoria,
    estado = estado
)