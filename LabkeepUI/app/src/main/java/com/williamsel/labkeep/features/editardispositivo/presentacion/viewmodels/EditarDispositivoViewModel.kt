package com.williamsel.labkeep.features.editardispositivo.presentacion.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.williamsel.labkeep.features.editardispositivo.domain.entities.EditarDispositivo
import com.williamsel.labkeep.features.editardispositivo.domain.usescases.PutEditarDispositivoUseCase
import com.williamsel.labkeep.features.editardispositivo.presentacion.screens.EditarDispositivoUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditarDispositivoViewModel @Inject constructor(
    private val useCase: PutEditarDispositivoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditarDispositivoUIState())
    val uiState: StateFlow<EditarDispositivoUIState> = _uiState.asStateFlow()

    init {
        cargarCategorias()
    }

    private fun cargarCategorias() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingCategorias = true) }
            useCase.getCategorias().fold(
                onSuccess = { lista ->
                    _uiState.update { it.copy(isLoadingCategorias = false, categorias = lista) }
                },
                onFailure = { e ->
                    _uiState.update { it.copy(isLoadingCategorias = false, error = e.message) }
                }
            )
        }
    }

    fun cargarDispositivo(id: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            useCase.getDispositivo(id).fold(
                onSuccess = { dispositivo ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            id = dispositivo.id,
                            nombre = dispositivo.nombre,
                            categoria = dispositivo.categoria,
                            categoriaId = dispositivo.categoriaId,
                            estado = dispositivo.estado,
                            imagenUrl = dispositivo.imagenUrl,
                            fechaCreacion = dispositivo.fechaCreacion
                        )
                    }
                },
                onFailure = { e ->
                    _uiState.update { it.copy(isLoading = false, error = e.message) }
                }
            )
        }
    }

    fun onNombreChange(value: String) {
        _uiState.update { it.copy(nombre = value) }
    }

    fun onCategoriaChange(id: Int, nombre: String) {
        _uiState.update { it.copy(categoriaId = id, categoria = nombre) }
    }

    fun guardarCambios() {
        val state = _uiState.value
        if (state.nombre.isBlank() || state.categoriaId == 0) {
            _uiState.update { it.copy(error = "Completa todos los campos") }
            return
        }
        viewModelScope.launch {
            _uiState.update { it.copy(isGuardando = true, error = null) }
            useCase.editar(
                EditarDispositivo(
                    id = state.id,
                    nombre = state.nombre,
                    categoria = state.categoria,
                    categoriaId = state.categoriaId,
                    estado = state.estado,
                    imagenUrl = state.imagenUrl,
                    fechaCreacion = state.fechaCreacion
                )
            ).fold(
                onSuccess = {
                    _uiState.update { it.copy(isGuardando = false, guardadoExitoso = true) }
                },
                onFailure = { e ->
                    _uiState.update { it.copy(isGuardando = false, error = e.message) }
                }
            )
        }
    }
}