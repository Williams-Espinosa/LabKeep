package com.williamsel.labkeep.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.williamsel.labkeep.features.descripciondispositivo.presentacion.screens.DescripcionDispositivoScreen
import com.williamsel.labkeep.features.editardispositivo.presentacion.screens.EditarDispositivoScreen
import com.williamsel.labkeep.features.eliminardispositivo.presentacion.screens.EliminarDispositivoScreen
import com.williamsel.labkeep.features.inventario.presentacion.screens.InventarioScreen
import com.williamsel.labkeep.features.login.presentation.screens.LoginScreen
import com.williamsel.labkeep.features.nuevodispositivo.presentacion.screens.NuevoDispositivoScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Login
    ) {
        composable<Login> {
            LoginScreen(
                onLoginSuccess = { navController.navigate(Inventario) }
            )
        }

        composable<Inventario> {
            InventarioScreen(
                onDispositivoClick = { id -> navController.navigate(DescripcionDispositivo(id)) },
                onAgregarClick = { navController.navigate(NuevoDispositivo) }
            )
        }

        composable<NuevoDispositivo> {
            NuevoDispositivoScreen(
                onVolver = { navController.popBackStack() },
                onGuardado = { navController.popBackStack() }
            )
        }

        composable<DescripcionDispositivo> { backStackEntry ->
            val route = backStackEntry.toRoute<DescripcionDispositivo>()
            DescripcionDispositivoScreen(
                dispositivoId = route.id,
                onVolver = { navController.popBackStack() },
                onEditar = { navController.navigate(EditarDispositivo(route.id)) },
                onEliminar = { navController.navigate(EliminarDispositivo(route.id)) }
            )
        }

        composable<EditarDispositivo> { backStackEntry ->
            val route = backStackEntry.toRoute<EditarDispositivo>()
            EditarDispositivoScreen(
                dispositivoId = route.id,
                onVolver = { navController.popBackStack() },
                onGuardado = { navController.popBackStack() }
            )
        }

        composable<EliminarDispositivo> { backStackEntry ->
            val route = backStackEntry.toRoute<EliminarDispositivo>()
            EliminarDispositivoScreen(
                dispositivoId = route.id,
                onVolver = { navController.popBackStack() },
                onEliminado = {
                    navController.navigate(Inventario) {
                        popUpTo<Inventario> { inclusive = true }
                    }
                }
            )
        }
    }
}