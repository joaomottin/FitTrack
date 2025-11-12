package com.example.fittrack.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.fittrack.model.Observacao

@Dao
interface ObservacaoDao {

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertObservacao(observacao: Observacao)

    @Update
    suspend fun updateObservacao(observacao: Observacao)

    @Delete
    suspend fun deleteObservacao(observacao: Observacao)

    @Query("SELECT * FROM observacoes WHERE exercicioId = :exercicioId")
    suspend fun getObservacoesByExercicio(exercicioId: Int): List<Observacao>
}