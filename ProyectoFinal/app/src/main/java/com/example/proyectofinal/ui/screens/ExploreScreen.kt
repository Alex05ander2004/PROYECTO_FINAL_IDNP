package com.example.proyectofinal.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(navController: NavController) {
    val categories = listOf("Música", "Deportes", "Teatro", "Cine", "Arte")

    val events = listOf(
        Event(
            "Concierto", "Festival de Jazz",
            "Central Park, Nueva York · 15 de julio · $30",
            "https://lh3.googleusercontent.com/aida-public/AB6AXuA1z92clRkUOnFYwlWU8UTh4buLTtxm0IzzcQ85gVF3YRZHcPlKXfzy3MbwSH3ns5SBcTAHlxXg8GGMqm3_s06ad7jMFIAKlJTKIJKku3ShkuVG8HeeO-eN4KSJ1Gpz7gu5oRjiO6gq8GdpUMGFR95evhpi_U-GGVBrLfVv7AV7axkOU8hue9tP4Hl9IYTUzlx2E202XpP8YGXYFGdcAbUQ8TPxlYVa3CADgWKHCUQyn6-1lxDnIWSLVPrpIWuYThm8_PbxbTQq_dJZ"
        ),
        Event(
            "Deporte", "Maratón de la ciudad",
            "Calles de la ciudad · 22 de julio · $50",
            "https://lh3.googleusercontent.com/aida-public/AB6AXuDEl2YLn-bjRazMUMFpKWOPgj-hEFjPBoZ4u2bsAOuiEgQYSqsp8JoGbREOyErHEKiQVWHchylgUO_pYAlelDqY7onpbTxq9pNEDVXyP6KiGCBo_lCrTAV8ReaHK5bMoG2tY_zgGAKrtDBl39nvZPCHdb08u5bA8lk3g8hF9tZ3gCzdRLX3uOC5TxW6mBR1zB2lcQqYX2gfHZZ8-TeRIr98JSh0Wb6r-3dmsR6L7ZPrs5F_Xki4NW2sSeZsooWm-PxzFi9ROMpIhyxv"
        ),
        Event(
            "Teatro", "El Fantasma de la Ópera",
            "Teatro Majestic, Nueva York · 29 de julio · $80",
            "https://lh3.googleusercontent.com/aida-public/AB6AXuCROhCrA-vVUySeIyDoQnj0mv-ZBHM-wtr1ZTOIY0zruTNdm8YFTplu9hDSp5KLP9FnkjdXiga7gCQCm3JftR_hKG0Fv6hMNmj9EdguT8UFhzuVe1CBQRd70f8XI83q0phaINbazdWQaMke6xQ7yR10_E3ZlIxgqYY352yezzSN8thbJxPSd-jnOHhdrZf2Zmrjs01tNiB9TMkbK0CxrdU7JkseVzneDXL_gbRSr2M86HxnbAdxeVbSugv7wAcJ_7ulhgkKWxSsTt-G"
        ),
        Event(
            "Cine", "Estreno de película",
            "Cine local · 5 de agosto · $15",
            "https://lh3.googleusercontent.com/aida-public/AB6AXuCqpkiFSPimk8qBx-HLQRh4Gn4J_GgzN2Bcc5F7nILLPmNNM5cAGHJpU3QDfDB6TerbNlqYxduY4unCbMoKQKhteOHkTaxjVN7OAALhIMaHyxdGeVbQ1C283IXxiCAYsEu3Z6SXtj2wVfepQjmJsrbWUa0zzXu-_AuzR5LNkMMgLGfhdMjdNVBZbawkD3sMzAbZPOmY9KsIxxRc_b-RUKBPK9xwavZYeIDsN7XEI0ms-2a-hVk9AapzDuNW0ZMF3tHVLiiVA9cyCmQM"
        ),
        Event(
            "Arte", "Exposición de arte moderno",
            "Museo de Arte Moderno · 12 de agosto · $20",
            "https://lh3.googleusercontent.com/aida-public/AB6AXuB9mmG5_HTyaKXQZR92Zo8pJ6ahVp8Ypa-IBi22loMLQi-CrGBQKcAx_k7kteyd_ytyvpt_2vPFMQ8t5lE1dMGOG8_gM2_LS-aCl8px-rL83fEGgAecI1tS8yxLBr01PQA-l7QRg6vGhqMrEVeVoX4mJbufEWLS_9QfreaPc7N7EWcSthTjSyCzKhscDRzKDQACGk2bXkzg2VMEm-8u1YYfGRtUEDgDuRUtLZ9eaBlmnE66MyYnzSOHz_AgOOT5Bt25c4Zlmf1NioNb"
        )
    )

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
                    EventCard(event)
                }
            }
        }
    }
}

data class Event(
    val category: String,
    val title: String,
    val description: String,
    val imageUrl: String
)

@Composable
fun EventCard(event: Event) {
    Row(
        modifier = Modifier.fillMaxWidth(),
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
