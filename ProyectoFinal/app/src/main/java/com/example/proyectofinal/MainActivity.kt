package com.example.proyectofinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.proyectofinal.ui.screens.ExploreScreen
import com.example.proyectofinal.ui.theme.ProyectoFinalTheme
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProyectoFinalTheme {
                val navController = rememberNavController()
                ExploreScreen(navController) // ← Muestra solo la pantalla de eventos
            }
        }
    }
}
