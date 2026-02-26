package com.williamsel.labkeep.core.navigation

import kotlinx.serialization.Serializable


@Serializable object Login
@Serializable object Inventario
@Serializable object NuevoDispositivo


@Serializable data class DescripcionDispositivo(val id: String)