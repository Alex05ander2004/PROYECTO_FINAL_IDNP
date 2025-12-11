package com.example.proyectofinal.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.proyectofinal.R
import com.example.proyectofinal.ui.components.SectionTitle
import com.example.proyectofinal.ui.theme.ProyectoFinalTheme


@Composable
fun ProfileScreen(navController: NavController) {
    ProfileContent(navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileContent(navController: NavController) {
    // --- ESTRUCTURA LIMPIA ---
    // Se elimina el Scaffold, ya que AppNavigation.kt lo gestiona.
    // Se elimina el fondo blanco para que el tema dinámico funcione.
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // La TopAppBar se mantiene, pero usando colores del tema.
        TopAppBar(
            title = {
                Text(
                    text = "Perfil",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface,
                titleContentColor = MaterialTheme.colorScheme.onSurface
            )
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(vertical = 20.dp)
        ) {
            item {
                // --- INFO DE PERFIL ---
                val painter = rememberAsyncImagePainter(
                    model = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRzEkJm-v_u-aWp-s_S1-hV5r5aA5PC7-5yYw&s"
                )
                Image(
                    painter = painter,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Jhamil Turpo",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "jhamil.turpo@tecsup.edu.pe",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            // --- SECCIÓN GENERAL ---
            item {
                SectionTitle(title = "General")
            }
            item {
                ProfileItem(
                    title = "Editar Perfil",
                    onClick = { /* TODO */ }
                )
            }
            item {
                ProfileItem(
                    title = "Mis eventos",
                    onClick = { /* TODO */ }
                )
            }

            // --- SECCIÓN CONFIGURACIÓN (con el título en negrita) ---
            item {
                SectionTitle(title = "Configuración")
            }
            item {
                ProfileItem(
                    title = "Ir a Ajustes",
                    onClick = { navController.navigate("settings") }
                )
            }

            // --- SECCIÓN AYUDA ---
            item {
                SectionTitle(title = "Ayuda")
            }
            item {
                ProfileItem(
                    title = "Contáctanos",
                    onClick = { /* TODO */ }
                )
            }
        }
    }
}

@Composable
fun ProfileItem(title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
        Icon(
            Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    ProyectoFinalTheme(darkTheme = false) {
        ProfileContent(navController = rememberNavController())
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenDarkPreview() {
    ProyectoFinalTheme(darkTheme = true) {
        ProfileContent(navController = rememberNavController())
    }
}
