package com.example.fittrack.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fittrack.model.Exercicio

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseDetailsScreen(
    exercicio: Exercicio,
    onAddObservacao: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(exercicio.nome) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Séries: ${exercicio.series}")
            Text("Repetições: ${exercicio.repeticoes}")
            Text("Carga: ${exercicio.carga} kg")
            Text("Descanso: ${exercicio.tempoDescanso} s")

            Spacer(Modifier.height(16.dp))
            Button(onClick = onAddObservacao) {
                Text("Adicionar Observação 📝")
            }
        }
    }
}
