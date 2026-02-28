package com.williamsel.labkeep.features.inventario.presentacion.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.williamsel.labkeep.features.inventario.domain.usescases.GetInventarioUseCase
import com.williamsel.labkeep.features.inventario.presentacion.screens.InventarioUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InventarioViewModel @Inject constructor(
    private val getInventarioUseCase: GetInventarioUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(InventarioUIState())
    val uiState: StateFlow<InventarioUIState> = _uiState.asStateFlow()

    init {
        cargarInventario()
    }

    fun cargarInventario() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            getInventarioUseCase().fold(
                onSuccess = { lista ->
                    _uiState.update { it.copy(isLoading = false, dispositivos = lista) }
                },
                onFailure = { e ->
                    _uiState.update { it.copy(isLoading = false, error = e.message) }
                }
            )
        }
    }

    fun onQueryChange(query: String) {
        _uiState.update { it.copy(query = query) }
    }
}