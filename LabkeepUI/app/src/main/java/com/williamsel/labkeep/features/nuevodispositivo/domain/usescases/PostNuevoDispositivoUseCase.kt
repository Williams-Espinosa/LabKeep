package com.williamsel.labkeep.features.nuevodispositivo.domain.usescases

import com.williamsel.labkeep.features.nuevodispositivo.domain.entities.NuevoDispositivo
import com.williamsel.labkeep.features.nuevodispositivo.domain.repositories.Categoria
import com.williamsel.labkeep.features.nuevodispositivo.domain.repositories.NuevoDispositivoRepository
import javax.inject.Inject

class PostNuevoDispositivoUseCase @Inject constructor(
    private val repository: NuevoDispositivoRepository
) {
    suspend fun getCategorias(): Result<List<Categoria>> =
        repository.getCategorias()

    suspend operator fun invoke(
        nombre: String,
        categoriaId: Int,
        imagenUri: String? = null
    ): Result<NuevoDispositivo> =
        repository.registrarDispositivo(nombre, categoriaId, imagenUri)
}