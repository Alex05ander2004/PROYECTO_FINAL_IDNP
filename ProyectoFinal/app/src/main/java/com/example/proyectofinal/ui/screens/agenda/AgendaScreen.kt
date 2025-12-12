package com.example.proyectofinal.ui.screens.agenda

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.proyectofinal.domain.model.Event
import com.example.proyectofinal.ui.components.BottomNavigationBar
import com.example.proyectofinal.ui.theme.ProyectoFinalTheme
// Asegúrate de importar el UiState del paquete correcto
import com.example.proyectofinal.ui.screens.agenda.AgendaUiState

@Composable
fun AgendaScreen(
    navController: NavController,
    viewModel: AgendaViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    AgendaContent(
        uiState = uiState,
        navController = navController,
        selectedTab = "agenda"
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendaContent(
    uiState: AgendaUiState,
    navController: NavController,
    selectedTab: String
) {
    Scaffold(
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
        ) {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Mi Agenda",
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
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )

            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (uiState.createdEvents.isEmpty() && uiState.addedEvents.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No tienes eventos en tu agenda", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // SECCIÓN 1: Mis Eventos Creados
                    if (uiState.createdEvents.isNotEmpty()) {
                        item { SectionTitle("Mis Publicaciones") }
                        items(uiState.createdEvents) { event ->
                            AgendaEventItem(event, navController)
                        }
                    }

                    // SECCIÓN 2: Eventos Guardados (Interés)
                    if (uiState.addedEvents.isNotEmpty()) {
                        item { SectionTitle("Eventos Guardados") }
                        items(uiState.addedEvents) { event ->
                            AgendaEventItem(event, navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        color = Color(0xFF111618),
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        modifier = Modifier.padding(bottom = 8.dp, top = 8.dp)
    )
}

@Composable
fun AgendaEventItem(event: Event, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate("detalle_evento/${event.id}") }
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(event.imageUrl),
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Column {
            Text(
                text = event.title,
                color = Color(0xFF111618),
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
            Text(
                text = event.category, // Mostramos categoría o descripción
                color = Color(0xFF617C89),
                fontSize = 13.sp,
                maxLines = 1
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AgendaScreenPreview() {
    ProyectoFinalTheme(darkTheme = false) {
        val navController = rememberNavController()
        val now = System.currentTimeMillis()

        // 1. Evento Creado por Mí (isUserCreated = true)
        val myEvent = Event(
            id = "1", title = "Mi Torneo de Fútbol", description = "Organizado por mí",
            dateTimestamp = now, category = "Deporte", price = "$0", imageUrl = "", text = "",
            isUserCreated = true, // 👈 CLAVE
            isInAgenda = false
        )

        // 2. Evento Guardado (isInAgenda = true)
        val savedEvent = Event(
            id = "2", title = "Concierto de Rock", description = "Me interesa ir",
            dateTimestamp = now, category = "Música", price = "$50", imageUrl = "", text = "",
            isUserCreated = false,
            isInAgenda = true // 👈 CLAVE
        )

        val dummyUiState = AgendaUiState(
            createdEvents = listOf(myEvent),
            addedEvents = listOf(savedEvent),
            isLoading = false
        )

        AgendaContent(
            uiState = dummyUiState,
            navController = navController,
            selectedTab = "agenda"
        )
    }
}