package com.example.fittrack.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "observacoes",
    foreignKeys = [
        ForeignKey(
            entity = Exercicio::class,
            parentColumns = ["id"],
            childColumns = ["exercicioId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Observacao(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val exercicioId: Int,
    val texto: String
)
