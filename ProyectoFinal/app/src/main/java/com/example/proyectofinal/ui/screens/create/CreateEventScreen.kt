package com.example.proyectofinal.ui.screens.create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.* // Importante para la lógica del dropdown
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
// Borramos el import de BottomNavigationBar porque ya no lo usamos aquí

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEventScreen(
    navController: NavController,
    viewModel: CreateEventViewModel = hiltViewModel()
) {
    // Escuchar evento de navegación para salir al terminar
    LaunchedEffect(true) {
        viewModel.navigationEvent.collect {
            navController.popBackStack()
        }
    }

    // Estado local para controlar si el menú está expandido o no
    var expanded by remember { mutableStateOf(false) }

    // Lista de categorías existentes
    val categories = listOf("Música", "Deporte", "Teatro", "Cine", "Arte", "Conferencia", "Otro")

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Crear Evento", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Campo Título
            OutlinedTextField(
                value = viewModel.title,
                onValueChange = { viewModel.title = it },
                label = { Text("Título del Evento") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            // Campo Días Faltantes
            OutlinedTextField(
                value = viewModel.daysUntilEvent,
                onValueChange = {
                    if (it.all { char -> char.isDigit() }) {
                        viewModel.daysUntilEvent = it
                    }
                },
                label = { Text("¿En cuántos días será? (ej: 5)") },
                placeholder = { Text("0 = Hoy") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )

            // Campo Descripción
            OutlinedTextField(
                value = viewModel.description,
                onValueChange = { viewModel.description = it },
                label = { Text("Descripción corta") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {

                // 👇 CAMBIO: Selector de Categoría (Dropdown)
                Box(modifier = Modifier.weight(1f)) {
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = viewModel.category,
                            onValueChange = {}, // Solo lectura, se cambia con el menú
                            readOnly = true,
                            label = { Text("Categoría") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.menuAnchor() // Necesario para anclar el menú
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            categories.forEach { selectionOption ->
                                DropdownMenuItem(
                                    text = { Text(selectionOption) },
                                    onClick = {
                                        viewModel.category = selectionOption
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                // Campo Precio
                OutlinedTextField(
                    value = viewModel.price,
                    onValueChange = { input ->
                        // 👇 VALIDACIÓN: Solo aceptamos dígitos
                        if (input.all { it.isDigit() }) {
                            viewModel.price = input
                        }
                    },
                    label = { Text("Precio") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    // 👇 TECLADO NUMÉRICO:
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
            }

            // Campo URL Imagen
            OutlinedTextField(
                value = viewModel.imageUrl,
                onValueChange = { viewModel.imageUrl = it },
                label = { Text("URL de imagen (Opcional)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            // Campo Texto Largo
            OutlinedTextField(
                value = viewModel.text,
                onValueChange = { viewModel.text = it },
                label = { Text("Detalles completos") },
                modifier = Modifier.fillMaxWidth().height(120.dp),
                shape = RoundedCornerShape(12.dp),
                maxLines = 5
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón Guardar
            Button(
                onClick = { viewModel.onSaveEvent() },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp),
                // Validamos que tenga título, categoría y días
                enabled = viewModel.title.isNotBlank() &&
                        viewModel.daysUntilEvent.isNotBlank() &&
                        viewModel.category.isNotBlank()
            ) {
                Text("Publicar Evento", fontSize = 18.sp)
            }
        }
    }
}