package com.williamsel.labkeep.features.eliminardispositivo.presentacion.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.williamsel.labkeep.features.eliminardispositivo.domain.usescases.DeleteEliminarDispositivoUseCase
import com.williamsel.labkeep.features.eliminardispositivo.presentacion.screens.EliminarDispositivoUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EliminarDispositivoViewModel @Inject constructor(
    private val useCase: DeleteEliminarDispositivoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EliminarDispositivoUIState())
    val uiState: StateFlow<EliminarDispositivoUIState> = _uiState.asStateFlow()

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

    fun eliminarDispositivo(id: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isEliminando = true, error = null) }
            useCase.eliminar(id).fold(
                onSuccess = {
                    _uiState.update { it.copy(isEliminando = false, eliminadoExitoso = true) }
                },
                onFailure = { e ->
                    _uiState.update { it.copy(isEliminando = false, error = e.message) }
                }
            )
        }
    }
}