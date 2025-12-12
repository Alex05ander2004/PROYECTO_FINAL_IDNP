package com.example.proyectofinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.proyectofinal.ui.navigation.AppNavigation
import com.example.proyectofinal.ui.screens.settings.SettingsViewModel
import com.example.proyectofinal.ui.theme.ProyectoFinalTheme
import androidx.navigation.compose.rememberNavController
import com.example.proyectofinal.ui.screens.agenda.AgendaScreen
import com.example.proyectofinal.ui.screens.create.CreateEventScreen
import com.example.proyectofinal.ui.screens.notifications.NotificationsScreen
import com.example.proyectofinal.ui.screens.profile.ProfileScreen
import com.example.proyectofinal.ui.screens.detail.EventDetailScreen
import com.example.proyectofinal.ui.screens.explore.ExploreScreen
import androidx.navigation.NavType
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    // Inyecta el ViewModel a nivel de actividad para controlar el tema global.
    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProyectoFinalTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "explorar"
                ) {
                    // Rutas principales (las de BottomNavigationBar)
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

                    // Rutas secundarias que no están en el BottomBar
                    composable(
                        route = "detalle_evento/{eventId}",
                        arguments = listOf(navArgument("eventId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val eventId = backStackEntry.arguments?.getString("eventId")
                        EventDetailScreen(navController = navController, eventId = eventId)
                    }
                    composable("create_event") { // <-- RUTA AÑADIDA
                        CreateEventScreen(navController = navController)
                    }
                }
            }
        }
    }
}
