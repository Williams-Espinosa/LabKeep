package com.williamsel.labkeep.features.login.presentation.screens

data class LoginUIState(
    val correo: String = "",
    val contrasena: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false
)