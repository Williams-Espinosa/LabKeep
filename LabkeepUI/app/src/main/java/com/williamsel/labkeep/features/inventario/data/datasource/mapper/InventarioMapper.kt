package com.williamsel.labkeep.features.inventario.data.datasource.mapper

import com.williamsel.labkeep.features.inventario.data.datasource.models.InventarioDto
import com.williamsel.labkeep.features.inventario.domain.entities.Inventario

fun InventarioDto.toDomain() = Inventario(
    id = id,
    nombre = nombre,
    categoria = categoria,
    estado = estado
)