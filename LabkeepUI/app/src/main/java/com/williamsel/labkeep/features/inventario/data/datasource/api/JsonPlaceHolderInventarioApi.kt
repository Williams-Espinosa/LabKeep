package com.williamsel.labkeep.features.inventario.data.datasource.api

import com.williamsel.labkeep.features.inventario.data.datasource.models.InventarioDto
import retrofit2.http.GET

interface JsonPlaceHolderInventarioApi {
    @GET("dispositivos")
    suspend fun getInventario(): List<InventarioDto>
}