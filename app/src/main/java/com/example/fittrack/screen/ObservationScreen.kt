package com.example.fittrack.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fittrack.model.Observacao
import com.example.fittrack.viewmodel.ObservacaoViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ObservationScreen(
    exercicioId: Int,
    observacaoViewModel: ObservacaoViewModel,
    onBack: () -> Unit
) {
    val observacoes by observacaoViewModel.observacoes.collectAsState()
    val scope = rememberCoroutineScope()
    var texto by remember { mutableStateOf("") }

    LaunchedEffect(exercicioId) {
        observacaoViewModel.carregarObservacoes(exercicioId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Observações") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = texto,
                onValueChange = { texto = it },
                label = { Text("Digite uma observação...") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    scope.launch {
                        observacaoViewModel.adicionarObservacao(
                            Observacao(exercicioId = exercicioId, texto = texto)
                        )
                        texto = ""
                    }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Salvar")
            }

            Divider(Modifier.padding(vertical = 8.dp))
            Text("Minhas observações:", style = MaterialTheme.typography.titleMedium)

            if (observacoes.isEmpty()) {
                Text("Nenhuma observação ainda.")
            } else {
                observacoes.forEach {
                    Text("• ${it.texto}")
                }
            }
        }
    }
}
