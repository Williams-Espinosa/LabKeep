package com.williamsel.labkeep.core.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.williamsel.labkeep.features.descripciondispositivo.domain.entities.DescripcionDispositivo
import com.williamsel.labkeep.features.inventario.domain.entities.Inventario
import com.williamsel.labkeep.features.login.domain.entities.Login
import com.williamsel.labkeep.features.login.presentation.screens.LoginScreen
import com.williamsel.labkeep.features.login.presentation.viewmodels.LoginViewModel

@Composable
fun NavigationWrapper() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Login
    ) {

        composable<Login> {

            val loginViewModel: LoginViewModel = viewModel()

            LoginScreen(
                viewModel = loginViewModel,
                onLoginSuccess = {
                    navController.navigate(Inventario) {
                        popUpTo(Login) { inclusive = true }
                    }
                }
            )
        }
    }
}