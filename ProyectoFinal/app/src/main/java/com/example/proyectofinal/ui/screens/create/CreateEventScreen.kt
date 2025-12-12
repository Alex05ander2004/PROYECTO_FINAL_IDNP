package com.example.proyectofinal.ui.screens.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.proyectofinal.ui.theme.ProyectoFinalTheme
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEventScreen(
    navController: NavController,
    viewModel: CreateEventViewModel = hiltViewModel()
) {

    // Escucha el evento de navegación para volver atrás
    LaunchedEffect(key1 = true) {
        viewModel.navigationEvent.collectLatest {
            navController.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crear Evento") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = viewModel.title,
                onValueChange = { viewModel.title = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = viewModel.description,
                onValueChange = { viewModel.description = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = viewModel.category,
                onValueChange = { viewModel.category = it },
                label = { Text("Categoría") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = viewModel.price,
                onValueChange = { viewModel.price = it },
                label = { Text("Precio") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = viewModel.imageUrl,
                onValueChange = { viewModel.imageUrl = it },
                label = { Text("URL de la imagen") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = viewModel.text,
                onValueChange = { viewModel.text = it },
                label = { Text("Texto adicional") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = viewModel::onSaveEvent, // Llama a la función del ViewModel
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Evento")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateEventScreenPreview() {
    ProyectoFinalTheme {
        CreateEventScreen(rememberNavController())
    }
}
