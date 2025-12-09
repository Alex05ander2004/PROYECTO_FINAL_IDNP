package com.example.proyectofinal.domain.usecase

import com.example.proyectofinal.domain.model.Event
import com.example.proyectofinal.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow

class GetEventByIdUseCase(
    private val repository: EventRepository
) {
    operator fun invoke(id: String): Flow<Event?> {
        return repository.getEventById(id)
    }
}
