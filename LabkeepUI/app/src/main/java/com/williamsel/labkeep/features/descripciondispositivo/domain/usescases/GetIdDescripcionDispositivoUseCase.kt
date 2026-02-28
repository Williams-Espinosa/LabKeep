package com.williamsel.labkeep.features.descripciondispositivo.domain.usescases

import com.williamsel.labkeep.features.descripciondispositivo.domain.entities.DescripcionDispositivo
import com.williamsel.labkeep.features.descripciondispositivo.domain.repositories.DescripcionDispositivoRepository
import javax.inject.Inject

class GetIdDescripcionDispositivoUseCase @Inject constructor(
    private val repository: DescripcionDispositivoRepository
) {
    suspend fun getDispositivo(id: Int): Result<DescripcionDispositivo> =
        repository.getDispositivo(id)

    suspend fun cambiarEstado(id: Int, nuevoEstado: String): Result<DescripcionDispositivo> =
        repository.cambiarEstado(id, nuevoEstado)
}