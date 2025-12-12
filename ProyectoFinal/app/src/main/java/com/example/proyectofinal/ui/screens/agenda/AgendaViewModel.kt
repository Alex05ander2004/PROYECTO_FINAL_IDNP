package com.example.proyectofinal.ui.screens.agenda

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinal.domain.model.Event
import com.example.proyectofinal.domain.usecase.GetAgendaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

// 💡 CAMBIO: El estado ahora refleja "Creados" y "Guardados"
data class AgendaUiState(
    val createdEvents: List<Event> = emptyList(), // Mis publicaciones
    val addedEvents: List<Event> = emptyList(),   // Eventos que me interesan
    val isLoading: Boolean = true
)

@HiltViewModel
class AgendaViewModel @Inject constructor(
    private val getAgendaUseCase: GetAgendaUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AgendaUiState())
    val uiState: StateFlow<AgendaUiState> = _uiState.asStateFlow()

    init {
        loadAgenda()
    }

    private fun loadAgenda() {
        viewModelScope.launch {
            getAgendaUseCase()
                .map { allAgendaEvents ->

                    // 1. Filtrar mis eventos creados
                    val created = allAgendaEvents.filter { it.isUserCreated }

                    // 2. Filtrar eventos guardados (excluyendo los que yo creé para no duplicar)
                    val added = allAgendaEvents.filter { it.isInAgenda && !it.isUserCreated }

                    AgendaUiState(
                        createdEvents = created,
                        addedEvents = added,
                        isLoading = false
                    )
                }
                .collect { newState ->
                    _uiState.value = newState
                }
        }
    }
}