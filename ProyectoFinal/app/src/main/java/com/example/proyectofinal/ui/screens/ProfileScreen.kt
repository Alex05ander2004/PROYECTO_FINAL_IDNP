package com.example.proyectofinal.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.proyectofinal.ui.components.BottomNavigationBar
import com.example.proyectofinal.ui.components.SectionTitle
import com.example.proyectofinal.ui.theme.ProyectoFinalTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Perfil",
                        color = Color(0xFF111618),
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color(0xFF111618)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        bottomBar = { BottomNavigationBar(navController = navController, selected = "perfil") }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        ) {
            // ===== Sección de imagen y nombre =====
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = rememberAsyncImagePainter("https://lh3.googleusercontent.com/aida-public/AB6AXuDaSRQL7ymjTy6j7KyyQzUDPGCjUACvO0cONvINs4EZPQj0-V4ExrqTb2h8uHCz3bmC6nCKBxuzJJUWduQuUdHUe0IpZUIcJf4JP6txktDH9snkztyFbjtTpBsVhOzbbD-dQjj0Lp8JoeumP7eC1U9dMF3_TFMCVzJO6J1qcz3guOaFGe-4WPrHczz8c1_HS09kbtHouZUIwio67PPTe5GA-CRjLN_QYb1w4N2eYZjiKPIyd2YUcYa8Vs7iWHC0ezuxWQp5ZtI4Ffru"),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .size(128.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Sofía Ramírez",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF111618)
                )
                Text(
                    text = "sofia.ramirez@email.com",
                    fontSize = 14.sp,
                    color = Color(0xFF617C89)
                )
            }

            Divider(color = Color(0xFFF0F3F4))

            // ===== Información personal =====
            SectionTitle(title = "Información")
            ProfileItem("Nombre", "Sofía Ramírez")
            ProfileItem("Correo electrónico", "sofia.ramirez@email.com")
            ProfileItem("Idioma", "Español")

            Divider(color = Color(0xFFF0F3F4))

            // ===== Configuración =====
            SectionTitle(title = "Configuración")
            ProfileItem("Notificaciones", "Activadas")
            ProfileItem("Privacidad", "Pública")
            ProfileItem("Cerrar sesión", "")
        }
    }
}

@Composable
fun ProfileItem(title: String, subtitle: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = title,
                color = Color(0xFF111618),
                fontWeight = FontWeight.Medium
            )
            if (subtitle.isNotEmpty()) {
                Text(
                    text = subtitle,
                    color = Color(0xFF617C89),
                    fontSize = 14.sp
                )
            }
        }
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = "Ir",
            tint = Color(0xFF111618)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    ProyectoFinalTheme {
        val navController = rememberNavController()
        ProfileScreen(navController)
    }
}
