package com.williamsel.labkeep.features.editardispositivo.domain.usescases

import com.williamsel.labkeep.features.editardispositivo.domain.entities.EditarDispositivo
import com.williamsel.labkeep.features.editardispositivo.domain.repositories.EditarDisposivoRepository
import javax.inject.Inject

class PutEditarDispositivoUseCase @Inject constructor(
    private val repository: EditarDisposivoRepository
) {
    suspend fun getDispositivo(id: Int): Result<EditarDispositivo> =
        repository.getDispositivo(id)

    suspend fun editar(dispositivo: EditarDispositivo): Result<EditarDispositivo> =
        repository.editarDispositivo(dispositivo)
}