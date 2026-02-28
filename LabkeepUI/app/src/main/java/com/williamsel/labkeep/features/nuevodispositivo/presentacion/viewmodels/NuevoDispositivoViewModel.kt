package com.williamsel.labkeep.features.nuevodispositivo.presentacion.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.williamsel.labkeep.features.nuevodispositivo.domain.entities.NuevoDispositivo
import com.williamsel.labkeep.features.nuevodispositivo.domain.usescases.PostNuevoDispositivoUseCase
import com.williamsel.labkeep.features.nuevodispositivo.presentacion.screens.NuevoDispositivoUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NuevoDispositivoViewModel @Inject constructor(
    private val postNuevoDispositivoUseCase: PostNuevoDispositivoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NuevoDispositivoUIState())
    val uiState: StateFlow<NuevoDispositivoUIState> = _uiState.asStateFlow()

    fun onNombreChange(value: String) {
        _uiState.update { it.copy(nombre = value) }
    }

    fun onCategoriaChange(value: String) {
        _uiState.update { it.copy(categoria = value) }
    }

    fun registrarDispositivo() {
        val state = _uiState.value
        if (state.nombre.isBlank() || state.categoria.isBlank()) {
            _uiState.update { it.copy(error = "Completa todos los campos") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val result = postNuevoDispositivoUseCase(
                NuevoDispositivo(nombre = state.nombre, categoria = state.categoria)
            )
            result.fold(
                onSuccess = { _uiState.update { it.copy(isLoading = false, guardadoExitoso = true) } },
                onFailure = { e -> _uiState.update { it.copy(isLoading = false, error = e.message) } }
            )
        }
    }

}