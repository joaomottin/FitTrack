package com.example.fittrack.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "exercicios",
    foreignKeys = [
        ForeignKey(
            entity = Treino::class,
            parentColumns = ["id"],
            childColumns = ["treinoId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Exercicio(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val treinoId: Int,
    val nome: String,
    val series: Int,
    val repeticoes: Int,
    val carga: Float,
    val tempoDescanso: Int,
    val concluido: Boolean = false
)
