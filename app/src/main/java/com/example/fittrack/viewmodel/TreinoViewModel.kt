package com.example.fittrack.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fittrack.model.Treino
import com.example.fittrack.repository.TreinoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TreinoViewModel(private val repository: TreinoRepository) : ViewModel() {

    private val _treinos = MutableStateFlow<List<Treino>>(emptyList())
    val treinos: StateFlow<List<Treino>> = _treinos

    fun carregarTreinos() {
        viewModelScope.launch {
            _treinos.value = repository.getAllTreinos()
        }
    }

    fun adicionarTreino(treino: Treino) {
        viewModelScope.launch {
            repository.insertTreino(treino)
            carregarTreinos()
        }
    }

    fun atualizarTreino(treino: Treino) {
        viewModelScope.launch {
            repository.updateTreino(treino)
            carregarTreinos()
        }
    }

    fun deletarTreino(treino: Treino) {
        viewModelScope.launch {
            repository.deleteTreino(treino)
            carregarTreinos()
        }
    }

    fun buscarTreinoPorNome(nome: String) {
        viewModelScope.launch {
            _treinos.value = repository.buscarTreinoPorNome(nome)
        }
    }

    fun buscarTreinoPorGrupoMuscular(grupoMuscular: String) {
        viewModelScope.launch {
            _treinos.value = repository.buscarTreinoPorGrupoMuscular(grupoMuscular)
        }
    }
}
