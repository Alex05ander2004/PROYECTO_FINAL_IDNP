// ui/screens/eventdetail/EventDetailViewModel.kt
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