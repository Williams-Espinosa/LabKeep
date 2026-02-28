package com.williamsel.labkeep.features.editardispositivo.domain.repositories

import com.williamsel.labkeep.features.editardispositivo.domain.entities.EditarDispositivo

interface EditarDisposivoRepository {
    suspend fun getDispositivo(id: Int): Result<EditarDispositivo>
    suspend fun editarDispositivo(dispositivo: EditarDispositivo): Result<EditarDispositivo>
}