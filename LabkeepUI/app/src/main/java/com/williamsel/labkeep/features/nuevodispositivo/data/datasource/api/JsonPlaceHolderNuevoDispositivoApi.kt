package com.williamsel.labkeep.features.nuevodispositivo.data.datasource.api

import com.williamsel.labkeep.features.nuevodispositivo.data.datasource.models.CategoriaDto
import com.williamsel.labkeep.features.nuevodispositivo.data.datasource.models.NuevoDispositivoDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface JsonPlaceHolderNuevoDispositivoApi {

    @GET("categorias")
    suspend fun getCategorias(): List<CategoriaDto>

    @Multipart
    @POST("dispositivos")
    suspend fun postNuevoDispositivo(
        @Part("nombre") nombre: RequestBody,
        @Part("categoria_id") categoriaId: RequestBody,
        @Part imagen: MultipartBody.Part? = null
    ): NuevoDispositivoDto
}