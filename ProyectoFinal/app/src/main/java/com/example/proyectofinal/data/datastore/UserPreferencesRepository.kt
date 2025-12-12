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

// 1. Definimos la extensión para crear la base de datos pequeña (DataStore)
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class UserPreferencesRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {

    // Definimos la clave para el Tema Oscuro
    private val THEME_KEY = booleanPreferencesKey("is_dark_theme")

    // 2. Función para LEER el dato (devuelve un Flow)
    val isDarkTheme: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            // Si no existe valor, devolvemos false (Tema claro por defecto)
            preferences[THEME_KEY] ?: false
        }

    // 3. Función para GUARDAR el dato
    suspend fun saveTheme(isDark: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDark
        }
    }
}