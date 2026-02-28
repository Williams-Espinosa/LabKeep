package com.williamsel.labkeep.features.nuevodispositivo.presentacion.screens

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.williamsel.labkeep.features.nuevodispositivo.presentacion.viewmodels.NuevoDispositivoViewModel
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevoDispositivoScreen(
    onVolver: () -> Unit,
    onGuardado: () -> Unit,
    viewModel: NuevoDispositivoViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    var expandedCategoria by remember { mutableStateOf(false) }
    var mostrarDialogoImagen by remember { mutableStateOf(false) }

    val cameraImageUri: Uri = remember {
        val file = File(context.cacheDir, "temp_camera_photo.jpg").also {
            if (!it.exists()) it.createNewFile()
        }
        FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    }

    val camaraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { exitoso ->
        if (exitoso) {
            viewModel.onImagenChange(cameraImageUri.toString())
        }
    }

    val galeriaLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.onImagenChange(it.toString()) }
    }

    val permisoCamaraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedido ->
        if (concedido) camaraLauncher.launch(cameraImageUri)
    }

    val permisoGaleriaLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedido ->
        if (concedido) galeriaLauncher.launch("image/*")
    }


    fun abrirCamara() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED
        ) {
            camaraLauncher.launch(cameraImageUri)
        } else {
            permisoCamaraLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    fun abrirGaleria() {
        val permiso = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            Manifest.permission.READ_MEDIA_IMAGES
        else
            Manifest.permission.READ_EXTERNAL_STORAGE

        if (ContextCompat.checkSelfPermission(context, permiso) == PackageManager.PERMISSION_GRANTED) {
            galeriaLauncher.launch("image/*")
        } else {
            permisoGaleriaLauncher.launch(permiso)
        }
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
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color.White
                )
            }
            Text(
                text = "Nuevo Equipo",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

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
                    "NOMBRE DEL DISPOSITIVO",
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
                    "CATEGORÍA",
                    color = Color(0xFF9E9E9E),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(6.dp))

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
                            value = state.categoriaNombre,
                            onValueChange = {},
                            readOnly = true,
                            placeholder = {
                                Text("Seleccionar categoría", color = Color(0xFF616161))
                            },
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

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "FOTO DEL EQUIPO",
                    color = Color(0xFF9E9E9E),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))

                if (state.imagenUri != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                            .border(1.dp, Color(0xFF424242), RoundedCornerShape(10.dp))
                            .clickable { mostrarDialogoImagen = true }
                    ) {
                        AsyncImage(
                            model = Uri.parse(state.imagenUri),
                            contentDescription = "Foto del equipo",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color(0xFF2C2C2C), RoundedCornerShape(10.dp))
                        )
                        Text(
                            text = "Toca para cambiar",
                            color = Color.White,
                            fontSize = 12.sp,
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .background(Color(0x88000000))
                                .padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = { abrirCamara() },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                            border = BorderStroke(1.dp, Color(0xFF424242))
                        ) {
                            Icon(
                                Icons.Default.CameraAlt,
                                contentDescription = null,
                                tint = Color(0xFFFF9800),
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Cámara", fontSize = 13.sp)
                        }

                        OutlinedButton(
                            onClick = { abrirGaleria() },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                            border = BorderStroke(1.dp, Color(0xFF424242))
                        ) {
                            Icon(
                                Icons.Default.Image,
                                contentDescription = null,
                                tint = Color(0xFFFF9800),
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Galería", fontSize = 13.sp)
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
            onClick = viewModel::registrarDispositivo,
            enabled = !state.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(22.dp),
                    color = Color.Black,
                    strokeWidth = 2.dp
                )
            } else {
                Text("Registrar Equipo", color = Color.Black, fontWeight = FontWeight.Bold)
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
    if (mostrarDialogoImagen) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoImagen = false },
            containerColor = Color(0xFF1E1E1E),
            title = { Text("Cambiar foto", color = Color.White) },
            text = { Text("¿Cómo quieres seleccionar la imagen?", color = Color(0xFF9E9E9E)) },
            confirmButton = {
                TextButton(onClick = {
                    mostrarDialogoImagen = false
                    abrirCamara()
                }) {
                    Icon(Icons.Default.CameraAlt, null, tint = Color(0xFFE53935))
                    Spacer(Modifier.width(4.dp))
                    Text("Cámara", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    mostrarDialogoImagen = false
                    abrirGaleria()
                }) {
                    Icon(Icons.Default.Image, null, tint = Color(0xFFE53935))
                    Spacer(Modifier.width(4.dp))
                    Text("Galería", color = Color.White)
                }
            }
        )
    }
}