package com.example.proyectofinal.ui.screens.eventdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinal.domain.model.Event
import com.example.proyectofinal.domain.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventDetailViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val eventId: String = savedStateHandle["eventId"]!!

    private val _uiState = MutableStateFlow<EventDetailUiState>(EventDetailUiState.Loading)
    val uiState: StateFlow<EventDetailUiState> = _uiState.asStateFlow()

    init {
        loadEvent()
    }

    fun retry() = loadEvent()

    // 👇 NUEVA FUNCIÓN: Cambiar estado de asistencia
    fun toggleAgendaStatus() {
        // Solo podemos actuar si ya tenemos el evento cargado (Success)
        val currentState = _uiState.value
        if (currentState is EventDetailUiState.Success) {
            val currentEvent = currentState.event

            // Creamos una copia del evento con el valor invertido
            val updatedEvent = currentEvent.copy(isInAgenda = !currentEvent.isInAgenda)

            viewModelScope.launch {
                // Guardamos en BD. Room notificará el cambio automáticamente via Flow.
                eventRepository.updateEvent(updatedEvent)
            }
        }
    }

    private fun loadEvent() {
        viewModelScope.launch {
            eventRepository.getEventById(eventId)
                .onStart { _uiState.value = EventDetailUiState.Loading }
                .catch { exception ->
                    _uiState.value = EventDetailUiState.Error("No se pudo cargar el evento")
                }
                .collect { event ->
                    if (event != null) {
                        _uiState.value = EventDetailUiState.Success(event)
                    } else {
                        _uiState.value = EventDetailUiState.Error("Evento no encontrado")
                    }
                }
        }
    }
}

sealed class EventDetailUiState {
    object Loading : EventDetailUiState()
    data class Success(val event: Event) : EventDetailUiState()
    data class Error(val message: String) : EventDetailUiState()
}