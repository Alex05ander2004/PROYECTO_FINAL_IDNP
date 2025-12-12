# Proyecto Final - Aplicación de Eventos

## Resumen Ejecutivo

Este proyecto es una aplicación Android nativa desarrollada con Kotlin y Jetpack Compose, diseñada para la exploración y gestión de eventos. La arquitectura sigue los principios de **Clean Architecture** y está orientada a features (feature-oriented), utilizando un patrón **MVVM** para la capa de UI.

El trabajo realizado consistió en una refactorización significativa para mejorar la escalabilidad, mantenibilidad y experiencia de usuario. Los cambios clave incluyen:

1.  **Implementación de un Módulo de Ajustes:** Se añadió una pantalla de `SettingsScreen` completa, accesible desde el perfil del usuario.
2.  **Tema Dinámico (Modo Claro/Oscuro):** Se implementó un sistema de tema dinámico que afecta a toda la aplicación. La preferencia del usuario se guarda de forma persistente utilizando **Jetpack DataStore**, asegurando que el tema seleccionado se aplique al iniciar la app.
3.  **Centralización de la Navegación:** Se refactorizó la lógica de navegación, centralizándola en un componente `AppNavigation` para evitar duplicidad de menús y asegurar una estructura de UI consistente y controlada.
4.  **Modernización de la UI:** Se limpiaron y actualizaron todas las pantallas principales (`Explore`, `Agenda`, `Notifications`, `Profile`) para que sean completamente compatibles con el tema dinámico, eliminando colores fijos y estructuras de `Scaffold` duplicadas.

**Tecnologías Principales:**
- **UI:** Jetpack Compose & Material 3
- **Arquitectura:** MVVM, Clean Architecture, Feature-Oriented
- **Inyección de Dependencias:** Hilt
- **Navegación:** Jetpack Navigation for Compose
- **Persistencia:** Jetpack DataStore (para preferencias) y Room (para datos de la app)
- **Asincronía:** Kotlin Coroutines & Flow
- **Carga de Imágenes:** Coil

---

## Interfaces Implementadas

| Interfaz | Propósito | Comportamiento Principal |
| :--- | :--- | :--- |
| **`ExploreScreen`** | Pantalla principal de la aplicación. | Muestra una lista de eventos disponibles. Permite al usuario buscar por texto y filtrar por categorías. |
| **`AgendaScreen`** | Agenda personal del usuario. | Muestra los eventos a los que el usuario se ha inscrito o agendado. |
| **`NotificationsScreen`** | Centro de notificaciones. | Presenta una lista de notificaciones relevantes para el usuario. |
| **`CreateScreen`** | Pantalla de creacion de un nuevo evento. | Muestra un formulario con los campos necesarios para crear el nuevo evento. |
| **`SettingsScreen`** | Centro de configuración de la app. | Permite al usuario personalizar la aplicación. Su funcionalidad clave es el **interruptor de "Modo oscuro"** que cambia el tema de toda la app en tiempo real. |
| **`AppNavigation`** | Orquestador de navegación. | Contiene el `Scaffold` principal, la `BottomNavigationBar` y el `NavHost`. Gestiona la transición entre las pantallas principales y secundarias. |

---

## Instrucciones de Ejecución y Pruebas

Sigue estos pasos para compilar y ejecutar la aplicación en tu entorno de desarrollo.

### Requisitos

- Android Studio Iguana | 2023.2.1 o superior.
- Emulador de Android o dispositivo físico con API 26+.

### Pasos para Ejecutar

1.  **Clonar el Repositorio** (si aplica) o abrir la carpeta del proyecto.
2.  **Abrir en Android Studio**: Selecciona la carpeta raíz del proyecto.
3.  **Sincronizar Gradle**: Espera a que Android Studio descargue todas las dependencias y sincronice el proyecto.
4.  **Ejecutar la Aplicación**: Selecciona la configuración de ejecución `app` y presiona "Run" (▶️), eligiendo un emulador o dispositivo conectado.
5.  **Instalar el apk**: Tambien se puede instalar el archivo .apk que esta al lado de proyecto final para su uso.

### Cómo Probar la Funcionalidad Clave

Para validar la correcta implementación de las características principales:

1.  **Navegación Principal:**
    -   Inicia la aplicación.
    -   Navega entre las diferentes pestañas del menú inferior (`Explorar`, `Mi Agenda`, `Notificaciones`, `Perfil`) y verifica que cada pantalla se muestra correctamente.

2.  **Prueba del Tema Dinámico (Modo Oscuro):**
    -   Ve a la pestaña **Perfil**.
    -   Toca en la opción **"Configuración"**.
    -   Activa el interruptor **"Modo oscuro"**.
    -   **Verificación:**
        -   La pantalla de `Settings` debe cambiar a modo oscuro instantáneamente.
        -   Navega hacia atrás y a otras pantallas. **Toda la aplicación**, incluyendo la barra de navegación inferior, `ExploreScreen`, etc., debe estar en modo oscuro.
        -   Desactiva el interruptor y verifica que toda la app vuelva al modo claro.

3.  **Prueba de Persistencia del Tema:**
    -   Con el modo oscuro activado, cierra completamente la aplicación desde el menú de recientes.
    -   Vuelve a abrir la aplicación.
    -   **Verificación:** La aplicación debe iniciarse directamente en modo oscuro, demostrando que la preferencia se guardó correctamente en DataStore.
