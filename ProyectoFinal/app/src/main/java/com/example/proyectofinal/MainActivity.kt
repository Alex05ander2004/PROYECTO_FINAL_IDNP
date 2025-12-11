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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    // Inyecta el ViewModel a nivel de actividad para controlar el tema global.
    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Recolecta el estado del tema desde el ViewModel.
            val uiState by settingsViewModel.uiState.collectAsState()

            // Aplica el tema dinámicamente en el composable raíz.
            ProyectoFinalTheme(darkTheme = uiState.isDarkTheme) {
                // Llama al composable que contiene toda la lógica de navegación.
                AppNavigation()
            }
        }
    }
}
