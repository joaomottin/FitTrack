package com.example.fittrack.repository

import com.example.fittrack.dao.ExercicioDao
import com.example.fittrack.model.Exercicio

class ExercicioRepository(private val exercicioDao: ExercicioDao) {

    suspend fun insertExercicio(exercicio: Exercicio) {
        exercicioDao.insertExercicio(exercicio)
    }

    suspend fun updateExercicio(exercicio: Exercicio) {
        exercicioDao.updateExercicio(exercicio)
    }

    suspend fun deleteExercicio(exercicio: Exercicio) {
        exercicioDao.deleteExercicio(exercicio)
    }

    suspend fun getExerciciosByTreino(treinoId: Int): List<Exercicio> {
        return exercicioDao.getExerciciosByTreino(treinoId)
    }

    suspend fun getExercicioById(id: Int): Exercicio? {
        return exercicioDao.getExercicioById(id)
    }

    suspend fun buscarExercicioPorNome(nome: String): List<Exercicio> {
        return exercicioDao.buscarExercicioPorNome(nome)
    }
}
