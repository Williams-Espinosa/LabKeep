package com.williamsel.labkeep.features.descripciondispositivo.presentacion.screens

import com.williamsel.labkeep.features.descripciondispositivo.domain.entities.DescripcionDispositivo

data class DescripcionDispositivoUIState(
    val dispositivo: DescripcionDispositivo? = null,
    val isLoading: Boolean = false,
    val isCambiandoEstado: Boolean = false,
    val error: String? = null
)