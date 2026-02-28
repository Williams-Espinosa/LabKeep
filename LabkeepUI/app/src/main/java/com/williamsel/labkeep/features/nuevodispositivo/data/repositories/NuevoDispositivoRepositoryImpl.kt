package com.williamsel.labkeep.features.nuevodispositivo.data.repositories

import com.williamsel.labkeep.features.nuevodispositivo.data.datasource.api.JsonPlaceHolderNuevoDispositivoApi
import com.williamsel.labkeep.features.nuevodispositivo.data.datasource.mapper.toDomain
import com.williamsel.labkeep.features.nuevodispositivo.data.datasource.mapper.toDto
import com.williamsel.labkeep.features.nuevodispositivo.domain.entities.NuevoDispositivo
import com.williamsel.labkeep.features.nuevodispositivo.domain.repositories.NuevoDispositivoRepository
import javax.inject.Inject

class NuevoDispositivoRepositoryImpl @Inject constructor(
    private val api: JsonPlaceHolderNuevoDispositivoApi
) : NuevoDispositivoRepository {

    override suspend fun registrarDispositivo(dispositivo: NuevoDispositivo): Result<NuevoDispositivo> {
        return try {
            val response = api.postNuevoDispositivo(dispositivo.toDto())
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}