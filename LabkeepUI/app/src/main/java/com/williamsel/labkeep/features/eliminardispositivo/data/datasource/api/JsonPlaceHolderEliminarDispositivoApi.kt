package com.williamsel.labkeep.features.eliminardispositivo.data.datasource.api

import com.williamsel.labkeep.features.eliminardispositivo.data.datasource.models.EliminarDispositivoDto
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface JsonPlaceHolderEliminarDispositivoApi {

    @GET("dispositivos/{id}")
    suspend fun getDispositivo(@Path("id") id: Int): EliminarDispositivoDto

    @DELETE("dispositivos/{id}")
    suspend fun deleteDispositivo(@Path("id") id: Int)
}