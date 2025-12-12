package com.example.proyectofinal.ui.screens.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinal.domain.model.NotificationSettings
import com.example.proyectofinal.domain.usecase.GetAgendaUseCase
import com.example.proyectofinal.domain.model.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

// 💡 Clases de Datos: Definimos el historial con los nuevos campos
data class NotificationItem(
    val title: String,
    val subtitle: String,
    val eventId: String? = null
)

// CAMBIO: Ahora usa 'upcoming' y 'past'
data class NotificationHistory(
    val upcoming: List<NotificationItem>,
    val past: List<NotificationItem>
)

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val getAgendaUseCase: GetAgendaUseCase
) : ViewModel() {

    private val _settings = MutableStateFlow(NotificationSettings())
    val settings: StateFlow<NotificationSettings> = _settings.asStateFlow()

    private val _history = MutableStateFlow<NotificationHistory?>(null)
    val history: StateFlow<NotificationHistory?> = _history.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            // Simulación de carga (reemplazar con DataStore real)
            delay(500)
            _settings.value = NotificationSettings(isEnabled = true, reminderRangeMinutes = 30)

            // Obtener y procesar el Flow de la agenda
            getAgendaUseCase()
                .map { allAgendaEvents ->
                    val now = System.currentTimeMillis()

                    // --- LÓGICA DE FILTRADO (PRÓXIMOS VS PASADOS) ---

                    // a) Eventos PRÓXIMOS (Fecha del evento >= Ahora)
                    val upcomingEvents = allAgendaEvents
                        .filter { it.dateTimestamp >= now }
                        .sortedBy { it.dateTimestamp }
                        // Mapear con un título claro para recordatorios
                        .map { mapToNotificationItem(it, "Recordatorio") }

                    // b) Eventos PASADOS (Fecha del evento < Ahora)
                    val pastEvents = allAgendaEvents
                        .filter { it.dateTimestamp < now }
                        .sortedByDescending { it.dateTimestamp }
                        // Mapear con un título claro para historial
                        .map { mapToNotificationItem(it, "Evento Finalizado") }

                    // Devolver el nuevo estado de historial
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

    // LÓGICA: Convierte un Evento a un Item de Notificación para la UI
    private fun mapToNotificationItem(event: Event, type: String): NotificationItem {
        // Formatear la fecha y hora del evento para el subtítulo
        val dateFormat = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault()).format(Date(event.dateTimestamp))

        return NotificationItem(
            title = "$type: ${event.title}",
            subtitle = dateFormat,
            eventId = event.id
        )
    }

    // (Funciones toggleNotifications y updateReminderRange se mantienen igual)
    fun toggleNotifications(enabled: Boolean) {
        viewModelScope.launch {
            _settings.update { it.copy(isEnabled = enabled) }
        }
    }

    fun updateReminderRange(minutes: Int) {
        viewModelScope.launch {
            _settings.update { it.copy(reminderRangeMinutes = minutes) }
        }
    }
}