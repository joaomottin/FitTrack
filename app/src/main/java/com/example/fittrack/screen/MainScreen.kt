package com.example.fittrack.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fittrack.model.Treino
import com.example.fittrack.viewmodel.TreinoViewModel
import com.example.fittrack.api.ZenQuotesApi
import kotlinx.coroutines.launch
import kotlin.collections.get
import com.example.fittrack.util.TranslatorHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    treinoViewModel: TreinoViewModel,
    onTreinoClick: (Treino) -> Unit,
    onAddTreino: () -> Unit,
    onEditTreino: (Treino) -> Unit = {}
) {
    val treinos by treinoViewModel.treinos.collectAsState()
    val scope = rememberCoroutineScope()
    var quote by remember { mutableStateOf("Carregando frase...") }
    var busca by remember { mutableStateOf("") }
    var mostrarBusca by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        // carrega treinos
        treinoViewModel.carregarTreinos()

        // busca quote aleatória da API ZenQuotes
        try {
            val api = ZenQuotesApi.create()
            val response = api.getRandomQuote()
            if (response.isNotEmpty()) {
                val englishQuote = response[0].q
                quote = try {
                    TranslatorHelper.translateToPortuguese(englishQuote)
                } catch (e: Exception) {
                    // fallback para a frase em inglês caso a tradução falhe
                    englishQuote
                }
            }
        } catch (e: Exception) {
            quote = "Push yourself, porque ninguém fará isso por você."
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddTreino) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar treino")
            }
        },
        topBar = {
            TopAppBar(
                title = { Text("FitTrack 🏋️‍♂️") },
                actions = {
                    IconButton(onClick = { mostrarBusca = !mostrarBusca }) {
                        Icon(Icons.Default.Search, contentDescription = "Buscar")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("💬 $quote", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(16.dp))

            if (mostrarBusca) {
                OutlinedTextField(
                    value = busca,
                    onValueChange = {
                        busca = it
                        if (it.isBlank()) {
                            treinoViewModel.carregarTreinos()
                        } else {
                            treinoViewModel.buscarTreinoPorNome(it)
                        }
                    },
                    label = { Text("Buscar treino") },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                )
            }

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
                            },
                            onEdit = { onEditTreino(treino) }
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
    onDelete: () -> Unit,
    onEdit: () -> Unit = {}
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
            Column(modifier = Modifier.weight(1f)) {
                Text(treino.nome, style = MaterialTheme.typography.titleMedium)
                Text("Grupo: ${treino.grupoMuscular}", style = MaterialTheme.typography.bodySmall)
            }
            Row {
                TextButton(onClick = onEdit) { Text("Editar") }
                TextButton(onClick = onDelete) { Text("Excluir") }
            }
        }
    }
}
