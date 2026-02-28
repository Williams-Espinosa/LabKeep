package com.williamsel.labkeep.features.editardispositivo.presentacion.screens

data class EditarDispositivoUIState(
    val id: Int = 0,
    val nombre: String = "",
    val categoria: String = "",
    val estado: String = "",
    val isLoading: Boolean = false,
    val isGuardando: Boolean = false,
    val guardadoExitoso: Boolean = false,
    val error: String? = null
)