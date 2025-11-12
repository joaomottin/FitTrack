package com.example.fittrack.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fittrack.repository.ExercicioRepository

class ExercicioViewModelFactory(
    private val repository: ExercicioRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExercicioViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExercicioViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
