package com.example.fittrack.model

import androidx.room.*

@Dao
interface ExercicioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercicio(exercicio: Exercicio)

    @Update
    suspend fun updateExercicio(exercicio: Exercicio)

    @Delete
    suspend fun deleteExercicio(exercicio: Exercicio)

    @Query("SELECT * FROM exercicios WHERE treinoId = :treinoId")
    suspend fun getExerciciosByTreino(treinoId: Int): List<Exercicio>

    @Query("SELECT * FROM exercicios WHERE id = :id")
    suspend fun getExercicioById(id: Int): Exercicio?
}
