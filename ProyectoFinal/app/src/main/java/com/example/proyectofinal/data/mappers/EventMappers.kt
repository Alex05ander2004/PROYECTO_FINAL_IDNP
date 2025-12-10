package com.example.proyectofinal.data.mappers

import com.example.proyectofinal.data.local.entity.EventEntity
import com.example.proyectofinal.domain.model.Event

// 1. De Base de Datos (Entity) a Negocio (Domain)
// Se usa cuando LEES datos para mostrarlos en la UI
fun EventEntity.toDomain(): Event {
    return Event(
        id = id,
        category = category,
        title = title,
        description = description,
        dateTimestamp = dateTimestamp,
        price = price,
        imageUrl = imageUrl,
        text = text,
        isUserCreated = isUserCreated,
        isInAgenda = isInAgenda
    )
}

// 2. De Negocio (Domain) a Base de Datos (Entity)
// Se usa cuando vas a GUARDAR un evento nuevo
fun Event.toEntity(): EventEntity {
    return EventEntity(
        id = id,
        category = category,
        title = title,
        description = description,
        dateTimestamp = dateTimestamp,
        price = price,
        imageUrl = imageUrl,
        text = text,
        isUserCreated = isUserCreated,
        isInAgenda = isInAgenda
    )
}

// 3. (Opcional) Mapear Listas completas
// Útil para cuando Room te devuelve una lista de entidades
fun List<EventEntity>.toDomainList(): List<Event> {
    return map { it.toDomain() }
}