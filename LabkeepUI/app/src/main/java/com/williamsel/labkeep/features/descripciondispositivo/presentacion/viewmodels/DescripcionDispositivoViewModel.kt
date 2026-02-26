package com.williamsel.labkeep.features.descripciondispositivo.presentacion.viewmodels

import jakarta.inject.Inject

class DescripcionDispositivoViewModel @Inject constructor() {
}

data class DescripcionDispositivo(val id: String)

sealed class Navigation
