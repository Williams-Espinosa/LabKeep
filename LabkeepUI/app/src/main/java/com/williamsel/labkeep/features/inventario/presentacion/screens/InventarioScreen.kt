package com.williamsel.labkeep.features.inventario.presentacion.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import coil.compose.AsyncImage
import com.williamsel.labkeep.features.inventario.domain.entities.Inventario
import com.williamsel.labkeep.features.inventario.presentacion.viewmodels.InventarioViewModel

@Composable
fun InventarioScreen(
    onDispositivoClick: (Int) -> Unit,
    onAgregarClick: () -> Unit,
    viewModel: InventarioViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.cargarInventario()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Inventario",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = state.query,
                onValueChange = viewModel::onQueryChange,
                placeholder = { Text("Buscar equipo o categoría...", color = Color(0xFF616161)) },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = null,
                        tint = Color(0xFFFF9800)
                    )
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFF9800),
                    unfocusedBorderColor = Color(0xFF2C2C2C),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color(0xFFFF9800),
                    unfocusedContainerColor = Color(0xFF1E1E1E),
                    focusedContainerColor = Color(0xFF1E1E1E)
                ),
                shape = RoundedCornerShape(12.dp)
            )

            when {
                state.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color(0xFFFF9800))
                    }
                }

                state.error != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = state.error!!,
                                color = Color(0xFFE53935),
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            TextButton(onClick = viewModel::cargarInventario) {
                                Text("Reintentar", color = Color.White)
                            }
                        }
                    }
                }

                state.dispositivosFiltrados.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No se encontraron equipos",
                            color = Color(0xFFFF9800),
                            fontSize = 14.sp
                        )
                    }
                }

                else -> {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        items(state.dispositivosFiltrados) { dispositivo ->
                            DispositivoItem(
                                dispositivo = dispositivo,
                                onClick = { onDispositivoClick(dispositivo.id) }
                            )
                        }
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = onAgregarClick,
            containerColor = Color.White,
            contentColor = Color.Black,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Agregar equipo")
        }
    }
}

@Composable
private fun DispositivoItem(
    dispositivo: Inventario,
    onClick: () -> Unit
) {
    val estadoColor = when (dispositivo.estado.uppercase()) {
        "DISPONIBLE" -> Color(0xFF4CAF50)
        "PRESTADO"   -> Color(0xFFE53935)
        else         -> Color(0xFF9E9E9E)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(Color(0xFF2C2C2C), RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (!dispositivo.imagenUrl.isNullOrBlank()) {
                    AsyncImage(
                        model = dispositivo.imagenUrl,
                        contentDescription = dispositivo.nombre,
                        contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFF2C2C2C), RoundedCornerShape(10.dp))
                    )
                } else {
                    Text("📷", fontSize = 20.sp)
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = dispositivo.categoria.uppercase(),
                    color = Color(0xFF9E9E9E),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = dispositivo.nombre,
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Surface(
                shape = RoundedCornerShape(20.dp),
                color = estadoColor.copy(alpha = 0.15f)
            ) {
                Text(
                    text = dispositivo.estado,
                    color = estadoColor,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text("›", color = Color(0xFF616161), fontSize = 20.sp)
        }
    }
}