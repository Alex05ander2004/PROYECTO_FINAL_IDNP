package com.example.proyectofinal.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey val id: String,
    val category: String,
    val title: String,
    val description: String,
    val dateTimestamp: Long,
    val price: String,
    val imageUrl: String,
    val text: String,
    val isUserCreated: Boolean,
    val isInAgenda: Boolean
)
