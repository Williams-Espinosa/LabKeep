package com.williamsel.labkeep.features.eliminardispositivo.presentacion.screens

import com.williamsel.labkeep.features.eliminardispositivo.domain.entities.EliminarDispositivo

data class EliminarDispositivoUIState(
    val dispositivo: EliminarDispositivo? = null,
    val isLoading: Boolean = false,
    val isEliminando: Boolean = false,
    val eliminadoExitoso: Boolean = false,
    val error: String? = null
)