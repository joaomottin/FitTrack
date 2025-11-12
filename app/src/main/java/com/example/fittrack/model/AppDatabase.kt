package com.example.fittrack.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fittrack.dao.ExercicioDao
import com.example.fittrack.model.Observacao
import com.example.fittrack.dao.ObservacaoDao
import com.example.fittrack.dao.TreinoDao

@Database(
    entities = [Treino::class, Exercicio::class, Observacao::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun treinoDao(): TreinoDao
    abstract fun exercicioDao(): ExercicioDao
    abstract fun observacaoDao(): ObservacaoDao
}