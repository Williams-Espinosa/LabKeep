package com.williamsel.labkeep.features.descripciondispositivo.data.repositories

import com.williamsel.labkeep.features.descripciondispositivo.data.datasource.api.JsonPlaceHolderDescripcionDispositivoApi
import com.williamsel.labkeep.features.descripciondispositivo.data.datasource.mapper.toDomain
import com.williamsel.labkeep.features.descripciondispositivo.domain.entities.DescripcionDispositivo
import com.williamsel.labkeep.features.descripciondispositivo.domain.repositories.DescripcionDispositivoRepository
import javax.inject.Inject

class DescripcionDispositivoImpl @Inject constructor(
    private val api: JsonPlaceHolderDescripcionDispositivoApi
) : DescripcionDispositivoRepository {

    override suspend fun getDispositivo(id: Int): Result<DescripcionDispositivo> {
        return try {
            Result.success(api.getDispositivo(id).toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun cambiarEstado(id: Int, nuevoEstado: String): Result<DescripcionDispositivo> {
        return try {
            val response = api.cambiarEstado(id, mapOf("estado" to nuevoEstado))
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}