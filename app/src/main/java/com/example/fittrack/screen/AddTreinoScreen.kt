package com.example.fittrack.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fittrack.model.Treino
import com.example.fittrack.viewmodel.TreinoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTreinoScreen(
    treinoViewModel: TreinoViewModel,
    onBack: () -> Unit
) {
    var nome by remember { mutableStateOf("") }
    var grupoMuscular by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Adicionar Treino") },
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
                label = { Text("Nome do Treino") },
                placeholder = { Text("Ex: Treino A, Treino de Peito...") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = grupoMuscular,
                onValueChange = { grupoMuscular = it },
                label = { Text("Grupo Muscular") },
                placeholder = { Text("Ex: Peito, Costas, Pernas, Superior...") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if (nome.isNotBlank() && grupoMuscular.isNotBlank()) {
                        treinoViewModel.adicionarTreino(
                            Treino(
                                nome = nome,
                                grupoMuscular = grupoMuscular
                            )
                        )
                        onBack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = nome.isNotBlank() && grupoMuscular.isNotBlank()
            ) {
                Text("Salvar Treino")
            }
        }
    }
}

