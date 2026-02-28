package com.williamsel.labkeep.features.inventario.data.datasource.mapper

import com.williamsel.labkeep.features.inventario.data.datasource.models.InventarioDto
import com.williamsel.labkeep.features.inventario.domain.entities.Inventario

fun InventarioDto.toDomain() = Inventario(
    id = id,
    nombre = nombre,
    categoria = categoriaNombre ?: "Sin categoría",
    estado = estado,
    imagenUrl = imagenUrl,
    fechaCreacion = fechaCreacion?.let {
        "%02d/%02d/%04d".format(it.getOrElse(2) { 1 }, it.getOrElse(1) { 1 }, it.getOrElse(0) { 2026 })
    } ?: ""
)