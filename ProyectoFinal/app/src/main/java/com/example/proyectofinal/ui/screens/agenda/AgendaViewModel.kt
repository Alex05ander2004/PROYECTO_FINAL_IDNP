package com.example.proyectofinal.ui.screens.agenda

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinal.domain.model.Event
import com.example.proyectofinal.domain.usecase.GetAgendaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

// 💡 Clase de Estado: Contiene las dos listas separadas y el estado de carga
// ESTA CLASE ES LA QUE TU SCREEN NECESITA IMPORTAR.
data class AgendaUiState(
    val upcomingEvents: List<Event> = emptyList(),
    val pastEvents: List<Event> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class AgendaViewModel @Inject constructor(
    private val getAgendaUseCase: GetAgendaUseCase
) : ViewModel() {

    // 🔗 El ViewModel expone el objeto AgendaUiState completo
    private val _uiState = MutableStateFlow(AgendaUiState())
    val uiState: StateFlow<AgendaUiState> = _uiState.asStateFlow()

    init {
        loadAgenda()
    }

    private fun loadAgenda() {
        viewModelScope.launch {
            // 1. Recoger el Flow de todos los eventos de la agenda (creados + guardados)
            getAgendaUseCase()
                // 2. Usar .map para transformar esa única lista en nuestro UiState (dos listas)
                .map { allAgendaEvents ->
                    val now = System.currentTimeMillis()

                    // a) FILTRAR PRÓXIMOS (Fecha del evento >= Ahora)
                    val upcoming = allAgendaEvents
                        .filter { it.dateTimestamp >= now }
                        .sortedBy { it.dateTimestamp }

                    // b) FILTRAR PASADOS (Fecha del evento < Ahora)
                    val past = allAgendaEvents
                        .filter { it.dateTimestamp < now }
                        .sortedByDescending { it.dateTimestamp }

                    // 3. Crear y devolver el nuevo estado de la UI
                    AgendaUiState(
                        upcomingEvents = upcoming,
                        pastEvents = past,
                        isLoading = false
                    )
                }
                // 4. Actualizar el StateFlow del ViewModel
                .collect { newState ->
                    _uiState.value = newState
                }
        }
    }
}