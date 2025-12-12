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
import java.util.concurrent.TimeUnit
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

    // 👇 NUEVO CAMPO: Guardamos el input del usuario (ej: "5")
    var daysUntilEvent by mutableStateOf("")

    private val _navigationEvent = Channel<Unit>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    fun onSaveEvent() {
        viewModelScope.launch {
            // 1. Convertimos el texto "5" a número entero (o 0 si está vacío)
            val days = daysUntilEvent.toIntOrNull() ?: 0

            // 2. Calculamos la fecha futura: Ahora + (Días * milisegundos en un día)
            val futureTimestamp = System.currentTimeMillis() + TimeUnit.DAYS.toMillis(days.toLong())

            val newEvent = Event(
                id = UUID.randomUUID().toString(),
                title = title,
                description = description,
                category = category,
                price = price,
                imageUrl = imageUrl,
                text = text,

                // 👇 Usamos la fecha calculada
                dateTimestamp = futureTimestamp,

                isUserCreated = true,
                isInAgenda = false
            )

            repository.createEvent(newEvent)
            _navigationEvent.send(Unit)
        }
    }
}