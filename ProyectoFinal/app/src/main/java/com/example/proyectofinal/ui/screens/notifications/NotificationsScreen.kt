package com.example.proyectofinal.ui.screens.notifications

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.proyectofinal.domain.model.NotificationSettings
import com.example.proyectofinal.ui.components.SectionTitle
import com.example.proyectofinal.ui.theme.ProyectoFinalTheme

// 1. COMPOSABLE STATEFUL (Con ViewModel)
@Composable
fun NotificationsScreen(
    navController: NavController,
    viewModel: NotificationsViewModel = hiltViewModel()
) {
    val settings by viewModel.settings.collectAsState()
    val history by viewModel.history.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    NotificationsContent(
        settings = settings,
        history = history,
        isLoading = isLoading,
        onToggleNotifications = viewModel::toggleNotifications,
        onClickReminderRange = { /* Navegación o diálogo */ }
    )
}

// 2. COMPOSABLE STATELESS (Limpio y corregido)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsContent(
    settings: NotificationSettings,
    history: NotificationHistory?,
    isLoading: Boolean,
    onToggleNotifications: (Boolean) -> Unit,
    onClickReminderRange: () -> Unit
) {
    val reminderRangeLabel = when (settings.reminderRangeMinutes) {
        15 -> "15 minutos antes"
        30 -> "30 minutos antes"
        60 -> "1 hora antes"
        else -> "${settings.reminderRangeMinutes} minutos antes"
    }

    // --- ESTRUCTURA LIMPIA ---
    // Se elimina el Scaffold, ya que AppNavigation.kt lo gestiona.
    // Se elimina el fondo blanco para que el tema dinámico funcione.
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // La TopAppBar se mantiene, pero usando colores del tema.
        TopAppBar(
            title = {
                Text(
                    text = "Notificaciones",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            },
            // Se elimina el ícono de navegación "Atrás", ya que es una pantalla principal.
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface,
                titleContentColor = MaterialTheme.colorScheme.onSurface
            )
        )

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                history?.upcoming?.let {
                    if (it.isNotEmpty()) {
                        item { SectionTitle("Próximos Recordatorios") }
                        items(it) { item -> NotificationRow(item) }
                    }
                }

                history?.past?.let {
                    if (it.isNotEmpty()) {
                        item { SectionTitle("Eventos Pasados") }
                        items(it) { item -> NotificationRow(item) }
                    }
                }

                item { SectionTitle("Configuración") }

                item {
                    NotificationToggleRow(
                        title = "Notificaciones generales",
                        subtitle = "Activar/desactivar notificaciones",
                        checked = settings.isEnabled,
                        onCheckedChange = onToggleNotifications
                    )
                }

                item {
                    NotificationOptionRow(
                        title = "Rango de recordatorios",
                        subtitle = "Avisar con $reminderRangeLabel",
                        onClick = onClickReminderRange
                    )
                }
            }
        }
    }
}


@Composable
fun NotificationRow(notification: NotificationItem) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = notification.title,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = notification.subtitle,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 14.sp
        )
    }
}

@Composable
fun NotificationToggleRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp
            )
            Text(
                text = subtitle,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 13.sp
            )
        }
        Switch(checked = checked, onCheckedChange = null)
    }
}

@Composable
fun NotificationOptionRow(title: String, subtitle: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp
            )
            Text(
                text = subtitle,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 13.sp
            )
        }
        Icon(
            Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
// 3. Preview Corregido
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NotificationsScreenPreview() {
    ProyectoFinalTheme(darkTheme = false) {
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
        val dummySettings = NotificationSettings(isEnabled = true, reminderRangeMinutes = 30)

        NotificationsContent(
            settings = dummySettings,
            history = dummyHistory,
            isLoading = false,
            onToggleNotifications = {},
            onClickReminderRange = {}
        )
    }
}
