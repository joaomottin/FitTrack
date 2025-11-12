package com.example.fittrack.model

import androidx.room.*

@Dao
interface ObservacaoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertObservacao(observacao: Observacao)

    @Update
    suspend fun updateObservacao(observacao: Observacao)

    @Delete
    suspend fun deleteObservacao(observacao: Observacao)

    @Query("SELECT * FROM observacoes WHERE exercicioId = :exercicioId")
    suspend fun getObservacoesByExercicio(exercicioId: Int): List<Observacao>
}
