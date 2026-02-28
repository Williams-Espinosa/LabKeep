package com.williamsel.labkeep.features.nuevodispositivo.data.datasource.mapper

import com.williamsel.labkeep.features.nuevodispositivo.data.datasource.models.NuevoDispositivoDto
import com.williamsel.labkeep.features.nuevodispositivo.domain.entities.NuevoDispositivo

fun NuevoDispositivoDto.toDomain() = NuevoDispositivo(
    nombre = nombre,
    categoriaId = categoriaId
)