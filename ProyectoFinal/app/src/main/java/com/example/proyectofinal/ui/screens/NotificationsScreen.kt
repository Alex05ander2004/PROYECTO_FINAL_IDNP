package com.example.proyectofinal.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.proyectofinal.ui.components.BottomNavigationBar
import com.example.proyectofinal.ui.components.SectionTitle
import com.example.proyectofinal.ui.theme.ProyectoFinalTheme

data class NotificationItem(
    val title: String,
    val subtitle: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(navController: NavController) {

    val hoy = listOf(
        NotificationItem("Recordatorio: Concierto de Jazz en el Parque", "10:00 AM"),
        NotificationItem("Actualización: Partido de fútbol - Cambio de horario", "12:30 PM")
    )

    val ayer = listOf(
        NotificationItem("Recordatorio: Clase de yoga al aire libre", "9:00 AM"),
        NotificationItem("Recordatorio: Excursión en bicicleta", "11:00 AM")
    )

    var notificationsEnabled by remember { mutableStateOf(true) }

    Scaffold(
        bottomBar = { BottomNavigationBar(selected = "notificaciones", navController = navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
        ) {
            // Header
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

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                item { SectionTitle("Hoy") }
                items(hoy.size) { index -> NotificationRow(hoy[index]) }

                item { SectionTitle("Ayer") }
                items(ayer.size) { index -> NotificationRow(ayer[index]) }

                item { SectionTitle("Configuración") }

                item {
                    NotificationToggleRow(
                        title = "Notificaciones generales",
                        subtitle = "Activar/desactivar notificaciones",
                        checked = notificationsEnabled,
                        onCheckedChange = { notificationsEnabled = it }
                    )
                }

                item {
                    NotificationOptionRow(
                        title = "Recordatorios de eventos",
                        subtitle = "Configurar recordatorios para eventos"
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
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = null,
            tint = Color(0xFF111618)
        )
        Column {
            Text(
                text = notification.title,
                color = Color(0xFF111618),
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp
            )
            Text(
                text = notification.subtitle,
                color = Color(0xFF617C89),
                fontSize = 13.sp
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
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color(0xFF13A4EC),
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color(0xFFF0F3F4)
            )
        )
    }
}

@Composable
fun NotificationOptionRow(title: String, subtitle: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NotificationsScreenPreview() {
    ProyectoFinalTheme {
        val navController = rememberNavController()
        NotificationsScreen(navController)
    }
}
