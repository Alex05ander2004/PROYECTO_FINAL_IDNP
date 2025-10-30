package com.example.proyectofinal.ui.screens

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
import com.example.proyectofinal.ui.theme.ProyectoFinalTheme
import com.example.proyectofinal.ui.components.BottomNavigationBar
import com.example.proyectofinal.data.EventDataSource // Importar el origen de datos
import com.example.proyectofinal.data.Event // Importar la data class


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(navController: NavController) {
    val categories = listOf("Música", "Deportes", "Teatro", "Cine", "Arte")

    // AHORA SE USA LA LISTA CENTRALIZADA:
    val events = EventDataSource.events

    var search by remember { mutableStateOf("") }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController, selected = "explorar") }
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
                        text = "Eventos",
                        color = Color(0xFF111618),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                actions = {
                    IconButton(onClick = { /* Acción de búsqueda */ }) {
                        Icon(Icons.Default.Search, contentDescription = null, tint = Color(0xFF111618))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )

            OutlinedTextField(
                value = search,
                onValueChange = { search = it },
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

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(categories) { category ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFF0F3F4))
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            category,
                            color = Color(0xFF111618),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(events) { event ->
                    EventCard(event, navController)
                }
            }
        }
    }
}

@Composable
fun EventCard(event: Event, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            // usamos el id unico del objeto event
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
