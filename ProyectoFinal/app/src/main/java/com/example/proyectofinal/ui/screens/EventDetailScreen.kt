package com.example.proyectofinal.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
import com.example.proyectofinal.ui.components.BottomNavigationBar
import com.example.proyectofinal.ui.theme.ProyectoFinalTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreen(navController: NavController) {
    val scrollState = rememberScrollState()

    Scaffold(
        bottomBar = { BottomNavigationBar(selected = "explorar", navController = navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(scrollState)
        ) {
            // Top bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Color(0xFF111618))
                }
                IconButton(onClick = { /* Compartir */ }) {
                    Icon(Icons.Default.Share, contentDescription = "Compartir", tint = Color(0xFF111618))
                }
            }

            // Imagen principal
            Image(
                painter = rememberAsyncImagePainter("https://lh3.googleusercontent.com/aida-public/AB6AXuB9T-o1BKjYLjaz3VCL18tlGGm3xKZONJaNjj4ZpY-YQlCQ79ZjYrt0y34ZQ-ldkqPgcxPpvZ8St9NEOOsLBvR5IYLdTXLIapleIw61j-NgJkYwQiwJ6rvurLORVt2gdR_GAaKuSQevbhKx7bQP0tQmEtHTen1NiDKUf8b-jM5vkqkFdIZuCf2QsOkvV-CbY7w7SqcydY-wyVtvwgymJShFmeFqpk9rjWy95YIV2E7CxL3lGl3QNBd8DatsKbAEprrXIHYgw-ZUjz6_"),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Título y fecha
            Text(
                text = "Indie Rock Festival",
                color = Color(0xFF111618),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Text(
                text = "Saturday, July 20, 7:00 PM",
                color = Color(0xFF617C89),
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )

            // Imagen secundaria
            Image(
                painter = rememberAsyncImagePainter("https://lh3.googleusercontent.com/aida-public/AB6AXuBDJRLXqYTbfppQeEBxU1QgRq77f8wHnb1V8JizeVtcn_8WbI1_BIJ9lX8IBfJdfjKFUT6YoqH9RxiLE7lc7uaWB_uJa6JjHYo91iVscuUSaTQrSrOP5AKn0QNJBmKPIthNx15WRODKZE-9iYxFO6wHFkYnH6ZfVECxLG52P3zRvfuax7Kkdj0lmsQ7NGdRwjABvntdDEe0JH29w3eSmAjdopd3GP-dbClotFXGiva7lzW765-yG3sZEh6tsDEF9j4C7koBEteb1g0I"),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16 / 9f)
                    .padding(16.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            // Descripción
            Text(
                text = "Experience the ultimate indie rock extravaganza at Central Park! Featuring electrifying performances by The Sonic Waves, Electric Echoes, and The Velvet Underground, this festival promises an unforgettable night of music, energy, and pure rock 'n' roll. Get ready to sing along to your favorite hits and discover new anthems that will stay with you long after the last note fades.",
                color = Color(0xFF111618),
                fontSize = 15.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                lineHeight = 20.sp
            )

            // Precio
            Text(
                text = "Price",
                color = Color(0xFF111618),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            Text(
                text = "$45 - $120",
                color = Color(0xFF111618),
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            // Botón principal
            Button(
                onClick = { /* Agregar a mi agenda */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF13A4EC)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Add to My Agenda", fontWeight = FontWeight.Bold)
            }

            // Botón secundario
            OutlinedButton(
                onClick = { /* Establecer recordatorio */ },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF111618)),
                border = ButtonDefaults.outlinedButtonBorder.copy(width = 0.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(44.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Set Reminder", fontWeight = FontWeight.Bold, color = Color(0xFF111618))
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EventDetailPreview() {
    ProyectoFinalTheme {
        val navController = rememberNavController()
        EventDetailScreen(navController)
    }
}
