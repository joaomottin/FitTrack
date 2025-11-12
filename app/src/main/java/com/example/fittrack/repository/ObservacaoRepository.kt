package com.example.fittrack.repository

import com.example.fittrack.dao.ObservacaoDao
import com.example.fittrack.model.Observacao

class ObservacaoRepository(private val observacaoDao: ObservacaoDao) {

    suspend fun insertObservacao(observacao: Observacao) {
        observacaoDao.insertObservacao(observacao)
    }

    suspend fun updateObservacao(observacao: Observacao) {
        observacaoDao.updateObservacao(observacao)
    }

    suspend fun deleteObservacao(observacao: Observacao) {
        observacaoDao.deleteObservacao(observacao)
    }

    suspend fun getObservacoesByExercicio(exercicioId: Int): List<Observacao> {
        return observacaoDao.getObservacoesByExercicio(exercicioId)
    }
}
