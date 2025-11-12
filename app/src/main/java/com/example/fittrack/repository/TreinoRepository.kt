package com.example.fittrack.repository

import com.example.fittrack.dao.TreinoDao
import com.example.fittrack.model.Treino

class TreinoRepository(private val treinoDao: TreinoDao) {

    suspend fun insertTreino(treino: Treino) {
        treinoDao.insertTreino(treino)
    }

    suspend fun updateTreino(treino: Treino) {
        treinoDao.updateTreino(treino)
    }

    suspend fun deleteTreino(treino: Treino) {
        treinoDao.deleteTreino(treino)
    }

    suspend fun getAllTreinos(): List<Treino> {
        return treinoDao.getAllTreinos()
    }

    suspend fun getTreinoById(id: Int): Treino? {
        return treinoDao.getTreinoById(id)
    }
}
