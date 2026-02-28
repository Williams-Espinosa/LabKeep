package com.williamsel.labkeep.features.eliminardispositivo.data.datasource.mapper

import com.williamsel.labkeep.features.eliminardispositivo.data.datasource.models.EliminarDispositivoDto
import com.williamsel.labkeep.features.eliminardispositivo.domain.entities.EliminarDispositivo

fun EliminarDispositivoDto.toDomain() = EliminarDispositivo(
    id = id,
    nombre = nombre,
    categoria = categoria,
    estado = estado
)