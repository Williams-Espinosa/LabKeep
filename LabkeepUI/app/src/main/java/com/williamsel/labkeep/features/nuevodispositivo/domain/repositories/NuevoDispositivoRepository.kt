package com.williamsel.labkeep.features.nuevodispositivo.domain.repositories

import com.williamsel.labkeep.features.nuevodispositivo.domain.entities.NuevoDispositivo

interface NuevoDispositivoRepository {
    suspend fun registrarDispositivo(dispositivo: NuevoDispositivo): Result<NuevoDispositivo>
}