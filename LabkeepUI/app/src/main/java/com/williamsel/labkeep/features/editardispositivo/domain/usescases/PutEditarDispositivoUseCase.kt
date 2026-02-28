package com.williamsel.labkeep.features.editardispositivo.domain.usescases

import com.williamsel.labkeep.features.editardispositivo.domain.entities.EditarDispositivo
import com.williamsel.labkeep.features.editardispositivo.domain.repositories.EditarDisposivoRepository
import com.williamsel.labkeep.features.nuevodispositivo.domain.repositories.Categoria
import com.williamsel.labkeep.features.nuevodispositivo.domain.usescases.PostNuevoDispositivoUseCase
import javax.inject.Inject

class PutEditarDispositivoUseCase @Inject constructor(
    private val repository: EditarDisposivoRepository,
    private val nuevoUseCase: PostNuevoDispositivoUseCase
) {
    suspend fun getCategorias(): Result<List<Categoria>> =
        nuevoUseCase.getCategorias()

    suspend fun getDispositivo(id: Int): Result<EditarDispositivo> =
        repository.getDispositivo(id)

    suspend fun editar(dispositivo: EditarDispositivo): Result<EditarDispositivo> =
        repository.editarDispositivo(dispositivo)
}