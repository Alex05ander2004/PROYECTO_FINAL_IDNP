package com.example.proyectofinal.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun BottomNavigationBar(
    navController: NavController,
    selected: String
) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 0.dp
    ) {
        NavigationBarItem(
            selected = selected == "explorar",
            onClick = { navController.navigate("explorar") },
            icon = { Icon(Icons.Default.Search, contentDescription = null) },
            label = { Text("Explorar") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF111618),
                unselectedIconColor = Color(0xFF617C89)
            )
        )
        NavigationBarItem(
            selected = selected == "agenda",
            onClick = { navController.navigate("agenda") },
            icon = { Icon(Icons.Default.DateRange, contentDescription = null) },
            label = { Text("Mi Agenda") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF111618),
                unselectedIconColor = Color(0xFF617C89)
            )
        )
        NavigationBarItem(
            selected = selected == "notificaciones",
            onClick = { navController.navigate("notificaciones") },
            icon = { Icon(Icons.Default.Notifications, contentDescription = null) },
            label = { Text("Notificaciones") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF111618),
                unselectedIconColor = Color(0xFF617C89)
            )
        )
        NavigationBarItem(
            selected = selected == "perfil",
            onClick = { navController.navigate("perfil") },
            icon = { Icon(Icons.Default.Person, contentDescription = null) },
            label = { Text("Perfil") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF111618),
                unselectedIconColor = Color(0xFF617C89)
            )
        )
    }
}
