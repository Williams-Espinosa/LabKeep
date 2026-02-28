package com.williamsel.labkeep.features.eliminardispositivo.domain.repositories

import com.williamsel.labkeep.features.eliminardispositivo.domain.entities.EliminarDispositivo

interface EliminarDispositivoRepository {
    suspend fun getDispositivo(id: Int): Result<EliminarDispositivo>
    suspend fun eliminarDispositivo(id: Int): Result<Unit>
}