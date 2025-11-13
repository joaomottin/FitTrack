# FitTrack - Aplicativo de Organização de Treinos 🏋️‍♂️

**Integrantes:** João Pedro Mezzadri Mottin e Iago Mayer Bach

## 📱 Sobre o Projeto

FitTrack é um aplicativo Android desenvolvido em Kotlin que ajuda o usuário a montar, acompanhar e registrar seus treinos físicos. O app permite organizar os exercícios por grupos musculares, acompanhar a evolução de cargas e repetições, além de adicionar observações personalizadas. O objetivo é tornar o treino mais eficiente, prático e motivador.

---

## ✨ Funcionalidades Principais

### Requisitos Funcionais Implementados

- **RF01:** Cadastrar exercícios informando nome, grupo muscular, série, repetições, carga e tempo de descanso ✅
- **RF02:** Editar e excluir exercícios e treinos cadastrados ✅
- **RF03:** Exibir lista de treinos salvos organizados por grupo muscular ✅
- **RF04:** Marcar exercício como concluído ao segurar na tela (long press) ✅
- **RF05:** Adicionar observações aos exercícios com lembretes e anotações pessoais ✅
- **RF06:** Armazenamento local com Room Database, funcionando offline ✅
- **RF07:** Citação motivacional diária obtida da API ZenQuotes ✅

### Funcionalidades Adicionais

- **Busca de treinos:** Pesquisar treinos por nome ou grupo muscular
- **Busca de exercícios:** Pesquisar exercícios por nome
- **Edição completa:** Editar tanto treinos quanto exercícios já cadastrados
- **Interface intuitiva:** Design moderno com Jetpack Compose

---

## 🏗️ Arquitetura

O projeto utiliza a arquitetura **MVVM (Model-View-ViewModel)**, garantindo:

- **Separação de responsabilidades:** Camadas bem definidas (Model, View, ViewModel)
- **Manutenibilidade:** Código organizado e fácil de manter
- **Testabilidade:** Lógica de negócio separada da interface
- **Escalabilidade:** Facilita adição de novas funcionalidades

### Camadas da Arquitetura

```
┌─────────────────────────────────────────────┐
│           VIEW (Compose Screens)            │
│  MainScreen, ExerciseListScreen, etc.       │
└──────────────────┬──────────────────────────┘
                   │
┌──────────────────▼──────────────────────────┐
│              VIEWMODEL                      │
│  TreinoViewModel, ExercicioViewModel, etc.  │
└──────────────────┬──────────────────────────┘
                   │
┌──────────────────▼──────────────────────────┐
│             REPOSITORY                      │
│  TreinoRepository, ExercicioRepository      │
└──────────────────┬──────────────────────────┘
                   │
┌──────────────────▼──────────────────────────┐
│          DATA SOURCE (Room DAO)             │
│  TreinoDao, ExercicioDao, ObservacaoDao     │
└──────────────────┬──────────────────────────┘
                   │
┌──────────────────▼──────────────────────────┐
│         DATABASE (Room SQLite)              │
│  Treino, Exercicio, Observacao              │
└─────────────────────────────────────────────┘
```

---

## 🗄️ Banco de Dados

### Estrutura das Entidades

#### 1. Treino
```kotlin
@Entity(tableName = "treinos")
data class Treino(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nome: String,
    val grupoMuscular: String
)
```

#### 2. Exercicio
```kotlin
@Entity(tableName = "exercicios")
data class Exercicio(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val treinoId: Int, // FK → Treino
    val nome: String,
    val series: Int,
    val repeticoes: Int,
    val carga: Float,
    val tempoDescanso: Int,
    val concluido: Boolean = false
)
```

#### 3. Observacao
```kotlin
@Entity(tableName = "observacoes")
data class Observacao(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val exercicioId: Int, // FK → Exercicio
    val texto: String
)
```

### Diagrama de Relacionamento

```
┌──────────────┐
│    Treino    │
│              │
│  id (PK)     │
│  nome        │
│  grupoMusc.  │
└──────┬───────┘
       │
       │ 1:N
       │
┌──────▼───────┐
│  Exercicio   │
│              │
│  id (PK)     │
│  treinoId(FK)│
│  nome        │
│  series      │
│  repeticoes  │
│  carga       │
│  tempoDesc.  │
│  concluido   │
└──────┬───────┘
       │
       │ 1:N
       │
┌──────▼───────┐
│  Observacao  │
│              │
│  id (PK)     │
│  exercicioId │
│  texto       │
└──────────────┘
```

### Operações CRUD

