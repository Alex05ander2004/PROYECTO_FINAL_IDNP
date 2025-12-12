package com.example.proyectofinal.ui.screens.notifications

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.proyectofinal.domain.model.NotificationSettings
import com.example.proyectofinal.ui.components.SectionTitle

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
        onClickReminderRange = { /* Futura navegación o diálogo */ },
        // 👇 CONEXIÓN: Conectamos el botón con la función del ViewModel
        onTestNotification = viewModel::sendTestNotification
    )
}

// 2. COMPOSABLE STATELESS (Limpio y con botón de prueba)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsContent(
    settings: NotificationSettings,
    history: NotificationHistory?,
    isLoading: Boolean,
    onToggleNotifications: (Boolean) -> Unit,
    onClickReminderRange: () -> Unit,
    onTestNotification: () -> Unit // 👇 Nuevo parámetro para la prueba
) {
    val reminderRangeLabel = when (settings.reminderRangeMinutes) {
        15 -> "15 minutos antes"
        30 -> "30 minutos antes"
        60 -> "1 hora antes"
        else -> "${settings.reminderRangeMinutes} minutos antes"
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Avisos", // Nombre corto para la barra
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            },
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
                // --- BOTÓN DE PRUEBA (SOLO PARA DESARROLLO) ---
                item {
                    Button(
                        onClick = onTestNotification,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary
                        )
                    ) {
                        Icon(Icons.Default.Notifications, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Probar Notificación Ahora")
                    }
                }

                // --- HISTORIAL DE PRÓXIMOS ---
                history?.upcoming?.let {
                    if (it.isNotEmpty()) {
                        item { SectionTitle("Próximos Recordatorios") }
                        items(it) { item -> NotificationRow(item) }
                    }
                }

                // --- HISTORIAL DE PASADOS ---
                history?.past?.let {
                    if (it.isNotEmpty()) {
                        item { SectionTitle("Historial Reciente") }
                        items(it) { item -> NotificationRow(item) }
                    }
                }

                // --- CONFIGURACIÓN ---
                item { SectionTitle("Configuración") }

                item {
                    NotificationToggleRow(
                        title = "Notificaciones generales",
                        subtitle = "Activar/desactivar alertas",
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

// --- COMPONENTES AUXILIARES ---

@Composable
fun NotificationRow(notification: NotificationItem) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = notification.title,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 16.sp
            )
            Text(
                text = notification.subtitle,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 14.sp
            )
        }
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