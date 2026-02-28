package com.williamsel.labkeep.features.eliminardispositivo.domain.usescases

import com.williamsel.labkeep.features.eliminardispositivo.domain.entities.EliminarDispositivo
import com.williamsel.labkeep.features.eliminardispositivo.domain.repositories.EliminarDispositivoRepository
import javax.inject.Inject

class DeleteEliminarDispositivoUseCase @Inject constructor(
    private val repository: EliminarDispositivoRepository
) {
    suspend fun getDispositivo(id: Int): Result<EliminarDispositivo> =
        repository.getDispositivo(id)

    suspend fun eliminar(id: Int): Result<Unit> =
        repository.eliminarDispositivo(id)
}