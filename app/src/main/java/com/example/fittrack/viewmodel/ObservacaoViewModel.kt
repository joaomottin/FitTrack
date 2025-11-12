package com.example.fittrack.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fittrack.model.Observacao
import com.example.fittrack.repository.ObservacaoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ObservacaoViewModel(private val repository: ObservacaoRepository) : ViewModel() {

    private val _observacoes = MutableStateFlow<List<Observacao>>(emptyList())
    val observacoes: StateFlow<List<Observacao>> = _observacoes

    fun carregarObservacoes(exercicioId: Int) {
        viewModelScope.launch {
            _observacoes.value = repository.getObservacoesByExercicio(exercicioId)
        }
    }

    fun adicionarObservacao(observacao: Observacao) {
        viewModelScope.launch {
            repository.insertObservacao(observacao)
            carregarObservacoes(observacao.exercicioId)
        }
    }

    fun atualizarObservacao(observacao: Observacao) {
        viewModelScope.launch {
            repository.updateObservacao(observacao)
            carregarObservacoes(observacao.exercicioId)
        }
    }

    fun deletarObservacao(observacao: Observacao) {
        viewModelScope.launch {
            repository.deleteObservacao(observacao)
            carregarObservacoes(observacao.exercicioId)
        }
    }
}
