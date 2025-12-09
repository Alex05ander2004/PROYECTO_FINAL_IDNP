package com.example.proyectofinal.ui.screens.agenda

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
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
import com.example.proyectofinal.ui.theme.ProyectoFinalTheme

data class AgendaEvent(
    val title: String,
    val subtitle: String,
    val imageUrl: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendaScreen(navController: NavController) {

    val proximos = listOf(
        AgendaEvent(
            "Concierto de Jazz",
            "Sábado, 20 de julio · Teatro Principal",
            "https://lh3.googleusercontent.com/aida-public/AB6AXuBc7suFChgvEg-tYXtGmyp1VIni-rV-5JYLJuFAWe1VYJsnIyEri80_Pxun0mSp_944jB92jlxu_74gSrE-d_0LUmQN0nLD6PQVXdFt71l5NL2Tma_cRyXl58d6cLAiUGEqPF95NKmKbLzSDUv5FiQgfOhyNGz03yT3LNnrQ-EoyaU3FIx73QkBsPR7-BQmtqoSW5heq-dYLD9Abtb_oRoKFIxhOJdgEaSvVq_RpGr8C-AUndAA8yePYkAwOB9_pprj8IaqrSP1hZtB"
        ),
        AgendaEvent(
            "Partido de Fútbol",
            "Domingo, 21 de julio · Estadio Municipal",
            "https://lh3.googleusercontent.com/aida-public/AB6AXuDeLoGzWuWnTKCMc4L7OES1PdSjq8LVJ9g2jrA74enBmRo4laNMi2Ia0V08FSBXw2_gI-WKmj77O4TNnitxXVx4rjTMchTMBIGeQ8rBN_ckW2Z0hVcLUwQwDXvcJcYmunbHeiayACjQMKSRI61WXaMYxb98ayPLay8mtpghpWqNP2DAKMhveqQ_BhU3yQI42b7jZzPhkzzb6_VG4K7isIxwzZFRgBhkg1Ejpeevx9CkrDnXgNR8SYxf9dmoFSChir8CirLb4Yt8dedw"
        ),
        AgendaEvent(
            "Exposición de Arte",
            "Lunes, 22 de julio · Centro Cultural",
            "https://lh3.googleusercontent.com/aida-public/AB6AXuCXVH3NYASEqaaKkxFwUehWKSaV7LrPLYyn5IaG4g6IEw9pEXspt9FdAnq4WdSLenRbITUOlEBqVR4I8rbd3mN4LG6CK1zHcYxOmz3kdMP98wJwTk5jt-_xamxvzO1OsqRAJGU3owR-Aj72NY_kJfNVFOneEIfkuQ-ZXAf0G4mQW21dYp_PINnl3ay7hwEoUu_MaP7cvczmXQLdxWltemASr-ejiUEBgcFikbH1Othhtki9PDrDlBe0I40JA-K5pughar5PE8SaEt_m"
        )
    )

    val pasados = listOf(
        AgendaEvent(
            "Noche de Blues",
            "Viernes, 19 de julio · Sala de Conciertos",
            "https://lh3.googleusercontent.com/aida-public/AB6AXuDej9WudF5n6k_MCGl2Itt9e8QKaN3_CNFE-qqrCPYLf_lkolFSUbEciziih1Iee4tQ_56-fVXTMmqaJiWWhXjvape-7nzuMUCLpCylbSFnTo6zQnwJ0hX_nsdpcON96bPMLG3bUIvup45RBhe_SZ_e_rdvndLe8eIe9DLpFJQnNDMxLF488eInVfa2O-lBF5NBdzVeSXicmpAgidmQqmo4-7cWKTJAgZ6Ls2bdT1yZFxgd6InXDmT5HX1lQzElFrGqvXYhvU6yLOCt"
        ),
        AgendaEvent(
            "Estreno de Película",
            "Jueves, 18 de julio · Cineplex",
            "https://lh3.googleusercontent.com/aida-public/AB6AXuBUFvNLuswvJgIVVVCdabHdkcOmJmC1ntxPh7gzPFEoBbhLHUir9x9k3QaXfHjXt-1UG_iEunA6Q3OKM01tLi85E4Ndx_yq_415jI5r3ZY0KoIRDb9EOzkn_97KO8boYNBoAMGw7ocUVhkOPsj3iHO4MERGc-O84OvwSQcI3ovCY6NQhmqXmZnfotdT0jB_k7_kYoBUZSQtBEZiDn43tN_0BalMyRI_qrJcyWesecMUeOrASqP_t5zKXVGl4Wlk0M3L_bz5X26CeZu9"
        )
    )

    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController, selected = "agenda") }
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
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                item { SectionTitle("Próximos") }
                items(proximos.size) { index ->
                    AgendaEventItem(proximos[index])
                }
                item { SectionTitle("Pasados") }
                items(pasados.size) { index ->
                    AgendaEventItem(pasados[index])
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
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Composable
fun AgendaEventItem(event: AgendaEvent) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
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
                text = event.subtitle,
                color = Color(0xFF617C89),
                fontSize = 13.sp
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AgendaScreenPreview() {
    ProyectoFinalTheme {
        val navController = rememberNavController()
        AgendaScreen(navController)
    }
}
