package com.example.proyectofinal.ui.screens.create

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinal.domain.model.Event
import com.example.proyectofinal.domain.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateEventViewModel @Inject constructor(
    private val repository: EventRepository
) : ViewModel() {

    var title by mutableStateOf("")
    var description by mutableStateOf("")
    var category by mutableStateOf("")
    var price by mutableStateOf("")
    var imageUrl by mutableStateOf("")
    var text by mutableStateOf("")

    // 👇 1. AGREGADO: Variable de estado para la fecha.
    // Por defecto tiene la hora actual del sistema.
    var dateTimestamp by mutableStateOf(System.currentTimeMillis())

    private val _navigationEvent = Channel<Unit>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    fun onSaveEvent() {
        viewModelScope.launch {
            val newEvent = Event(
                id = UUID.randomUUID().toString(),
                title = title,
                description = description,
                category = category,
                price = price,
                imageUrl = imageUrl,
                text = text,

                // 👇 2. AGREGADO: Asignamos la fecha
                dateTimestamp = dateTimestamp,

                // 👇 3. IMPORTANTE: Marcamos que este evento lo creó el usuario
                isUserCreated = true,

                // Por defecto no está en la agenda (o true, si prefieres que se auto-agende)
                isInAgenda = false
            )

            repository.createEvent(newEvent)

            _navigationEvent.send(Unit)
        }
    }
}
