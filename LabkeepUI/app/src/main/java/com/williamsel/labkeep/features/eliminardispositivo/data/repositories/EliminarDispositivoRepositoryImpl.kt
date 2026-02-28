package com.williamsel.labkeep.features.eliminardispositivo.data.repositories

import com.williamsel.labkeep.features.eliminardispositivo.data.datasource.api.JsonPlaceHolderEliminarDispositivoApi
import com.williamsel.labkeep.features.eliminardispositivo.data.datasource.mapper.toDomain
import com.williamsel.labkeep.features.eliminardispositivo.domain.entities.EliminarDispositivo
import com.williamsel.labkeep.features.eliminardispositivo.domain.repositories.EliminarDispositivoRepository
import javax.inject.Inject

class EliminarDispositivoRepositoryImpl @Inject constructor(
    private val api: JsonPlaceHolderEliminarDispositivoApi
) : EliminarDispositivoRepository {

    override suspend fun getDispositivo(id: Int): Result<EliminarDispositivo> {
        return try {
            Result.success(api.getDispositivo(id).toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun eliminarDispositivo(id: Int): Result<Unit> {
        return try {
            api.deleteDispositivo(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}