package com.example.proyectofinal.ui.screens.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinal.domain.model.Event
import com.example.proyectofinal.domain.model.NotificationSettings
import com.example.proyectofinal.domain.usecase.GetAgendaUseCase
import com.example.proyectofinal.util.NotificationHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

// --- CLASES DE DATOS (Solo se definen una vez aquí arriba) ---

data class NotificationItem(
    val title: String,
    val subtitle: String,
    val eventId: String? = null
)

data class NotificationHistory(
    val upcoming: List<NotificationItem>,
    val past: List<NotificationItem>
)

// --- VIEWMODEL UNIFICADO ---

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val getAgendaUseCase: GetAgendaUseCase,    // 1. Para la lista de eventos
    private val notificationHandler: NotificationHandler // 2. Para lanzar notificaciones
) : ViewModel() {

    // Estados
    private val _settings = MutableStateFlow(NotificationSettings())
    val settings: StateFlow<NotificationSettings> = _settings.asStateFlow()

    private val _history = MutableStateFlow<NotificationHistory?>(null)
    val history: StateFlow<NotificationHistory?> = _history.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadData()
    }

    // Carga los datos de la agenda y los separa en Pasados/Futuros
    private fun loadData() {
        viewModelScope.launch {
            // Simulación de carga de configuración
            delay(500)
            _settings.value = NotificationSettings(isEnabled = true, reminderRangeMinutes = 30)

            // Obtener y procesar el Flow de la agenda
            getAgendaUseCase()
                .map { allAgendaEvents ->
                    val now = System.currentTimeMillis()

                    // a) Eventos PRÓXIMOS (Fecha >= Ahora)
                    val upcomingEvents = allAgendaEvents
                        .filter { it.dateTimestamp >= now }
                        .sortedBy { it.dateTimestamp }
                        .map { mapToNotificationItem(it, "Recordatorio") }

                    // b) Eventos PASADOS (Fecha < Ahora)
                    val pastEvents = allAgendaEvents
                        .filter { it.dateTimestamp < now }
                        .sortedByDescending { it.dateTimestamp }
                        .map { mapToNotificationItem(it, "Evento Finalizado") }

                    NotificationHistory(
                        upcoming = upcomingEvents,
                        past = pastEvents
                    )
                }
                .collect { history ->
                    _history.value = history
                    _isLoading.value = false
                }
        }
    }

    // Helper para transformar Event -> NotificationItem
    private fun mapToNotificationItem(event: Event, type: String): NotificationItem {
        val dateFormat = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault())
            .format(Date(event.dateTimestamp))

        return NotificationItem(
            title = "$type: ${event.title}",
            subtitle = dateFormat,
            eventId = event.id
        )
    }

    // Cambiar configuración (simulado)
    fun toggleNotifications(enabled: Boolean) {
        viewModelScope.launch {
            _settings.update { it.copy(isEnabled = enabled) }
        }
    }

    // Cambiar rango de tiempo (simulado)
    fun updateReminderRange(minutes: Int) {
        viewModelScope.launch {
            _settings.update { it.copy(reminderRangeMinutes = minutes) }
        }
    }

    // 👇 FUNCIÓN NUEVA: Botón de prueba (Usa el notificationHandler inyectado)
    fun sendTestNotification() {
        notificationHandler.showBasicNotification(
            title = "¡Prueba de Notificación!",
            message = "Si lees esto, el sistema funciona correctamente 🚀"
        )
    }
}