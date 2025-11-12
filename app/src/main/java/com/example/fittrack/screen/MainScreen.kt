package com.example.fittrack.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fittrack.model.Treino
import com.example.fittrack.viewmodel.TreinoViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    treinoViewModel: TreinoViewModel,
    onTreinoClick: (Treino) -> Unit,
    onAddTreino: () -> Unit
) {
    val treinos by treinoViewModel.treinos.collectAsState()
    val scope = rememberCoroutineScope()
    var quote by remember { mutableStateOf("Carregando frase...") }

    LaunchedEffect(Unit) {
        quote = "Push yourself, because no one else is going to do it for you." // depois: API ZenQuotes
        treinoViewModel.carregarTreinos()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddTreino) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar treino")
            }
        },
        topBar = { TopAppBar(title = { Text("FitTrack 🏋️‍♂️") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("💬 $quote", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(16.dp))

            if (treinos.isEmpty()) {
                Box(Modifier.fillMaxSize(), Alignment.Center) {
                    Text("Nenhum treino cadastrado ainda 😅")
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(treinos) { treino ->
                        TreinoCard(
                            treino = treino,
                            onClick = { onTreinoClick(treino) },
                            onDelete = {
                                scope.launch { treinoViewModel.deletarTreino(treino) }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TreinoCard(
    treino: Treino,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(treino.nome, style = MaterialTheme.typography.titleMedium)
                Text("Grupo: ${treino.grupoMuscular}", style = MaterialTheme.typography.bodySmall)
            }
            TextButton(onClick = onDelete) { Text("Excluir") }
        }
    }
}
