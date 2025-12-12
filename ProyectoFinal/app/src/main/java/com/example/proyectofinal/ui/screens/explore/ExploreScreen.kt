package com.example.proyectofinal.ui.screens.explore

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.proyectofinal.domain.model.Event
import com.example.proyectofinal.ui.components.BottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(
    navController: NavController,
    viewModel: ExploreViewModel = hiltViewModel()
) {
    // 1. Datos del ViewModel
    val allEvents by viewModel.events.collectAsState()

    // 2. Estados Locales para Búsqueda y Filtros
    var searchText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Todos") }

    // Gestor de foco para ocultar teclado
    val focusManager = LocalFocusManager.current

    // 3. Calculamos categorías disponibles (dinámico)
    val categories = remember(allEvents) {
        listOf("Todos") + allEvents.map { it.category }.distinct()
    }

    // 4. LÓGICA DE FILTRADO (Aquí es donde ocurre la magia)
    val filteredEvents = remember(searchText, selectedCategory, allEvents) {
        allEvents.filter { event ->
            // Filtro 1: Categoría
            val matchCategory = if (selectedCategory == "Todos") true else event.category == selectedCategory

            // Filtro 2: Texto (Título o Descripción)
            val matchText = if (searchText.isBlank()) true else {
                event.title.contains(searchText, ignoreCase = true) ||
                        event.description.contains(searchText, ignoreCase = true) ||
                        event.category.contains(searchText, ignoreCase = true)
            }

            matchCategory && matchText
        }
    }

    Scaffold(
        // Ya no usamos bottomBar aquí porque está en AppNavigation
        // Ya no usamos floatingActionButton aquí porque está en BottomNavBar
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
        ) {
            // --- HEADER ---
            CenterAlignedTopAppBar(
                title = {
                    Text("Explorar", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )

            // --- BARRA DE BÚSQUEDA MEJORADA ---
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("Buscar eventos...") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray)
                },

                trailingIcon = {
                    if (searchText.isNotEmpty()) {
                        IconButton(onClick = { searchText = "" }) {
                            Icon(Icons.Default.Close, contentDescription = "Borrar", tint = Color.Gray)
                        }
                    }
                },

                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = { focusManager.clearFocus() }
                ),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF0F3F4),
                    unfocusedContainerColor = Color(0xFFF0F3F4),
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = Color.Transparent
                )
            )

            // --- FILTRO DE CATEGORÍAS ---
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(categories) { category ->
                    val isSelected = category == selectedCategory
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedCategory = category },
                        label = { Text(category) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = Color.White,
                            containerColor = Color(0xFFF0F3F4),
                            labelColor = Color.Black
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = isSelected,
                            borderColor = Color.Transparent
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // --- LISTA DE RESULTADOS ---
            if (filteredEvents.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Search, contentDescription = null, tint = Color.LightGray, modifier = Modifier.size(64.dp))
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "No se encontraron resultados para\n\"$searchText\"",
                            color = Color.Gray,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
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

// Reutilizamos el EventCard que ya funcionaba (asegúrate de que esté en el archivo)
@Composable
fun EventCard(event: Event, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate("detalle_evento/${event.id}") },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA))
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .height(IntrinsicSize.Min),
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
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray),
                contentScale = ContentScale.Crop
            )
        }
    }
}