package com.example.proyectofinal.ui.screens.edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinal.domain.model.Event
import com.example.proyectofinal.domain.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class EditEventViewModel @Inject constructor(
    private val repository: EventRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val eventId: String = savedStateHandle["eventId"]!!

    // Estados del formulario
    var title by mutableStateOf("")
    var description by mutableStateOf("")
    var category by mutableStateOf("")
    var price by mutableStateOf("")
    var imageUrl by mutableStateOf("")
    var text by mutableStateOf("")
    var daysUntilEvent by mutableStateOf("")

    // Guardamos el evento original para no perder datos como isUserCreated
    private var originalEvent: Event? = null

    private val _navigationEvent = Channel<Unit>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    init {
        loadEvent()
    }

    private fun loadEvent() {
        viewModelScope.launch {
            repository.getEventById(eventId).collect { event ->
                event?.let {
                    originalEvent = it
                    title = it.title
                    description = it.description
                    category = it.category
                    price = it.price
                    imageUrl = it.imageUrl
                    text = it.text

                    // Calculamos los días restantes inversamente para mostrar en el campo
                    val diff = it.dateTimestamp - System.currentTimeMillis()
                    val days = TimeUnit.MILLISECONDS.toDays(diff).coerceAtLeast(0)
                    daysUntilEvent = days.toString()
                }
            }
        }
    }

    fun onUpdateEvent() {
        viewModelScope.launch {
            val days = daysUntilEvent.toIntOrNull() ?: 0
            val futureTimestamp = System.currentTimeMillis() + TimeUnit.DAYS.toMillis(days.toLong())

            originalEvent?.let { current ->
                val updatedEvent = current.copy(
                    title = title,
                    description = description,
                    category = category,
                    price = price,
                    imageUrl = imageUrl,
                    text = text,
                    dateTimestamp = futureTimestamp
                    // isUserCreated e isInAgenda se mantienen del original
                )

                repository.updateEvent(updatedEvent)
                _navigationEvent.send(Unit)
            }
        }
    }
}