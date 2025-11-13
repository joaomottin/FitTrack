package com.example.fittrack.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    onEditExercicio: (Exercicio) -> Unit = {},
    onBack: () -> Unit
) {
    val exercicios by exercicioViewModel.exercicios.collectAsState()
    val scope = rememberCoroutineScope()
    var busca by remember { mutableStateOf("") }
    var mostrarBusca by remember { mutableStateOf(false) }

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
                },
                actions = {
                    IconButton(onClick = { mostrarBusca = !mostrarBusca }) {
                        Icon(Icons.Default.Search, contentDescription = "Buscar")
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
            if (mostrarBusca) {
                OutlinedTextField(
                    value = busca,
                    onValueChange = {
                        busca = it
                        if (it.isBlank()) {
                            exercicioViewModel.carregarExercicios(treinoId)
                        } else {
                            exercicioViewModel.buscarExercicioPorNome(it)
                        }
                    },
                    label = { Text("Buscar exercício") },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                )
            }

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
                            },
                            onLongPress = {
                                scope.launch { exercicioViewModel.marcarComoConcluido(exercicio) }
                            },
                            onEdit = { onEditExercicio(exercicio) }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExercicioCard(
    exercicio: Exercicio,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    onLongPress: () -> Unit,
    onEdit: () -> Unit = {}
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongPress
            ),
        colors = CardDefaults.cardColors(
            containerColor = if (exercicio.concluido) Color(0xFFE8F5E9) else MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = exercicio.nome,
                    style = MaterialTheme.typography.titleMedium
                )
                if (exercicio.concluido) {
                    Text("✅", style = MaterialTheme.typography.titleMedium)
                }
            }
            Text("Séries: ${exercicio.series}  Reps: ${exercicio.repeticoes}")
            Text("Carga: ${exercicio.carga}kg  Descanso: ${exercicio.tempoDescanso}s")
            Row {
                TextButton(onClick = onEdit) { Text("Editar") }
                TextButton(onClick = onDelete) { Text("Excluir") }
            }
        }
    }
}
