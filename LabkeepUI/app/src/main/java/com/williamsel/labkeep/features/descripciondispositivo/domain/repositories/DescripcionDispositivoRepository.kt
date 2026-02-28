package com.williamsel.labkeep.features.descripciondispositivo.domain.repositories

import com.williamsel.labkeep.features.descripciondispositivo.domain.entities.DescripcionDispositivo

interface DescripcionDispositivoRepository {
    suspend fun getDispositivo(id: Int): Result<DescripcionDispositivo>
    suspend fun cambiarEstado(id: Int, nuevoEstado: String): Result<DescripcionDispositivo>
}