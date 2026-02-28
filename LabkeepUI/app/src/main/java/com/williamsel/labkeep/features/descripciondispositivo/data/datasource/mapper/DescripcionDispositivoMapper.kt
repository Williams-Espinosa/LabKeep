package com.williamsel.labkeep.features.descripciondispositivo.data.datasource.mapper

import com.williamsel.labkeep.features.descripciondispositivo.data.datasource.models.DescripcionDispositivoDto
import com.williamsel.labkeep.features.descripciondispositivo.domain.entities.DescripcionDispositivo

fun DescripcionDispositivoDto.toDomain() = DescripcionDispositivo(
    id = id,
    nombre = nombre,
    categoria = categoria,
    estado = estado,
    fechaRegistro = fechaRegistro,
    historial = historial
)