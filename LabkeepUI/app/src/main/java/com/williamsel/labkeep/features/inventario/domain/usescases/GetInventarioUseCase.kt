package com.williamsel.labkeep.features.inventario.domain.usescases

import com.williamsel.labkeep.features.inventario.domain.entities.Inventario
import com.williamsel.labkeep.features.inventario.domain.repositories.InventarioRepository
import javax.inject.Inject

class GetInventarioUseCase @Inject constructor(
    private val repository: InventarioRepository
) {
    suspend operator fun invoke(): Result<List<Inventario>> {
        return repository.getInventario()
    }
}