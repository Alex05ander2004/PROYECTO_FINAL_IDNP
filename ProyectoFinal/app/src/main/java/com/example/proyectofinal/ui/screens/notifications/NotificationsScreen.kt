package com.example.proyectofinal.ui.screens.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.proyectofinal.ui.components.BottomNavigationBar
import com.example.proyectofinal.ui.components.SectionTitle
import com.example.proyectofinal.ui.theme.ProyectoFinalTheme
import com.example.proyectofinal.domain.model.NotificationSettings
import com.example.proyectofinal.ui.screens.notifications.NotificationHistory
import com.example.proyectofinal.ui.screens.notifications.NotificationItem

// 1. COMPOSABLE STATEFUL (Con ViewModel)
// Maneja el estado, llama a hiltViewModel()
@Composable
fun NotificationsScreen(
    navController: NavController,
    viewModel: NotificationsViewModel = hiltViewModel()
) {
    // Observar el estado del ViewModel
    val settings by viewModel.settings.collectAsState()
    val history by viewModel.history.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    NotificationsContent(
        navController = navController,
        settings = settings,
        history = history,
        isLoading = isLoading,
        onToggleNotifications = viewModel::toggleNotifications,
        // En un caso real: onClickReminderRange = viewModel::showRangeDialog
        onClickReminderRange = { /* Navegación o diálogo */ }
    )
}

// 2. COMPOSABLE STATELESS (Sin ViewModel - Solo recibe datos)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsContent(
    navController: NavController,
    settings: NotificationSettings,
    history: NotificationHistory?,
    isLoading: Boolean,
    onToggleNotifications: (Boolean) -> Unit,
    onClickReminderRange: () -> Unit
) {
    // Definir la etiqueta del rango
    val reminderRangeLabel = when (settings.reminderRangeMinutes) {
        15 -> "15 minutos antes"
        30 -> "30 minutos antes"
        60 -> "1 hora antes"
        else -> "${settings.reminderRangeMinutes} minutos antes"
    }

    Scaffold(
        bottomBar = { BottomNavigationBar(selected = "notificaciones", navController = navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
        ) {
            TopAppBar(
                title = {
                    Text(
                        text = "Notificaciones",
                        color = Color(0xFF111618),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color(0xFF111618))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )

            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    // NUEVO: SECCIÓN PRÓXIMOS RECORDATORIOS
                    history?.upcoming?.let {
                        if (it.isNotEmpty()) {
                            item { SectionTitle("Próximos Recordatorios") }
                            items(it.size) { index -> NotificationRow(it[index]) }
                        }
                    }

                    // NUEVO: SECCIÓN EVENTOS PASADOS (Historial)
                    history?.past?.let {
                        if (it.isNotEmpty()) {
                            item { SectionTitle("Eventos Pasados") }
                            items(it.size) { index -> NotificationRow(it[index]) }
                        }
                    }

                    // CONFIGURACIÓN
                    item { SectionTitle("Configuración") }

                    item {
                        NotificationToggleRow(
                            title = "Notificaciones generales",
                            subtitle = "Activar/desactivar notificaciones",
                            checked = settings.isEnabled,
                            onCheckedChange = onToggleNotifications // Usar callback
                        )
                    }

                    item {
                        NotificationOptionRow(
                            title = "Rango de recordatorios",
                            subtitle = "Avisar con ${reminderRangeLabel}",
                            onClick = onClickReminderRange // Usar callback
                        )
                    }
                }
            }
        }
    }
}

// Data class NotificationItem fue movida al ViewModel para que sea un objeto de la capa de UI.
// Si necesitas que compile aquí, puedes pegarla temporalmente, pero el diseño ideal es usar el import:
// import com.example.proyectofinal.ui.screens.notifications.NotificationItem

@Composable
fun NotificationRow(notification: NotificationItem) {
    // ... (El cuerpo de NotificationRow se mantiene igual)
}

@Composable
fun NotificationToggleRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    // ... (El cuerpo de NotificationToggleRow se mantiene igual)
}

@Composable
fun NotificationOptionRow(title: String, subtitle: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick) // Hacer toda la fila clickeable
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = title,
                color = Color(0xFF111618),
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp
            )
            Text(
                text = subtitle,
                color = Color(0xFF617C89),
                fontSize = 13.sp
            )
        }
        Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = Color(0xFF111618))
    }
}
// 3. Preview Corregido
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NotificationsScreenPreview() {
    ProyectoFinalTheme {
        val navController = rememberNavController()

        // 1. Crear datos de prueba (YA NO REDEFINIMOS LAS CLASES)
        val dummyUpcoming = listOf(
            NotificationItem(title = "Recordatorio: Concierto Test", subtitle = "En 30 minutos"),
            NotificationItem(title = "Recordatorio: Maratón", subtitle = "Mañana")
        )

        val dummyPast = listOf(
            NotificationItem(title = "Finalizado: Clase de Yoga", subtitle = "Hace 2 días"),
            NotificationItem(title = "Finalizado: Reunión", subtitle = "Ayer")
        )

        val dummyHistory = NotificationHistory(
            upcoming = dummyUpcoming,
            past = dummyPast
        )

        // 2. Usar el modelo de dominio NotificationSettings
        val dummySettings = NotificationSettings(isEnabled = true, reminderRangeMinutes = 30)

        // 3. Llamada al contenido
        NotificationsContent(
            navController = navController,
            settings = dummySettings,
            history = dummyHistory, // Este objeto ahora es del tipo esperado
            isLoading = false,
            onToggleNotifications = {},
            onClickReminderRange = {}
        )
    }
}