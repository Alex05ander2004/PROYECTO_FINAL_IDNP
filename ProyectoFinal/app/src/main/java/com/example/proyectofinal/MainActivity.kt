package com.example.proyectofinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.proyectofinal.ui.theme.ProyectoFinalTheme
import androidx.navigation.compose.rememberNavController
import com.example.proyectofinal.ui.screens.AgendaScreen
import com.example.proyectofinal.ui.screens.NotificationsScreen
import com.example.proyectofinal.ui.screens.ProfileScreen
import com.example.proyectofinal.ui.screens.EventDetailScreen
import com.example.proyectofinal.ui.screens.ExploreScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProyectoFinalTheme {
                // 1. Crear el NavController
                val navController = rememberNavController()

                // 2. Definir el NavHost con todas las rutas
                NavHost(
                    navController = navController,
                    // Establecer la pantalla que se mostrará al inicio
                    startDestination = "explorar"
                ) {
                    // Rutas principales (las de BottomNavigationBar)
                    composable("explorar") {
                        ExploreScreen(navController = navController)
                    }
                    composable("agenda") {
                        // Asegúrate de crear el composable AgendaScreen si aún no lo has hecho
                        AgendaScreen(navController = navController)
                    }
                    composable("notificaciones") {
                        NotificationsScreen(navController = navController)
                    }
                    composable("perfil") {
                        ProfileScreen(navController = navController)
                    }

                    // Rutas secundarias que no están en el BottomBar
                    composable("detalle_evento") {
                        EventDetailScreen(navController = navController)

                    }
                }
            }
        }
    }
}
