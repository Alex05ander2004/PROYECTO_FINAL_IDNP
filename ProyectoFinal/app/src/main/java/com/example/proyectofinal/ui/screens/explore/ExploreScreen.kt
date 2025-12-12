package com.example.proyectofinal.ui.screens.explore

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.* // Importante para remember, mutableStateOf, etc.
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.proyectofinal.domain.model.Event
import com.example.proyectofinal.ui.components.BottomNavigationBar

private const val TAG = "ExploreScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(
    navController: NavController,
    viewModel: ExploreViewModel = hiltViewModel()
) {
    // 1. Nos suscribimos a la lista de eventos del ViewModel
    val allEvents by viewModel.events.collectAsState()
    Log.d(TAG, "ExploreScreen se está recomponiendo con ${allEvents.size} eventos.")

    // 2. Lógica local para búsqueda y filtrado
    var search by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Todos") }

    // Calculamos las categorías dinámicamente
    val categories = remember(allEvents) {
        listOf("Todos") + allEvents.map { it.category }.distinct()
    }

    // Filtramos los eventos según la búsqueda y categoría
    val filteredEvents = remember(search, selectedCategory, allEvents) {
        allEvents
            .filter { event ->
                if (selectedCategory == "Todos") true else event.category == selectedCategory
            }
            .filter { event ->
                if (search.isBlank()) true else {
                    event.title.contains(search, ignoreCase = true) ||
                            event.description.contains(search, ignoreCase = true)
                }
            }
    }

    Scaffold(
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding) // Usamos el padding del Scaffold
                .fillMaxSize()
                .background(Color.White)
        ) {
            // --- HEADER: Título ---
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Explorar",
                        color = Color(0xFF111618),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )

            // --- BARRA DE BÚSQUEDA ---
            OutlinedTextField(
                value = search,
                onValueChange = { search = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
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
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            // --- FILTRO DE CATEGORÍAS ---
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(categories) { category ->
                    val isSelected = category == selectedCategory
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { selectedCategory = category }
                            .background(if (isSelected) Color(0xFF13A4EC) else Color(0xFFF0F3F4))
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            category,
                            color = if (isSelected) Color.White else Color(0xFF111618),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // --- LISTA DE EVENTOS ---
            if (filteredEvents.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                    Text("No se encontraron eventos", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredEvents) { event ->
                        EventCard(event, navController)
                    }
                }
            }
        }
    }
}

@Composable
fun EventCard(event: Event, navController: NavController) {
    Card( // Envuelvo en Card para mejor estilo
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate("detalle_evento/${event.id}") },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA))
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .height(IntrinsicSize.Min), // Altura dinámica
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(event.category, color = Color(0xFF617C89), fontSize = 12.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    event.title,
                    color = Color(0xFF111618),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
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
                    .size(100.dp) // Tamaño fijo para la imagen
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray), // Placeholder visual
                contentScale = ContentScale.Crop
            )
        }
    }
}