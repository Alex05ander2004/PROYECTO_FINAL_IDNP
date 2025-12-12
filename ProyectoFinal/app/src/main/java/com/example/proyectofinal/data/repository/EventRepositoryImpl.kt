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
    private val dao: EventDao,
    private val staticDataSource: StaticEventDataSource
) : EventRepository {

    override fun getEvents(): Flow<List<Event>> {
        return dao.getAll().map { entities ->
            val dbEvents = entities.map { it.toDomain() }
            val staticEvents = staticDataSource.getBaseEvents()

            // Combinamos: Si un evento estático ya está en BD (por ID), usamos la versión de BD
            // para que se mantengan los cambios (como isInAgenda = true)
            val mergedEvents = staticEvents.map { static ->
                dbEvents.find { it.id == static.id } ?: static
            } + dbEvents.filter { db -> staticEvents.none { it.id == db.id } }

            mergedEvents
        }
    }

    override fun getEventById(id: String): Flow<Event?> {
        return dao.getById(id).map { entity ->
            // 1. Si existe en la BD (porque ya le dimos guardar), usamos ese.
            // 2. Si no, lo buscamos en los estáticos.
            entity?.toDomain() ?: staticDataSource.getBaseEvents().find { it.id == id }
        }
    }

    override fun getAgendaEvents(): Flow<List<Event>> {
        // Aquí también debemos tener cuidado. Si solo consultamos la BD,
        // los estáticos nunca aparecerán hasta que los guardemos.
        // Pero para la "Agenda", esto es correcto: solo queremos ver lo que hemos guardado o creado.
        return dao.getAgendaEvents().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun createEvent(event: Event) {
        dao.insert(event.toEntity())
    }

    override suspend fun updateEvent(event: Event) {
        // 🚨 CAMBIO CLAVE AQUÍ:
        // Usamos 'insert' en lugar de 'update'.
        // Al tener OnConflictStrategy.REPLACE en el DAO, esto funciona como un "Guardar o Actualizar".
        // Si el evento era estático (no existía en BD), ahora se GUARDARÁ en la BD con el nuevo estado.
        dao.insert(event.toEntity())
    }

    override suspend fun deleteEvent(event: Event) {
        dao.delete(event.toEntity())
    }
}