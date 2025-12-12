package com.example.proyectofinal.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// Definición del estado (Asegúrate de que esté aquí o importada)
data class SettingsUiState(
    val isDarkTheme: Boolean = false
)

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    // 👇 AQUÍ ESTABA EL ERROR:
    // Antes se llamaba 'toggleTheme', ahora lo renombramos a 'onThemeChange'
    // para que coincida con tu SettingsScreen.
    fun onThemeChange(isDark: Boolean) {
        viewModelScope.launch {
            _uiState.update { it.copy(isDarkTheme = isDark) }

            // TODO: Aquí deberíamos guardar el valor en DataStore para que
            // el tema se recuerde al cerrar la app.
        }
    }
}