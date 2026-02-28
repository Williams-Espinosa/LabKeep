package com.williamsel.labkeep.features.inventario.data.repositories

import com.williamsel.labkeep.features.inventario.data.datasource.api.JsonPlaceHolderInventarioApi
import com.williamsel.labkeep.features.inventario.data.datasource.mapper.toDomain
import com.williamsel.labkeep.features.inventario.domain.entities.Inventario
import com.williamsel.labkeep.features.inventario.domain.repositories.InventarioRepository
import javax.inject.Inject

class InventarioRepositoryImpl @Inject constructor(
    private val api: JsonPlaceHolderInventarioApi
) : InventarioRepository {

    override suspend fun getInventario(): Result<List<Inventario>> {
        return try {
            val response = api.getInventario()
            Result.success(response.map { it.toDomain() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}