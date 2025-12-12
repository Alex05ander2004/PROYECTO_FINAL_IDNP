package com.example.proyectofinal.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

// Declara el DataStore a nivel de top-level, asociado al contexto de la aplicación.
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

@Singleton
class UserPreferencesRepository @Inject constructor(@ApplicationContext private val context: Context) {

    // Clave para almacenar la preferencia del tema oscuro.
    private companion object {
        val IS_DARK_THEME = booleanPreferencesKey("is_dark_theme")
    }

    /**
     * Flujo que emite el estado actual del tema oscuro.
     * Si no hay ningún valor guardado, por defecto será `false` (tema claro).
     */
    val isDarkTheme: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[IS_DARK_THEME] ?: false
        }

    /**
     * Guarda el estado del tema oscuro en DataStore.
     * @param isDarkTheme `true` para tema oscuro, `false` para tema claro.
     */
    suspend fun saveThemePreference(isDarkTheme: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_DARK_THEME] = isDarkTheme
        }
    }
}