Todas as entidades possuem operações completas de:
- **Create:** Inserir novos registros
- **Read:** Buscar e listar registros
- **Update:** Atualizar registros existentes
- **Delete:** Remover registros

### Buscas Específicas Implementadas

```kotlin
// TreinoDao
@Query("SELECT * FROM treinos WHERE nome LIKE '%' || :nome || '%'")
suspend fun buscarTreinoPorNome(nome: String): List<Treino>

@Query("SELECT * FROM treinos WHERE grupoMuscular LIKE '%' || :grupoMuscular || '%'")
suspend fun buscarTreinoPorGrupoMuscular(grupoMuscular: String): List<Treino>

// ExercicioDao
@Query("SELECT * FROM exercicios WHERE nome LIKE '%' || :nome || '%'")
suspend fun buscarExercicioPorNome(nome: String): List<Exercicio>
```

---

## 🌐 Integração com API

### API Utilizada: ZenQuotes

**Endpoint:** `https://zenquotes.io/api/random`  
**Método:** GET  
**Autenticação:** Não requer

#### Descrição
A API ZenQuotes fornece citações motivacionais aleatórias. O app faz uma requisição HTTP toda vez que o usuário abre a tela principal.

#### Exemplo de Resposta
```json
[
  {
    "q": "Push yourself, because no one else is going to do it for you.",
    "a": "Unknown",
    "h": "<blockquote>...</blockquote>"
  }
]
```

#### Implementação
```kotlin
interface ZenQuotesApiService {
    @GET("api/random")
    suspend fun getRandomQuote(): List<Quote>
}

data class Quote(
    val q: String, // Texto da citação
    val a: String  // Autor
)
```

A citação é traduzida para português usando a classe `TranslatorHelper` e exibida na parte superior da tela principal.

---

## 📱 Navegação Entre Telas

O aplicativo utiliza **Jetpack Navigation Compose** com uma única Activity e múltiplas rotas:

### Fluxo de Navegação

```
MainScreen (Lista de Treinos)
    ├── AddTreinoScreen (Adicionar Treino)
    ├── EditTreinoScreen (Editar Treino)
    └── ExerciseListScreen (Lista de Exercícios)
            ├── AddExercicioScreen (Adicionar Exercício)
            ├── EditExercicioScreen (Editar Exercício)
            └── ExerciseDetailsScreen (Detalhes do Exercício)
                    └── ObservationScreen (Observações)
```

### Rotas Implementadas

1. **`"main"`** - Tela principal com lista de treinos
2. **`"addTreino"`** - Formulário para adicionar novo treino
3. **`"editTreino/{treinoId}"`** - Formulário para editar treino existente
4. **`"exerciseList/{treinoId}"`** - Lista de exercícios de um treino específico
5. **`"addExercicio/{treinoId}"`** - Formulário para adicionar novo exercício
6. **`"editExercicio/{exercicioId}"`** - Formulário para editar exercício existente
7. **`"exerciseDetails/{exercicioId}"`** - Detalhes de um exercício específico
8. **`"observations/{exercicioId}"`** - Lista e formulário de observações

---

## 🛠️ Tecnologias e Bibliotecas

### Core
- **Kotlin** - Linguagem de programação
- **Android Studio** - IDE de desenvolvimento
- **Jetpack Compose** - Framework de UI declarativa

### Arquitetura e Estado
- **ViewModel** - Gerenciamento de estado e lógica de apresentação
- **StateFlow** - Gerenciamento reativo de estado
- **Coroutines** - Programação assíncrona

### Persistência
- **Room Database** - Banco de dados SQLite local
- **Room DAOs** - Data Access Objects para acesso aos dados

### Navegação
- **Navigation Compose** - Navegação declarativa entre telas

### Rede
- **Retrofit** - Cliente HTTP para consumo de APIs REST
- **Gson** - Serialização/Deserialização JSON

### Tradução
- **Google Cloud Translation API** - Tradução automática de citações

---

## 📦 Estrutura do Projeto

