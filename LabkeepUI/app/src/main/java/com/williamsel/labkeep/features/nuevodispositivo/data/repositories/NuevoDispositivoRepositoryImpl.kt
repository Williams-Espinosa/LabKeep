package com.williamsel.labkeep.features.nuevodispositivo.data.repositories

import android.content.Context
import android.net.Uri
import com.williamsel.labkeep.features.nuevodispositivo.data.datasource.api.JsonPlaceHolderNuevoDispositivoApi
import com.williamsel.labkeep.features.nuevodispositivo.data.datasource.mapper.toDomain
import com.williamsel.labkeep.features.nuevodispositivo.domain.entities.NuevoDispositivo
import com.williamsel.labkeep.features.nuevodispositivo.domain.repositories.Categoria
import com.williamsel.labkeep.features.nuevodispositivo.domain.repositories.NuevoDispositivoRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class NuevoDispositivoRepositoryImpl @Inject constructor(
    private val api: JsonPlaceHolderNuevoDispositivoApi,
    @ApplicationContext private val context: Context
) : NuevoDispositivoRepository {

    override suspend fun getCategorias(): Result<List<Categoria>> {
        return try {
            val response = api.getCategorias()
            Result.success(response.map { Categoria(it.id, it.nombre) })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun registrarDispositivo(
        nombre: String,
        categoriaId: Int,
        imagenUri: String?
    ): Result<NuevoDispositivo> {
        return try {
            val nombreBody = nombre.toRequestBody("text/plain".toMediaTypeOrNull())
            val categoriaBody = categoriaId.toString().toRequestBody("text/plain".toMediaTypeOrNull())

            val imagenPart = imagenUri?.let { uriStr ->
                val uri = Uri.parse(uriStr)
                val inputStream = context.contentResolver.openInputStream(uri)
                val tempFile = File.createTempFile("img", ".jpg", context.cacheDir)
                FileOutputStream(tempFile).use { output -> inputStream?.copyTo(output) }
                val requestFile = tempFile.asRequestBody("image/*".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("imagen", tempFile.name, requestFile)
            }

            val response = api.postNuevoDispositivo(nombreBody, categoriaBody, imagenPart)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}