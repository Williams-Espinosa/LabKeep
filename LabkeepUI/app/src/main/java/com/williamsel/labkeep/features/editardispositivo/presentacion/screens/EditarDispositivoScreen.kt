package com.williamsel.labkeep.features.editardispositivo.presentacion.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.williamsel.labkeep.features.editardispositivo.presentacion.viewmodels.EditarDispositivoViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarDispositivoScreen(
    dispositivoId: Int,
    onVolver: () -> Unit,
    onGuardado: () -> Unit,
    viewModel: EditarDispositivoViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(dispositivoId) {
        viewModel.cargarDispositivo(dispositivoId)
    }

    LaunchedEffect(state.guardadoExitoso) {
        if (state.guardadoExitoso) onGuardado()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            IconButton(onClick = onVolver) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color.White
                )
            }
            Text(
                text = "Editar Equipo",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        when {
            state.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFFFF9800))
                }
            }

            else -> {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = null,
                                tint = Color(0xFFFF9800),
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "DATOS DEL EQUIPO",
                                color = Color(0xFFFF9800),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(start = 6.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "NOMBRE DEL DISPOSITIVO",
                            color = Color(0xFF9E9E9E),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        OutlinedTextField(
                            value = state.nombre,
                            onValueChange = viewModel::onNombreChange,
                            placeholder = { Text("Ej. Centrífuga Médica X1", color = Color(0xFF616161)) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFFFF9800),
                                unfocusedBorderColor = Color(0xFF424242),
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                cursorColor = Color(0xFFFF9800)
                            ),
                            shape = RoundedCornerShape(10.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "CATEGORÍA",
                            color = Color(0xFF9E9E9E),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(6.dp))

                        var expandedCategoria by remember { mutableStateOf(false) }

                        if (state.isLoadingCategorias) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    color = Color(0xFFFF9800),
                                    strokeWidth = 2.dp
                                )
                            }
                        } else {
                            ExposedDropdownMenuBox(
                                expanded = expandedCategoria,
                                onExpandedChange = { expandedCategoria = it }
                            ) {
                                OutlinedTextField(
                                    value = state.categoria,
                                    onValueChange = {},
                                    readOnly = true,
                                    placeholder = { Text("Seleccionar categoría", color = Color(0xFF616161)) },
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCategoria)
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .menuAnchor(),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = Color(0xFFFF9800),
                                        unfocusedBorderColor = Color(0xFF424242),
                                        focusedTextColor = Color.White,
                                        unfocusedTextColor = Color.White
                                    ),
                                    shape = RoundedCornerShape(10.dp)
                                )
                                ExposedDropdownMenu(
                                    expanded = expandedCategoria,
                                    onDismissRequest = { expandedCategoria = false },
                                    modifier = Modifier.background(Color(0xFF2C2C2C))
                                ) {
                                    if (state.categorias.isEmpty()) {
                                        DropdownMenuItem(
                                            text = { Text("Sin categorías", color = Color(0xFF9E9E9E)) },
                                            onClick = { expandedCategoria = false }
                                        )
                                    } else {
                                        state.categorias.forEach { categoria ->
                                            DropdownMenuItem(
                                                text = { Text(categoria.nombre, color = Color.White) },
                                                onClick = {
                                                    viewModel.onCategoriaChange(categoria.id, categoria.nombre)
                                                    expandedCategoria = false
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                if (state.error != null) {
                    Text(
                        text = state.error!!,
                        color = Color(0xFFE53935),
                        fontSize = 13.sp,
                        modifier = Modifier.padding(top = 12.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = viewModel::guardarCambios,
                    enabled = !state.isGuardando,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    if (state.isGuardando) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(22.dp),
                            color = Color.Black,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Guardar Cambios", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                }

                TextButton(
                    onClick = onVolver,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                ) {
                    Text("Cancelar", color = Color(0xFF9E9E9E))
                }
            }
        }
    }
}