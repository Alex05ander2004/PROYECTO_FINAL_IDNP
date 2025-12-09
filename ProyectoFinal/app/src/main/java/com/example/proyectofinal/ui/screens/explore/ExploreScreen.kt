package com.example.proyectofinal.ui.screens.explore

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.proyectofinal.data.Event
import com.example.proyectofinal.data.EventDataSource
import com.example.proyectofinal.ui.theme.ProyectoFinalTheme
import com.example.proyectofinal.ui.components.BottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(navController: NavController) {
    val categories = listOf("Todos") + EventDataSource.events.map { it.category }.distinct() // Añadir "Todos"
    val allEvents = EventDataSource.events // Usar la fuente de datos centralizada

    // 1. Estados de control
    var search by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Todos") }

    // 2. Lógica de Filtrado (Calculated State)
    val filteredEvents = remember(search, selectedCategory) {
        allEvents
            // Filtrado por Categoría
            .filter { event ->
                if (selectedCategory == "Todos") true else event.category == selectedCategory
            }
            // Filtrado por Búsqueda (en título o descripción)
            .filter { event ->
                if (search.isBlank()) true else {
                    event.title.contains(search, ignoreCase = true) ||
                            event.description.contains(search, ignoreCase = true)
                }
            }
    }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController, selected = "explorar") }
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
                        text = "Eventos",
                        color = Color(0xFF111618),
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )

            // Barra de Búsqueda (ya configurada, pero ahora afecta filteredEvents)
            OutlinedTextField(
                value = search,
                onValueChange = { search = it }, // Actualiza el estado 'search'
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(56.dp),
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null, tint = Color(0xFF617C89))
                },
                placeholder = { Text("Buscar eventos", color = Color(0xFF617C89)) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF0F3F4),
                    unfocusedContainerColor = Color(0xFFF0F3F4),
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent
                ),
                shape = RoundedCornerShape(12.dp)
            )

            // Categorías (LazyRow)
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(categories) { category ->
                    val isSelected = category == selectedCategory
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            // Acción de clic para cambiar la categoría seleccionada
                            .clickable { selectedCategory = category }
                            .background(if (isSelected) Color(0xFF13A4EC) else Color(0xFFF0F3F4)) // Color al seleccionar
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            category,
                            color = if (isSelected) Color.White else Color(0xFF111618), // Color del texto
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Lista de Eventos Filtrados
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredEvents) { event -> // Usar la lista filtrada
                    EventCard(event, navController) // Pasar NavController
                }
            }
        }
    }
}

// ⚠️ Mueve la data class Event y EventCard fuera del Composable para evitar errores de recompilación
// o asegúrate de que EventCard esté definida como en la respuesta anterior para aceptar navController y clickable.

// Si la definición de EventCard está aquí (como en tu código original), modifícala:
@Composable
fun EventCard(event: Event, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            // Implementar la navegación al detalle
            .clickable { navController.navigate("detalle_evento/${event.id}") },
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(modifier = Modifier.weight(2f)) {
            Text(event.category, color = Color(0xFF617C89), fontSize = 14.sp)
            Text(event.title, color = Color(0xFF111618), fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(
                event.description,
                color = Color(0xFF617C89),
                fontSize = 13.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
        Image(
            painter = rememberAsyncImagePainter(event.imageUrl),
            contentDescription = null,
            modifier = Modifier
                .weight(1f)
                .aspectRatio(16 / 9f)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ExploreScreenPreview() {
    ProyectoFinalTheme {
        val navController = rememberNavController()
        ExploreScreen(navController)
    }
}
