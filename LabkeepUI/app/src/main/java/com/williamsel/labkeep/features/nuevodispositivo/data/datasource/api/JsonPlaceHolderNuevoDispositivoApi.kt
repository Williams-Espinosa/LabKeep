package com.williamsel.labkeep.features.nuevodispositivo.data.datasource.api

import com.williamsel.labkeep.features.nuevodispositivo.data.datasource.models.NuevoDispositivoDto
import retrofit2.http.Body
import retrofit2.http.POST

interface JsonPlaceHolderNuevoDispositivoApi {
    @POST("dispositivos")
    suspend fun postNuevoDispositivo(@Body body: NuevoDispositivoDto): NuevoDispositivoDto
}