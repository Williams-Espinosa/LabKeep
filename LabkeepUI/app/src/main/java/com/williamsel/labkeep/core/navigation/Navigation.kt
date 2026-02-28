package com.williamsel.labkeep.core.navigation

import kotlinx.serialization.Serializable

@Serializable object Login
@Serializable object Inventario
@Serializable object NuevoDispositivo
@Serializable data class DescripcionDispositivo(val id: Int)
@Serializable data class EditarDispositivo(val id: Int)
@Serializable data class EliminarDispositivo(val id: Int)