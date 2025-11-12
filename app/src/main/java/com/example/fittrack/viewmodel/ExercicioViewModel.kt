package com.example.fittrack.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fittrack.model.Exercicio
import com.example.fittrack.repository.ExercicioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ExercicioViewModel(private val repository: ExercicioRepository) : ViewModel() {

    private val _exercicios = MutableStateFlow<List<Exercicio>>(emptyList())
    val exercicios: StateFlow<List<Exercicio>> = _exercicios

    fun carregarExercicios(treinoId: Int) {
        viewModelScope.launch {
            _exercicios.value = repository.getExerciciosByTreino(treinoId)
        }
    }

    fun adicionarExercicio(exercicio: Exercicio) {
        viewModelScope.launch {
            repository.insertExercicio(exercicio)
            carregarExercicios(exercicio.treinoId)
        }
    }

    fun atualizarExercicio(exercicio: Exercicio) {
        viewModelScope.launch {
            repository.updateExercicio(exercicio)
            carregarExercicios(exercicio.treinoId)
        }
    }

    fun deletarExercicio(exercicio: Exercicio) {
        viewModelScope.launch {
            repository.deleteExercicio(exercicio)
            carregarExercicios(exercicio.treinoId)
        }
    }
}
