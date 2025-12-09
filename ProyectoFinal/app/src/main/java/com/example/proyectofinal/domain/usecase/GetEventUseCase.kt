package com.example.proyectofinal.domain.usecase

import com.example.proyectofinal.domain.model.Event
import com.example.proyectofinal.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow

class GetEventsUseCase(
    private val repository: EventRepository
) {
    operator fun invoke(): Flow<List<Event>> {
        return repository.getEvents()
    }
}
