package com.williamsel.labkeep.features.nuevodispositivo.domain.usescases

import com.williamsel.labkeep.features.nuevodispositivo.domain.entities.NuevoDispositivo
import com.williamsel.labkeep.features.nuevodispositivo.domain.repositories.NuevoDispositivoRepository
import javax.inject.Inject

class PostNuevoDispositivoUseCase @Inject constructor(
    private val repository: NuevoDispositivoRepository
) {
    suspend operator fun invoke(dispositivo: NuevoDispositivo): Result<NuevoDispositivo> {
        return repository.registrarDispositivo(dispositivo)
    }
}