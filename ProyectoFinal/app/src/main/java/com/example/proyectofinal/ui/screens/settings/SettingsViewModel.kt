
package com.example.proyectofinal.ui.screens.settings

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {

    private val _backgroundColor = MutableStateFlow(Color.White)
    val backgroundColor: StateFlow<Color> = _backgroundColor

    fun changeBackgroundColor() {
        _backgroundColor.value = if (_backgroundColor.value == Color.White) Color.LightGray else Color.White
    }
}
