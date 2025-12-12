package com.example.proyectofinal.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.proyectofinal.R // Asegúrate de importar tu R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NotificationHandler @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val CHANNEL_ID = "agenda_channel_id"
    private val CHANNEL_NAME = "Recordatorios de Agenda"

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        // En Android 8.0+ (Oreo), es obligatorio crear un canal
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
                description = "Canal para alertas de eventos próximos"
            }

            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showBasicNotification(title: String, message: String) {
        // Comprobación de permiso básica para Android 13
        try {
            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info) // Icono del sistema por defecto
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true) // Se borra al tocarla

            with(NotificationManagerCompat.from(context)) {
                // El ID (1001) sirve para actualizar o borrar esta notificación específica
                notify(1001, builder.build())
            }
        } catch (e: SecurityException) {
            // Aquí deberíamos manejar si no hay permisos, pero para pruebas está bien
            e.printStackTrace()
        }
    }
}