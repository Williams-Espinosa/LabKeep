package com.williamsel.labkeep.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.williamsel.labkeep.features.descripciondispositivo.presentacion.screens.DescripcionDispositivoScreen
import com.williamsel.labkeep.features.editardispositivo.presentacion.screens.EditarDispositivoScreen
import com.williamsel.labkeep.features.eliminardispositivo.presentacion.screens.EliminarDispositivoScreen
import com.williamsel.labkeep.features.inventario.presentacion.screens.InventarioScreen
import com.williamsel.labkeep.features.login.presentation.screens.LoginScreen
import com.williamsel.labkeep.features.nuevodispositivo.presentacion.screens.NuevoDispositivoScreen

object Routes {
    const val LOGIN = "login"
    const val INVENTARIO = "inventario"
    const val NUEVO_DISPOSITIVO = "nuevodispositivo"
    const val DESCRIPCION_DISPOSITIVO = "descripciondispositivo/{id}"
    const val EDITAR_DISPOSITIVO = "editardispositivo/{id}"
    const val ELIMINAR_DISPOSITIVO = "eliminardispositivo/{id}"

    fun descripcionDispositivo(id: Int) = "descripciondispositivo/$id"
    fun editarDispositivo(id: Int) = "editardispositivo/$id"
    fun eliminarDispositivo(id: Int) = "eliminardispositivo/$id"
}

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = { navController.navigate(Routes.INVENTARIO) }
            )
        }

        composable(Routes.INVENTARIO) {
            InventarioScreen(
                onDispositivoClick = { id -> navController.navigate(Routes.descripcionDispositivo(id)) },
                onAgregarClick = { navController.navigate(Routes.NUEVO_DISPOSITIVO) }
            )
        }

        composable(Routes.NUEVO_DISPOSITIVO) {
            NuevoDispositivoScreen(
                onVolver = { navController.popBackStack() },
                onGuardado = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.DESCRIPCION_DISPOSITIVO,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            DescripcionDispositivoScreen(
                dispositivoId = id,
                onVolver = { navController.popBackStack() },
                onEditar = { navController.navigate(Routes.editarDispositivo(id)) },
                onEliminar = { navController.navigate(Routes.eliminarDispositivo(id)) }
            )
        }

        composable(
            route = Routes.EDITAR_DISPOSITIVO,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            EditarDispositivoScreen(
                dispositivoId = id,
                onVolver = { navController.popBackStack() },
                onGuardado = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.ELIMINAR_DISPOSITIVO,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            EliminarDispositivoScreen(
                dispositivoId = id,
                onVolver = { navController.popBackStack() },
                onEliminado = {
                    navController.navigate(Routes.INVENTARIO) {
                        popUpTo(Routes.INVENTARIO) { inclusive = true }
                    }
                }
            )
        }
    }
}