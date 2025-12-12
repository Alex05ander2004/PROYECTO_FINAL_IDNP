package com.example.proyectofinal.ui.components

import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings // Usamos icono de Settings
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun BottomNavigationBar(
    navController: NavController,
    selected: String
) {
    NavigationBar(
        // Cambio 1: Usar el color del tema en lugar de blanco fijo.
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp
    ) {
        NavigationBarItem(
            selected = selected == "explorar",
            onClick = { navController.navigate("explorar") },
            icon = { Icon(Icons.Default.Search, contentDescription = null) },
            label = { Text("Explorar") },
            // Cambio 2: Usar los colores del tema para los ítems.
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
        NavigationBarItem(
            selected = selected == "crear",
            onClick = { navController.navigate("crear") },
            icon = { Icon(Icons.Default.Create, contentDescription = null) },
            label = { Text("Crear") }, // Texto más corto para que quepa bien
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF111618),
                unselectedIconColor = Color(0xFF617C89)
            )
        )
        NavigationBarItem(
            selected = selected == "agenda",
            onClick = { navController.navigate("agenda") },
            icon = { Icon(Icons.Default.DateRange, contentDescription = null) },
            label = { Text("Agenda") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
        NavigationBarItem(
            selected = selected == "notificaciones",
            onClick = { navController.navigate("notificaciones") },
            icon = { Icon(Icons.Default.Notifications, contentDescription = null) },
            label = { Text("Avisos") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
        // 👇 CAMBIO IMPORTANTE: La ruta interna es "settings", la etiqueta es "Configuración"
        NavigationBarItem(
            selected = selected == "settings",
            onClick = { navController.navigate("settings") },
            icon = { Icon(Icons.Default.Settings, contentDescription = null) },
            label = { Text("Ajustes") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
    }
}