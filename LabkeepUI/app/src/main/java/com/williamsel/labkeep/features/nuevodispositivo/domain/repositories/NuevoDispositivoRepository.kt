package com.williamsel.labkeep.features.nuevodispositivo.domain.repositories

import com.williamsel.labkeep.features.nuevodispositivo.domain.entities.NuevoDispositivo

data class Categoria(val id: Int, val nombre: String)

interface NuevoDispositivoRepository {
    suspend fun getCategorias(): Result<List<Categoria>>
    suspend fun registrarDispositivo(
        nombre: String,
        categoriaId: Int,
        imagenUri: String?
    ): Result<NuevoDispositivo>
}