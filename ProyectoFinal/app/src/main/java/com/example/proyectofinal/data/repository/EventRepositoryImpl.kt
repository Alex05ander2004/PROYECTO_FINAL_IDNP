package com.example.proyectofinal.data.repository

import com.example.proyectofinal.data.local.dao.EventDao
import com.example.proyectofinal.data.mappers.toDomain
import com.example.proyectofinal.data.mappers.toEntity
import com.example.proyectofinal.data.source.StaticEventDataSource
import com.example.proyectofinal.domain.model.Event
import com.example.proyectofinal.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val dao: EventDao
    // Ya no inyectamos la fuente estática porque es un 'object' global
) : EventRepository {

    override fun getEvents(): Flow<List<Event>> {
        return dao.getAll().map { entities ->
            val dbEvents = entities.map { it.toDomain() }

            // Accedemos directamente al 'object' StaticEventDataSource
            val staticEvents = StaticEventDataSource.events

            // Unimos ambas listas
            staticEvents + dbEvents
        }
    }

    override fun getEventById(id: String): Flow<Event?> {
        return dao.getById(id).map { entity ->
            // Si no está en BD, busca en estáticos (Fallback)
            entity?.toDomain() ?: StaticEventDataSource.events.find { it.id == id }
        }
    }

    override suspend fun createEvent(event: Event) {
        dao.insert(event.toEntity())
    }

    override suspend fun updateEvent(event: Event) {
        dao.update(event.toEntity())
    }

    override suspend fun deleteEvent(event: Event) {
        dao.delete(event.toEntity())
    }

    override fun getAgendaEvents(): Flow<List<Event>> {
        return dao.getAgendaEvents().map { entities ->
            entities.map { it.toDomain() }
        }
    }
}
