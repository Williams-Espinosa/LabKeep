package com.williamsel.labkeep.features.descripciondispositivo.data.datasource.api

import com.williamsel.labkeep.features.descripciondispositivo.data.datasource.models.DescripcionDispositivoDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface JsonPlaceHolderDescripcionDispositivoApi {

    @GET("dispositivos/{id}")
    suspend fun getDispositivo(@Path("id") id: Int): DescripcionDispositivoDto

    @PATCH("dispositivos/{id}/estado")
    suspend fun cambiarEstado(
        @Path("id") id: Int,
        @Body body: Map<String, String>
    ): DescripcionDispositivoDto
}