package com.example.proyectofinal.domain.model

data class NotificationSettings(
    val isEnabled: Boolean = true,
    // El rango de recordatorio, ej: 15 minutos, 60 minutos, 12 horas (en minutos)
    val reminderRangeMinutes: Int = 60
)