package com.example.proyectofinal.ui.screens.eventdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.proyectofinal.domain.model.Event
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreen(
    navController: NavController,
    viewModel: EventDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Detalle del Evento") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        when (uiState) {
            is EventDetailUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is EventDetailUiState.Success -> {
                val event = (uiState as EventDetailUiState.Success).event
                EventDetailContent(
                    event = event,
                    modifier = Modifier.padding(innerPadding),
                    onToggleAgenda = { viewModel.toggleAgendaStatus() },
                    navController = navController
                )
            }

            is EventDetailUiState.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text((uiState as EventDetailUiState.Error).message, color = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { viewModel.retry() }) {
                        Text("Reintentar")
                    }
                }
            }
        }
    }
}

@Composable
fun EventDetailContent(
    event: Event,
    modifier: Modifier = Modifier,
    onToggleAgenda: () -> Unit,
    navController: NavController // 👇 Recibimos el navController aquí
) {
    // --- LÓGICA DE FECHAS ---
    val now = System.currentTimeMillis()
    val isEventPast = event.dateTimestamp < now

    // Cálculo de días restantes
    val diff = event.dateTimestamp - now
    val daysRemaining = TimeUnit.MILLISECONDS.toDays(diff)

    // Formateador de fecha legible
    val dateFormat = SimpleDateFormat("dd MMM, yyyy - hh:mm a", Locale.getDefault())
    val dateString = dateFormat.format(Date(event.dateTimestamp))

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        // Imagen principal
        Image(
            painter = rememberAsyncImagePainter(event.imageUrl),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Título
        Text(
            text = event.title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(8.dp))

        // --- SECCIÓN DE FECHA ---
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = null,
                tint = if (isEventPast) Color.Gray else MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = dateString,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                // Texto de estado
                Text(
                    text = if (isEventPast) "Evento Finalizado" else "Faltan $daysRemaining días",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isEventPast) Color.Red else Color(0xFF617C89)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Categoría y precio
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AssistChip(
                onClick = { },
                label = { Text(event.category) }
            )
            Text(
                text = "$"+event.price,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Descripción
        Text(
            text = event.description,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (event.text.isNotBlank()) {
            Text(
                text = event.text,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // --- LÓGICA DE BOTONES ---
        if (event.isUserCreated) {
            // OPCIÓN A: ES MI EVENTO -> BOTÓN EDITAR
            Button(
                // Ahora sí funciona porque recibimos navController
                onClick = { navController.navigate("editar_evento/${event.id}") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFA000)
                )
            ) {
                Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Editar Evento", fontSize = 18.sp)
            }
        } else {
            // OPCIÓN B: NO ES MI EVENTO -> BOTÓN ASISTIR
            val buttonText = when {
                isEventPast -> "Evento Finalizado"
                event.isInAgenda -> "Dejar de asistir"
                else -> "Asistir al evento"
            }

            val buttonColor = when {
                isEventPast -> Color.Gray
                event.isInAgenda -> MaterialTheme.colorScheme.secondary
                else -> MaterialTheme.colorScheme.primary
            }

            Button(
                onClick = onToggleAgenda,
                enabled = !isEventPast,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
            ) {
                Text(buttonText, fontSize = 18.sp)
            }
        }
    }
}