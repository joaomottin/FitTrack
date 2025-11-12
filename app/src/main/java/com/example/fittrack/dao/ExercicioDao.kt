package com.example.fittrack.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.fittrack.model.Exercicio

@Dao
interface ExercicioDao {

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
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