package com.williamsel.labkeep.features.nuevodispositivo.presentacion.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    init {
        cargarCategorias()
    }

    private fun cargarCategorias() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingCategorias = true) }
            postNuevoDispositivoUseCase.getCategorias().fold(
                onSuccess = { lista ->
                    _uiState.update { it.copy(isLoadingCategorias = false, categorias = lista) }
                },
                onFailure = { e ->
                    _uiState.update { it.copy(isLoadingCategorias = false, error = e.message) }
                }
            )
        }
    }

    fun onNombreChange(value: String) {
        _uiState.update { it.copy(nombre = value) }
    }

    fun onCategoriaChange(id: Int, nombre: String) {
        _uiState.update { it.copy(categoriaId = id, categoriaNombre = nombre) }
    }

    fun onImagenChange(uri: String?) {
        _uiState.update { it.copy(imagenUri = uri) }
    }

    fun registrarDispositivo() {
        val state = _uiState.value
        if (state.nombre.isBlank() || state.categoriaId == 0) {
            _uiState.update { it.copy(error = "Completa todos los campos") }
            return
        }
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            postNuevoDispositivoUseCase(
                nombre = state.nombre,
                categoriaId = state.categoriaId,
                imagenUri = state.imagenUri
            ).fold(
                onSuccess = {
                    _uiState.update { it.copy(isLoading = false, guardadoExitoso = true) }
                },
                onFailure = { e ->
                    _uiState.update { it.copy(isLoading = false, error = e.message) }
                }
            )
        }
    }
}