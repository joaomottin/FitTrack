package com.example.fittrack.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.fittrack.model.Treino

@Dao
interface TreinoDao {

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertTreino(treino: Treino)

    @Update
    suspend fun updateTreino(treino: Treino)

    @Delete
    suspend fun deleteTreino(treino: Treino)

    @Query("SELECT * FROM treinos ORDER BY nome ASC")
    suspend fun getAllTreinos(): List<Treino>

    @Query("SELECT * FROM treinos WHERE id = :id")
    suspend fun getTreinoById(id: Int): Treino?

    @Query("SELECT * FROM treinos WHERE nome LIKE '%' || :nome || '%'")
    suspend fun buscarTreinoPorNome(nome: String): List<Treino>

    @Query("SELECT * FROM treinos WHERE grupoMuscular LIKE '%' || :grupoMuscular || '%'")
    suspend fun buscarTreinoPorGrupoMuscular(grupoMuscular: String): List<Treino>
}