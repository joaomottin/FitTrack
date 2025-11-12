package com.example.fittrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.example.fittrack.model.AppDatabase
import com.example.fittrack.repository.ExercicioRepository
import com.example.fittrack.repository.ObservacaoRepository
import com.example.fittrack.repository.TreinoRepository
import com.example.fittrack.screen.*
import com.example.fittrack.ui.theme.FitTrackTheme
import com.example.fittrack.viewmodel.*

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 🔹 Criando o banco de dados Room
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "fittrack_db"
        ).build()

        // 🔹 Criando os repositories
        val treinoRepository = TreinoRepository(db.treinoDao())
        val exercicioRepository = ExercicioRepository(db.exercicioDao())
        val observacaoRepository = ObservacaoRepository(db.observacaoDao())

        // 🔹 Criando os ViewModels usando Factory
        val treinoViewModel: TreinoViewModel by viewModels {
            TreinoViewModelFactory(treinoRepository)
        }

        val exercicioViewModel: ExercicioViewModel by viewModels {
            ExercicioViewModelFactory(exercicioRepository)
        }

        val observacaoViewModel: ObservacaoViewModel by viewModels {
            ObservacaoViewModelFactory(observacaoRepository)
        }

        // 🔹 Configurando o conteúdo Compose
        setContent {
            FitTrackTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "main"
                ) {
                    // 🏋️ Tela principal (lista de treinos)
                    composable("main") {
                        MainScreen(
                            treinoViewModel = treinoViewModel,
                            onTreinoClick = { treino ->
                                navController.navigate("exerciseList/${treino.id}")
                            },
                            onAddTreino = {
                                navController.navigate("addTreino")
                            }
                        )
                    }

                    // ➕ Tela para adicionar treino
                    composable("addTreino") {
                        AddTreinoScreen(
                            treinoViewModel = treinoViewModel,
                            onBack = { navController.popBackStack() }
                        )
                    }

                    // 💪 Tela de lista de exercícios
                    composable(
                        route = "exerciseList/{treinoId}",
                        arguments = listOf(navArgument("treinoId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val treinoId = backStackEntry.arguments?.getInt("treinoId") ?: 0
                        ExerciseListScreen(
                            treinoId = treinoId,
                            exercicioViewModel = exercicioViewModel,
                            onExercicioClick = { exercicio ->
                                navController.navigate("exerciseDetails/${exercicio.id}")
                            },
                            onAddExercicio = {
                                navController.navigate("addExercicio/$treinoId")
                            },
                            onBack = { navController.popBackStack() }
                        )
                    }

                    // ➕ Tela para adicionar exercício
                    composable(
                        route = "addExercicio/{treinoId}",
                        arguments = listOf(navArgument("treinoId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val treinoId = backStackEntry.arguments?.getInt("treinoId") ?: 0
                        AddExercicioScreen(
                            treinoId = treinoId,
                            exercicioViewModel = exercicioViewModel,
                            onBack = { navController.popBackStack() }
                        )
                    }

                    // 📋 Tela de detalhes do exercício
                    composable(
                        route = "exerciseDetails/{exercicioId}",
                        arguments = listOf(navArgument("exercicioId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val exercicioId = backStackEntry.arguments?.getInt("exercicioId") ?: 0

                        // Coleta o fluxo de exercícios
                        val exerciciosState = exercicioViewModel.exercicios.collectAsState(initial = emptyList())
                        val exercicio = exerciciosState.value.find { it.id == exercicioId }

                        if (exercicio != null) {
                            ExerciseDetailsScreen(
                                exercicio = exercicio,
                                onAddObservacao = {
                                    navController.navigate("observations/${exercicio.id}")
                                },
                                onBack = { navController.popBackStack() }
                            )
                        } else {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Exercício não encontrado ou ainda carregando...")
                            }
                        }
                    }

                    // 📝 Tela de observações
                    composable(
                        route = "observations/{exercicioId}",
                        arguments = listOf(navArgument("exercicioId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val exercicioId = backStackEntry.arguments?.getInt("exercicioId") ?: 0
                        ObservationScreen(
                            exercicioId = exercicioId,
                            observacaoViewModel = observacaoViewModel,
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
