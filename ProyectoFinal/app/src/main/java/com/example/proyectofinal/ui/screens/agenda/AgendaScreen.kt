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
import com.example.proyectofinal.ui.theme.ProyectoFinalTheme

@Composable
fun AgendaScreen(
    navController: NavController,
    viewModel: AgendaViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    AgendaContent(
        uiState = uiState,
        navController = navController
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendaContent(
    uiState: AgendaUiState,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Mi Agenda",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface)
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
        )

        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (uiState.upcomingEvents.isEmpty() && uiState.pastEvents.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No tienes eventos agendados aún", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (uiState.upcomingEvents.isNotEmpty()) {
                    item { SectionTitle("Próximos") }
                    items(uiState.upcomingEvents) { event ->
                        AgendaEventItem(event, navController)
                    }
                }

                if (uiState.pastEvents.isNotEmpty()) {
                    item { SectionTitle("Pasados") }
                    items(uiState.pastEvents) { event ->
                        AgendaEventItem(event, navController)
                    }
                }
            }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        color = MaterialTheme.colorScheme.onSurface,
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
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
            Text(
                text = event.description,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 13.sp,
                maxLines = 1
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AgendaScreenPreview() {
    ProyectoFinalTheme(darkTheme = false) {
        val navController = rememberNavController()
        val dummyUiState = AgendaUiState(
            upcomingEvents = listOf(Event(id = "1", title = "Upcoming Event", description = "Description", dateTimestamp = 0, category = "Music", price = "$10", imageUrl = "", text = "")),
            pastEvents = listOf(Event(id = "2", title = "Past Event", description = "Description", dateTimestamp = 0, category = "Art", price = "$5", imageUrl = "", text = "")),
            isLoading = false
        )
        AgendaContent(
            uiState = dummyUiState,
            navController = navController
        )
    }
}
