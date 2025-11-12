package com.example.fittrack.model

import androidx.room.*

@Dao
interface TreinoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTreino(treino: Treino)

    @Update
    suspend fun updateTreino(treino: Treino)

    @Delete
    suspend fun deleteTreino(treino: Treino)

    @Query("SELECT * FROM treinos ORDER BY nome ASC")
    suspend fun getAllTreinos(): List<Treino>

    @Query("SELECT * FROM treinos WHERE id = :id")
    suspend fun getTreinoById(id: Int): Treino?
}
