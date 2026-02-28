package com.williamsel.labkeep.features.editardispositivo.data.datasource.api

import com.williamsel.labkeep.features.editardispositivo.data.datasource.models.EditarDispositivoDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface JsonPlaceHolderEditarDispositivoApi {

    @GET("dispositivos/{id}")
    suspend fun getDispositivo(@Path("id") id: Int): EditarDispositivoDto

    @PUT("dispositivos/{id}")
    suspend fun putDispositivo(
        @Path("id") id: Int,
        @Body body: EditarDispositivoDto
    ): EditarDispositivoDto
}