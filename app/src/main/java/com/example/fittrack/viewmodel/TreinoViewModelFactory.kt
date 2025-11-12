package com.example.fittrack.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fittrack.repository.TreinoRepository

class TreinoViewModelFactory(
    private val repository: TreinoRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TreinoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TreinoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
