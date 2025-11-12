package com.example.fittrack.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fittrack.repository.ObservacaoRepository

class ObservacaoViewModelFactory(
    private val repository: ObservacaoRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ObservacaoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ObservacaoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
