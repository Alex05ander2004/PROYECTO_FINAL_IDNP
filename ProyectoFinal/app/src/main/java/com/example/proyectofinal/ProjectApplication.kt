package com.example.proyectofinal

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp // ⚠️ ¡Esta anotación es la que activa Hilt en toda la app!
class ProjectApplication : Application()