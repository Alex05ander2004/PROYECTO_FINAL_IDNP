package com.example.proyectofinal.domain.repository

import com.example.proyectofinal.domain.model.Event
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    fun getEvents(): Flow<List<Event>>
    fun getEventById(id: String): Flow<Event?>
    suspend fun createEvent(event: Event)
    suspend fun updateEvent(event: Event)
    suspend fun deleteEvent(event: Event)
    fun getAgendaEvents(): Flow<List<Event>>
}
