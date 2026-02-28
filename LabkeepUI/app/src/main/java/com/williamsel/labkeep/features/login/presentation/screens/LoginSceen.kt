package com.williamsel.labkeep.features.login.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.williamsel.labkeep.R
import com.williamsel.labkeep.features.login.presentation.viewmodels.LoginViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state = viewModel.state
    var contrasenaVisible by remember { mutableStateOf(false) }

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) onLoginSuccess()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.applogo),
                contentDescription = "LabKeep Logo",
                modifier = Modifier
                    .size(180.dp)
                    .padding(bottom = 16.dp)
                    .clip(RoundedCornerShape(0.dp))
            )

            Text(
                text = "Inicia sesión",
                fontSize = 26.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(bottom = 40.dp)
            )

            OutlinedTextField(
                value = state.correo,
                onValueChange = viewModel::onCorreoChange,
                placeholder = { Text("Correo electrónico", color = Color(0xFF616161)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = null,
                        tint = Color(0xFF616161)
                    )
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color(0xFFE53935),
                    unfocusedContainerColor = Color(0xFF1E1E1E),
                    focusedContainerColor = Color(0xFF1E1E1E)
                ),
                shape = RoundedCornerShape(14.dp)
            )

            // Campo contraseña con visibilidad
            OutlinedTextField(
                value = state.contrasena,
                onValueChange = viewModel::onContrasenaChange,
                placeholder = { Text("Contraseña", color = Color(0xFF616161)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = Color(0xFF616161)
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { contrasenaVisible = !contrasenaVisible }) {
                        Icon(
                            imageVector = if (contrasenaVisible)
                                Icons.Default.Visibility
                            else
                                Icons.Default.VisibilityOff,
                            contentDescription = if (contrasenaVisible)
                                "Ocultar contraseña"
                            else
                                "Ver contraseña",
                            tint = Color(0xFF616161)
                        )
                    }
                },
                visualTransformation = if (contrasenaVisible)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color(0xFFE53935),
                    unfocusedContainerColor = Color(0xFF1E1E1E),
                    focusedContainerColor = Color(0xFF1E1E1E)
                ),
                shape = RoundedCornerShape(14.dp)
            )

            if (state.errorMessage != null) {
                Text(
                    text = state.errorMessage,
                    color = Color(0xFFE53935),
                    fontSize = 13.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = viewModel::login,
                enabled = !state.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        color = Color.Black,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "Continuar",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "›",
                        color = Color.Black,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}