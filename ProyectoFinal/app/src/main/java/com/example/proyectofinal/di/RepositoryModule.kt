package com.example.proyectofinal.di

import com.example.proyectofinal.data.repository.EventRepositoryImpl
import com.example.proyectofinal.domain.repository.EventRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    // @Binds es más eficiente que @Provides para unir Interfaz con Implementación
    @Binds
    @Singleton
    abstract fun bindEventRepository(
        eventRepositoryImpl: EventRepositoryImpl
    ): EventRepository
}