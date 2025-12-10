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

// 1. Agregamos @Inject para que Hilt sepa cómo construir esto automáticamente
class EventRepositoryImpl @Inject constructor(
    private val dao: EventDao,
    private val staticSource: StaticEventDataSource // 2. Inyectamos los datos estáticos también
) : EventRepository {

    // 3. CAMBIO IMPORTANTE: Usamos Flow para reactividad
    override fun getEvents(): Flow<List<Event>> {
        // Observamos la base de datos (dao.getAll devuelve Flow)
        return dao.getAll().map { entities ->

            // A. Convertimos los eventos de la BD a Dominio
            val dbEvents = entities.map { it.toDomain() }

            // B. Obtenemos los eventos estáticos
            val staticEvents = staticSource.getBaseEvents()

            // C. ¡FUSIÓN! Unimos ambas listas antes de entregarlas a la UI
            staticEvents + dbEvents
        }
    }

    override fun getEventById(id: String): Flow<Event?> {
        // Aquí también podrías buscar primero en estáticos y luego en BD si quisieras
        // Por simplicidad, observamos la BD
        return dao.getById(id).map { entity ->
            // Si no está en BD, busca en estáticos (Fallback)
            entity?.toDomain() ?: staticSource.getBaseEvents().find { it.id == id }
        }
    }

    // Las operaciones de escritura se mantienen igual (suspend)
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
