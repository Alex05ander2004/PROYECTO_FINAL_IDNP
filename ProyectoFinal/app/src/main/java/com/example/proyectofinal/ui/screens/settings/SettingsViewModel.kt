package com.example.proyectofinal.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinal.data.datastore.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SettingsUiState(
    val isDarkTheme: Boolean = false
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    // 👇 INYECTAMOS EL REPOSITORIO QUE GUARDA EN DISCO
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        // Al iniciar, nos suscribimos a los cambios de la base de datos (DataStore)
        viewModelScope.launch {
            userPreferencesRepository.isDarkTheme.collectLatest { isDark ->
                _uiState.value = SettingsUiState(isDarkTheme = isDark)
            }
        }
    }

    // Cuando el usuario toca el switch...
    fun onThemeChange(isDark: Boolean) {
        viewModelScope.launch {
            // ... guardamos en DataStore.
            // Esto disparará automáticamente el 'collectLatest' de arriba
            // y actualizará tanto esta pantalla como el MainActivity.
            userPreferencesRepository.saveTheme(isDark)
        }
    }
}