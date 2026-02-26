package com.williamsel.labkeep.features.login.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.williamsel.labkeep.features.login.domain.usescases.PostLoginUseCase
import com.williamsel.labkeep.features.login.presentation.screens.LoginUIState
import jakarta.inject.Inject
import kotlinx.coroutines.launch

class LoginViewModel @Inject constructor(
    private val loginUseCase: PostLoginUseCase
) : ViewModel() {

    var state by mutableStateOf(LoginUIState())
        private set

    fun onEmailChange(email: String) {
        state = state.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        state = state.copy(password = password)
    }

    fun login() {
        viewModelScope.launch {

            state = state.copy(
                isLoading = true,
                errorMessage = null
            )
            try {
                loginUseCase(state.email, state.password)

                state = state.copy(
                    isLoading = false,
                    isSuccess = true
                )
            } catch (e: Exception) {
                state = state.copy(
                    isLoading = false,
                    errorMessage = "Credenciales incorrectas"
                )
            }
        }
    }
}