package com.williamsel.labkeep.features.inventario.domain.repositories

import com.williamsel.labkeep.features.inventario.domain.entities.Inventario

interface InventarioRepository {
    suspend fun getInventario(): Result<List<Inventario>>
}