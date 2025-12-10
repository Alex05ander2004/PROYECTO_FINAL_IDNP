package com.example.proyectofinal.ui.screens.agenda

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinal.domain.model.Event
import com.example.proyectofinal.domain.usecase.GetAgendaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AgendaViewModel @Inject constructor(
    private val getAgendaUseCase: GetAgendaUseCase
) : ViewModel() {

    // Estado que observará la UI
    private val _uiState = MutableStateFlow<List<Event>>(emptyList())
    val uiState: StateFlow<List<Event>> = _uiState.asStateFlow()

    init {
        loadAgenda()
    }

    private fun loadAgenda() {
        viewModelScope.launch {
            getAgendaUseCase().collect { events ->
                _uiState.value = events
            }
        }
    }
}