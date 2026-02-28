package com.williamsel.labkeep.features.nuevodispositivo.presentacion.screens

data class NuevoDispositivoUIState(
    val nombre: String = "",
    val categoria: String = "",
    val isLoading: Boolean = false,
    val guardadoExitoso: Boolean = false,
    val error: String? = null
)