```
app/src/main/java/com/example/fittrack/
├── api/
│   ├── ZenQuotesApi.kt          # Interface Retrofit para API
├── dao/
│   ├── TreinoDao.kt             # DAO para Treinos
│   ├── ExercicioDao.kt          # DAO para Exercícios
│   └── ObservacaoDao.kt         # DAO para Observações
├── model/
│   ├── Treino.kt                # Entidade Treino
│   ├── Exercicio.kt             # Entidade Exercício
│   ├── Observacao.kt            # Entidade Observação
│   └── AppDatabase.kt           # Configuração do Room
├── repository/
│   ├── TreinoRepository.kt      # Repositório de Treinos
│   ├── ExercicioRepository.kt   # Repositório de Exercícios
│   └── ObservacaoRepository.kt  # Repositório de Observações
├── screen/
│   ├── MainScreen.kt            # Tela principal
│   ├── AddTreinoScreen.kt       # Adicionar treino
│   ├── EditTreinoScreen.kt      # Editar treino
│   ├── ExerciseListScreen.kt    # Lista de exercícios
│   ├── AddExercicioScreen.kt    # Adicionar exercício
│   ├── EditExercicioScreen.kt   # Editar exercício
│   ├── ExerciseDetailsScreen.kt # Detalhes do exercício
│   └── ObservationScreen.kt     # Observações
├── viewmodel/
│   ├── TreinoViewModel.kt       # ViewModel de Treinos
│   ├── ExercicioViewModel.kt    # ViewModel de Exercícios
│   ├── ObservacaoViewModel.kt   # ViewModel de Observações
│   └── *ViewModelFactory.kt     # Factories dos ViewModels
├── ui/theme/
│   └── Theme.kt                 # Tema do aplicativo
├── util/
│   └── TranslatorHelper.kt      # Helper para tradução
└── MainActivity.kt              # Activity principal
```

---

## 🚀 Como Executar

### Pré-requisitos

- Android Studio Arctic Fox ou superior
- JDK 11 ou superior
- Android SDK (API Level 24+)
- Emulador Android ou dispositivo físico

### Passos para Execução

1. **Clone ou baixe o projeto**

2. **Configure o Android SDK**
   - Abra o arquivo `local.properties` na raiz do projeto
   - Adicione o caminho do seu Android SDK:
   ```properties
   sdk.dir=C\:\\Users\\SEU_USUARIO\\AppData\\Local\\Android\\Sdk
   ```

3. **Abra o projeto no Android Studio**
   - File → Open → Selecione a pasta do projeto

4. **Sincronize as dependências**
   - O Android Studio irá baixar automaticamente as dependências do Gradle

5. **Execute o aplicativo**
   - Clique no botão "Run" (▶️) ou pressione Shift+F10
   - Selecione um emulador ou dispositivo físico

---

## 📝 Observações de Desenvolvimento

### Decisões Técnicas

1. **Jetpack Compose:** Escolhido por ser a abordagem moderna recomendada pelo Google para desenvolvimento de UI no Android.

2. **Navigation Compose:** Permite navegação declarativa sem múltiplas Activities, reduzindo complexidade e melhorando performance.

3. **Room Database:** Fornece uma camada de abstração sobre SQLite, facilitando operações de banco de dados com type-safety.

4. **Arquitetura MVVM:** Separa lógica de negócio da UI, facilitando testes e manutenção.

5. **StateFlow:** Escolhido em vez de LiveData por ter melhor integração com Compose e Coroutines.

6. **Fallback de Migração:** Usado `fallbackToDestructiveMigration()` para facilitar desenvolvimento, mas em produção seria necessário implementar migrações adequadas.

### Desafios Enfrentados

1. **Sincronização de estado:** Garantir que as mudanças no banco de dados sejam refletidas imediatamente na UI.

2. **Navegação com argumentos:** Passar IDs entre telas e recuperar objetos do ViewModel.

3. **Long press para marcar como concluído:** Implementar gesture recognition com `combinedClickable`.

4. **Tradução de citações:** Integrar API de tradução para converter frases do inglês para português.

---

## 👥 Contribuições dos Integrantes

### João Pedro Mezzadri Mottin
- Implementação da arquitetura MVVM
- Desenvolvimento das telas de treinos (Main, Add, Edit)
- Integração com API ZenQuotes
- Implementação do sistema de busca
- Configuração do Room Database
- Documentação do projeto

### Iago Mayer Bach
- Desenvolvimento das telas de exercícios (List, Details, Add, Edit)
- Implementação da funcionalidade de marcar como concluído
- Desenvolvimento da tela de observações
- Design da interface do usuário
- Testes de funcionalidades
- Revisão de código

---

## 📄 Licença

Este projeto foi desenvolvido para fins acadêmicos como trabalho da disciplina de Desenvolvimento Mobile.

---

## 📞 Contato

Para dúvidas ou sugestões sobre o projeto:
- João Pedro Mezzadri Mottin - [joao.mottin@proton.me]
- Iago Mayer Bach - [mayerbachiago@gmail.com]

---

**Desenvolvido com ❤️ por João Pedro e Iago**


