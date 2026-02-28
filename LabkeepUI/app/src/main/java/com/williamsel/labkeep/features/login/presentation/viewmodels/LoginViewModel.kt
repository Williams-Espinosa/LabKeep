package com.williamsel.labkeep.features.login.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.williamsel.labkeep.features.login.domain.usescases.PostLoginUseCase
import com.williamsel.labkeep.features.login.presentation.screens.LoginUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: PostLoginUseCase
) : ViewModel() {

    var state by mutableStateOf(LoginUIState())
        private set

    fun onCorreoChange(correo: String) {
        state = state.copy(correo = correo)
    }

    fun onContrasenaChange(contrasena: String) {
        state = state.copy(contrasena = contrasena)
    }

    fun login() {
        viewModelScope.launch {
            state = state.copy(isLoading = true, errorMessage = null)
            loginUseCase(state.correo, state.contrasena).fold(
                onSuccess = { state = state.copy(isLoading = false, isSuccess = true) },
                onFailure = { state = state.copy(isLoading = false, errorMessage = "Correo o contraseña incorrectos") }
            )
        }
    }
}