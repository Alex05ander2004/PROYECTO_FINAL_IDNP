package com.example.proyectofinal.di

import android.content.Context
import androidx.room.Room
import com.example.proyectofinal.data.local.database.AppDatabase
import com.example.proyectofinal.data.local.dao.EventDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // Esto vivirá tanto como la App
object DatabaseModule {

    // 1. Enseñar a crear la Base de Datos
    @Provides
    @Singleton // Para que solo exista UNA instancia de la BD en toda la app
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "proyectofinal_database" // Nombre del archivo .db en el celular
        ).build()
    }

    // 2. Enseñar a obtener el DAO (Hilt sacará la db del método de arriba)
    @Provides
    @Singleton
    fun provideEventDao(db: AppDatabase): EventDao {
        return db.eventDao()
    }
}