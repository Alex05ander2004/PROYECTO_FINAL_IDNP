package com.example.proyectofinal.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.proyectofinal.ui.components.BottomNavigationBar
import com.example.proyectofinal.ui.screens.agenda.AgendaScreen
import com.example.proyectofinal.ui.screens.create.CreateEventScreen // 👈 IMPORTANTE
import com.example.proyectofinal.ui.screens.eventdetail.EventDetailScreen
import com.example.proyectofinal.ui.screens.explore.ExploreScreen
import com.example.proyectofinal.ui.screens.notifications.NotificationsScreen
// import com.example.proyectofinal.ui.screens.profile.ProfileScreen <-- YA NO SE USA
import com.example.proyectofinal.ui.screens.settings.SettingsScreen
import com.example.proyectofinal.ui.screens.edit.EditEventScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            // 👇 LISTA CORREGIDA: Agregamos "settings" y quitamos "perfil"
            val showBottomBar = currentRoute in listOf(
                "explorar",
                "crear",
                "agenda",
                "notificaciones",
                "settings" // Coincide con la ruta del NavHost
            )

            if (showBottomBar) {
                BottomNavigationBar(
                    navController = navController,
                    selected = currentRoute ?: "explorar"
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "explorar",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("explorar") {
                ExploreScreen(navController = navController)
            }

            // 👇 AGREGADO: La pantalla para crear eventos
            composable("crear") {
                CreateEventScreen(navController = navController)
            }

            composable("agenda") {
                AgendaScreen(navController = navController)
            }
            composable("notificaciones") {
                NotificationsScreen(navController = navController)
            }

            // 👇 ELIMINADO: composable("perfil") ya no existe.

            composable("settings") {
                SettingsScreen(navController = navController)
            }

            composable(
                route = "detalle_evento/{eventId}",
                arguments = listOf(navArgument("eventId") { type = NavType.StringType })
            ) {
                // EventDetailScreen ya no recibe argumentos manuales, Hilt los maneja
                EventDetailScreen(navController = navController)
            }
            // 👇 NUEVA RUTA: Editar Evento
            composable(
                route = "editar_evento/{eventId}",
                arguments = listOf(navArgument("eventId") { type = NavType.StringType })
            ) {
                // Hilt inyectará el eventId en el EditEventViewModel automáticamente
                EditEventScreen(navController = navController)
            }
        }
    }
}