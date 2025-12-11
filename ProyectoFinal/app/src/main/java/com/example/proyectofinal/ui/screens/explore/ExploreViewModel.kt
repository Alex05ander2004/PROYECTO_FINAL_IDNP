// ui/screens/explore/ExploreViewModel.kt
package com.example.proyectofinal.ui.screens.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinal.domain.model.Event
import com.example.proyectofinal.domain.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val eventRepository: EventRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow("Todos")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val allEvents = eventRepository.getEvents()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val uiState: StateFlow<ExploreUiState> = combine(
        allEvents,
        _searchQuery.debounce(300),
        _selectedCategory
    ) { events, query, category ->
        val filtered = events
            .filter { event ->
                if (category == "Todos") true else event.category == category
            }
            .filter { event ->
                if (query.isBlank()) true
                else event.title.contains(query, ignoreCase = true) ||
                        event.description.contains(query, ignoreCase = true)
            }

        ExploreUiState(
            isLoading = false,
            events = filtered,
            categories = listOf("Todos") + events.map { it.category }.distinct(),
            selectedCategory = category,
            searchQuery = query
        )
    }.catch {
        ExploreUiState(errorMessage = "Error al cargar eventos")
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ExploreUiState(isLoading = true))

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onCategorySelected(category: String) {
        _selectedCategory.value = category
    }
}

data class ExploreUiState(
    val events: List<Event> = emptyList(),
    val categories: List<String> = emptyList(),
    val selectedCategory: String = "Todos",
    val searchQuery: String = "",
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)