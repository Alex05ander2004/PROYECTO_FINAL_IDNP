package com.example.proyectofinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels // 👈 Importante para 'by viewModels()'
import androidx.compose.runtime.collectAsState // 👈 Importante para .collectAsState()
import androidx.compose.runtime.getValue // 👈 Importante para usar 'by' con estados
import com.example.proyectofinal.ui.navigation.AppNavigation
import com.example.proyectofinal.ui.screens.settings.SettingsViewModel
import com.example.proyectofinal.ui.theme.ProyectoFinalTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // Inyecta el ViewModel. Si 'viewModels()' sale en rojo, revisa el paso extra abajo.
    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Recolecta el estado. Gracias al import 'getValue', el 'by' funciona.
            val uiState by settingsViewModel.uiState.collectAsState()

            // Aplica el tema. Gracias al paso 1, 'isDarkTheme' ahora existe.
            ProyectoFinalTheme(darkTheme = uiState.isDarkTheme) {
                AppNavigation()
            }
        }
    }
}