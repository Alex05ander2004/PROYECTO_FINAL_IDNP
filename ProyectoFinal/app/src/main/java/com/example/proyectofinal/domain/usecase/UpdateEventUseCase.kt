package com.example.proyectofinal.domain.usecase

import com.example.proyectofinal.domain.model.Event
import com.example.proyectofinal.domain.repository.EventRepository

class UpdateEventUseCase(
    private val repository: EventRepository
) {
    suspend operator fun invoke(event: Event) {
        repository.updateEvent(event)
    }
}