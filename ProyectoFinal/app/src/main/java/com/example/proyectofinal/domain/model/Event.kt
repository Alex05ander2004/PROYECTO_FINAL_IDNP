package com.example.proyectofinal.domain.model
data class Event(
    val id: String,
    val category: String,
    val title: String,
    val description: String,
    val dateTimestamp: Long,
    val price: String,
    val imageUrl: String,
    val text: String,
    val isUserCreated: Boolean = false,
    val isInAgenda: Boolean = false
)
