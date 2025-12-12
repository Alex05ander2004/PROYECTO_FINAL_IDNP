// ui/screens/explore/ExploreViewModel.kt
package com.example.proyectofinal.ui.screens.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinal.domain.model.Event
import com.example.proyectofinal.domain.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val repository: EventRepository
) : ViewModel() {

    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events = _events.asStateFlow()

    init {
        // Nos suscribimos a los cambios en el repositorio
        repository.getEvents()
            .onEach { eventList ->
                _events.value = eventList
            }
            .launchIn(viewModelScope)
    }
}
