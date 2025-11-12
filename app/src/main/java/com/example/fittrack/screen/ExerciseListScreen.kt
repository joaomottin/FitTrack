package com.example.fittrack.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fittrack.model.Exercicio
import com.example.fittrack.viewmodel.ExercicioViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseListScreen(
    treinoId: Int,
    exercicioViewModel: ExercicioViewModel,
    onExercicioClick: (Exercicio) -> Unit,
    onAddExercicio: () -> Unit,
    onBack: () -> Unit
) {
    val exercicios by exercicioViewModel.exercicios.collectAsState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(treinoId) {
        exercicioViewModel.carregarExercicios(treinoId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Exercícios") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddExercicio) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar exercício")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp)
        ) {
            if (exercicios.isEmpty()) {
                Text("Nenhum exercício adicionado ainda 😅")
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(exercicios) { exercicio ->
                        ExercicioCard(
                            exercicio = exercicio,
                            onClick = { onExercicioClick(exercicio) },
                            onDelete = {
                                scope.launch { exercicioViewModel.deletarExercicio(exercicio) }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ExercicioCard(
    exercicio: Exercicio,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Card(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(exercicio.nome, style = MaterialTheme.typography.titleMedium)
            Text("Séries: ${exercicio.series}  Reps: ${exercicio.repeticoes}")
            Text("Carga: ${exercicio.carga}kg  Descanso: ${exercicio.tempoDescanso}s")
            TextButton(onClick = onDelete) { Text("Excluir") }
        }
    }
}
