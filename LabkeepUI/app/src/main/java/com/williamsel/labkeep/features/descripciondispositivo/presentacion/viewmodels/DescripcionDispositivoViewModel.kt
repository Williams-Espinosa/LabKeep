package com.williamsel.labkeep.features.descripciondispositivo.presentacion.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.williamsel.labkeep.features.descripciondispositivo.domain.usescases.GetIdDescripcionDispositivoUseCase
import com.williamsel.labkeep.features.descripciondispositivo.presentacion.screens.DescripcionDispositivoUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DescripcionDispositivoViewModel @Inject constructor(
    private val useCase: GetIdDescripcionDispositivoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DescripcionDispositivoUIState())
    val uiState: StateFlow<DescripcionDispositivoUIState> = _uiState.asStateFlow()

    fun cargarDispositivo(id: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            useCase.getDispositivo(id).fold(
                onSuccess = { dispositivo ->
                    _uiState.update { it.copy(isLoading = false, dispositivo = dispositivo) }
                },
                onFailure = { e ->
                    _uiState.update { it.copy(isLoading = false, error = e.message) }
                }
            )
        }
    }

    fun toggleEstado() {
        val dispositivo = _uiState.value.dispositivo ?: return
        val nuevoEstado = if (dispositivo.estado.uppercase() == "DISPONIBLE") "PRESTADO" else "DISPONIBLE"

        viewModelScope.launch {
            _uiState.update { it.copy(isCambiandoEstado = true, error = null) }
            useCase.cambiarEstado(dispositivo.id, nuevoEstado).fold(
                onSuccess = { updated ->
                    _uiState.update { it.copy(isCambiandoEstado = false, dispositivo = updated) }
                },
                onFailure = { e ->
                    _uiState.update { it.copy(isCambiandoEstado = false, error = e.message) }
                }
            )
        }
    }
}