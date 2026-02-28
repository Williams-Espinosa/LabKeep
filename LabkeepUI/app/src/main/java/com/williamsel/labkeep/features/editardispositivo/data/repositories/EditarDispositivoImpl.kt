package com.williamsel.labkeep.features.editardispositivo.data.repositories

import com.williamsel.labkeep.features.editardispositivo.data.datasource.api.JsonPlaceHolderEditarDispositivoApi
import com.williamsel.labkeep.features.editardispositivo.data.datasource.mapper.toDomain
import com.williamsel.labkeep.features.editardispositivo.data.datasource.mapper.toDto
import com.williamsel.labkeep.features.editardispositivo.domain.entities.EditarDispositivo
import com.williamsel.labkeep.features.editardispositivo.domain.repositories.EditarDisposivoRepository
import javax.inject.Inject

class EditarDispositivoImpl @Inject constructor(
    private val api: JsonPlaceHolderEditarDispositivoApi
) : EditarDisposivoRepository {

    override suspend fun getDispositivo(id: Int): Result<EditarDispositivo> {
        return try {
            Result.success(api.getDispositivo(id).toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun editarDispositivo(dispositivo: EditarDispositivo): Result<EditarDispositivo> {
        return try {
            val response = api.putDispositivo(dispositivo.id, dispositivo.toDto())
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}