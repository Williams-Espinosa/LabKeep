package com.williamsel.labkeep.features.eliminardispositivo.presentacion.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.layout.ContentScale
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.williamsel.labkeep.features.eliminardispositivo.presentacion.viewmodels.EliminarDispositivoViewModel

@Composable
fun EliminarDispositivoScreen(
    dispositivoId: Int,
    onVolver: () -> Unit,
    onEliminado: () -> Unit,
    viewModel: EliminarDispositivoViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(dispositivoId) {
        viewModel.cargarDispositivo(dispositivoId)
    }

    LaunchedEffect(state.eliminadoExitoso) {
        if (state.eliminadoExitoso) onEliminado()
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
                text = "Eliminar Equipo",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        when {
            state.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFFE53935))
                }
            }

            state.error != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = state.error!!, color = Color(0xFFE53935), fontSize = 14.sp)
                }
            }

            state.dispositivo != null -> {
                val dispositivo = state.dispositivo!!


                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = Color(0xFFE53935),
                        modifier = Modifier.size(64.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))


                Text(
                    text = "¿Eliminar este equipo?",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))


                Text(
                    text = "Estás a punto de borrar \"${dispositivo.nombre}\" de LabKeep. Esta acción no se puede deshacer y se perderá todo el historial.",
                    color = Color(0xFF9E9E9E),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))


                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(Color(0xFF2C2C2C), RoundedCornerShape(10.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            if (!dispositivo.imagenUrl.isNullOrBlank()) {
                                AsyncImage(
                                    model = dispositivo.imagenUrl,
                                    contentDescription = dispositivo.nombre,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color(0xFF2C2C2C), RoundedCornerShape(10.dp))
                                )
                            } else {
                                Text("📷", fontSize = 20.sp)
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = dispositivo.nombre,
                                color = Color.White,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "${dispositivo.categoria} • ${dispositivo.estado}",
                                color = Color(0xFF9E9E9E),
                                fontSize = 13.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))


                if (state.error != null) {
                    Text(
                        text = state.error!!,
                        color = Color(0xFFE53935),
                        fontSize = 13.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                Button(
                    onClick = { viewModel.eliminarDispositivo(dispositivoId) },
                    enabled = !state.isEliminando,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935))
                ) {
                    if (state.isEliminando) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(22.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            text = "Eliminar permanentemente",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
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