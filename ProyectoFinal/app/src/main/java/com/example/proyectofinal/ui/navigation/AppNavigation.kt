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
import com.example.proyectofinal.ui.screens.detail.EventDetailScreen
import com.example.proyectofinal.ui.screens.explore.ExploreScreen
import com.example.proyectofinal.ui.screens.notifications.NotificationsScreen
import com.example.proyectofinal.ui.screens.profile.ProfileScreen
import com.example.proyectofinal.ui.screens.settings.SettingsScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            // Solo muestra la barra de navegación en las pantallas principales
            if (currentRoute in listOf("explorar", "agenda", "notificaciones", "perfil")) {
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
            composable("agenda") {
                AgendaScreen(navController = navController)
            }
            composable("notificaciones") {
                NotificationsScreen(navController = navController)
            }
            composable("perfil") {
                ProfileScreen(navController = navController)
            }
            composable("settings") {
                SettingsScreen(navController = navController)
            }
            composable(
                route = "detalle_evento/{eventId}",
                arguments = listOf(navArgument("eventId") { type = NavType.StringType })
            ) { backStackEntry ->
                val eventId = backStackEntry.arguments?.getString("eventId")
                EventDetailScreen(navController = navController, eventId = eventId)
            }
        }
    }
}
