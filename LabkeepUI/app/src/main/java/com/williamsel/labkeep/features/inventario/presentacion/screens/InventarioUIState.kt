package com.williamsel.labkeep.features.inventario.presentacion.screens

import com.williamsel.labkeep.features.inventario.domain.entities.Inventario

data class InventarioUIState(
    val dispositivos: List<Inventario> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val query: String = ""
) {
    val dispositivosFiltrados: List<Inventario>
        get() = if (query.isBlank()) dispositivos
        else dispositivos.filter {
            it.nombre.contains(query, ignoreCase = true) ||
                    it.categoria.contains(query, ignoreCase = true)
        }
}