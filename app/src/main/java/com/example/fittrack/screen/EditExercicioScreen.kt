package com.example.fittrack.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.fittrack.model.Exercicio
import com.example.fittrack.viewmodel.ExercicioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditExercicioScreen(
    exercicio: Exercicio,
    exercicioViewModel: ExercicioViewModel,
    onBack: () -> Unit
) {
    var nome by remember { mutableStateOf(exercicio.nome) }
    var series by remember { mutableStateOf(exercicio.series.toString()) }
    var repeticoes by remember { mutableStateOf(exercicio.repeticoes.toString()) }
    var carga by remember { mutableStateOf(exercicio.carga.toString()) }
    var tempoDescanso by remember { mutableStateOf(exercicio.tempoDescanso.toString()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar Exercício") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Text("←", style = MaterialTheme.typography.headlineMedium)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome do Exercício") },
                placeholder = { Text("Ex: Supino Reto, Agachamento...") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = series,
                onValueChange = { series = it },
                label = { Text("Séries") },
                placeholder = { Text("Ex: 3, 4, 5...") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = repeticoes,
                onValueChange = { repeticoes = it },
                label = { Text("Repetições") },
                placeholder = { Text("Ex: 10, 12, 15...") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = carga,
                onValueChange = { carga = it },
                label = { Text("Carga (kg)") },
                placeholder = { Text("Ex: 20, 40, 60...") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = tempoDescanso,
                onValueChange = { tempoDescanso = it },
                label = { Text("Tempo de Descanso (segundos)") },
                placeholder = { Text("Ex: 60, 90, 120...") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if (nome.isNotBlank() && series.isNotBlank() && repeticoes.isNotBlank()) {
                        exercicioViewModel.atualizarExercicio(
                            exercicio.copy(
                                nome = nome,
                                series = series.toIntOrNull() ?: exercicio.series,
                                repeticoes = repeticoes.toIntOrNull() ?: exercicio.repeticoes,
                                carga = carga.toFloatOrNull() ?: exercicio.carga,
                                tempoDescanso = tempoDescanso.toIntOrNull() ?: exercicio.tempoDescanso
                            )
                        )
                        onBack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = nome.isNotBlank() && series.isNotBlank() && repeticoes.isNotBlank()
            ) {
                Text("Salvar Alterações")
            }
        }
    }
}

