package com.williamsel.labkeep.features.nuevodispositivo.presentacion.screens

import com.williamsel.labkeep.features.nuevodispositivo.domain.repositories.Categoria

data class NuevoDispositivoUIState(
    val nombre: String = "",
    val categoriaId: Int = 0,
    val categoriaNombre: String = "",
    val categorias: List<Categoria> = emptyList(),
    val imagenUri: String? = null,
    val isLoadingCategorias: Boolean = false,
    val isLoading: Boolean = false,
    val guardadoExitoso: Boolean = false,
    val error: String? = null
)