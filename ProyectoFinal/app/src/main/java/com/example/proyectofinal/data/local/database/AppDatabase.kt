package com.example.proyectofinal.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.proyectofinal.data.local.entity.EventEntity
import com.example.proyectofinal.data.local.dao.EventDao

@Database(
    entities = [EventEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao
}
