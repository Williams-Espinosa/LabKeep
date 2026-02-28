package com.williamsel.labkeep.features.editardispositivo.presentacion.screens

import com.williamsel.labkeep.features.nuevodispositivo.domain.repositories.Categoria

data class EditarDispositivoUIState(
    val id: Int = 0,
    val nombre: String = "",
    val categoria: String = "",
    val categoriaId: Int = 0,
    val estado: String = "",
    val imagenUrl: String? = null,
    val fechaCreacion: String? = null,
    val categorias: List<Categoria> = emptyList(),
    val isLoadingCategorias: Boolean = false,
    val isLoading: Boolean = false,
    val isGuardando: Boolean = false,
    val guardadoExitoso: Boolean = false,
    val error: String? = null
)