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

// 1. COMPOSABLE STATEFUL (Con ViewModel)
// Este es el que llama la Navegación (AppNavigation)
@Composable
fun AgendaScreen(
    navController: NavController,
    viewModel: AgendaViewModel = hiltViewModel()
) {
    val agendaEvents by viewModel.uiState.collectAsState()

    // Llamamos al componente "puro" pasándole solo los datos
    AgendaContent(
        agendaEvents = agendaEvents,
        navController = navController,
        selectedTab = "agenda"
    )
}

// 2. COMPOSABLE STATELESS (Sin ViewModel)
// Este recibe DATOS puros. La Preview usa este.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendaContent(
    agendaEvents: List<Event>,
    navController: NavController,
    selectedTab: String
) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController, selected = selectedTab) }
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

            if (agendaEvents.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No tienes eventos agendados aún", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item { SectionTitle("Mis Eventos") }
                    items(agendaEvents) { event ->
                        AgendaEventItem(event, navController)
                    }
                }
            }
        }
    }
}

// ... (SectionTitle y AgendaEventItem se quedan igual) ...
@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        color = Color(0xFF111618),
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        modifier = Modifier.padding(bottom = 8.dp)
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
                text = event.description,
                color = Color(0xFF617C89),
                fontSize = 13.sp,
                maxLines = 1
            )
        }
    }
}

// 3. PREVIEW (Usando datos falsos)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AgendaScreenPreview() {
    ProyectoFinalTheme {
        val navController = rememberNavController()

        // Creamos datos falsos SOLO para ver el diseño
        val dummyEvents = listOf(
            Event(
                id = "1",
                category = "Música",
                title = "Concierto de Prueba",
                description = "Un evento genial",
                price = "$10",
                imageUrl = "",
                text = ""
            ),
            Event(
                id = "2",
                category = "Arte",
                title = "Exposición Mock",
                description = "Museo de arte",
                price = "$5",
                imageUrl = "",
                text = ""
            )
        )

        // Llamamos a AgendaContent (NO a AgendaScreen)
        AgendaContent(
            agendaEvents = dummyEvents,
            navController = navController,
            selectedTab = "agenda"
        )
    }
}