# 🔧 Guia de Configuração e Resolução de Problemas

## ⚠️ IMPORTANTE: Configurar Android SDK

### Erro: "SDK location not found"

Este erro ocorre porque o arquivo `local.properties` não está configurado ou está com o caminho errado do Android SDK.

### ✅ Solução Passo a Passo

#### **Opção 1: Deixar o Android Studio configurar automaticamente**

1. Abra o projeto no Android Studio
2. O Android Studio irá detectar que falta o `local.properties`
3. Clique em **"Sync Project with Gradle Files"** (ícone do elefante)
4. O Android Studio criará o arquivo automaticamente

#### **Opção 2: Criar manualmente o arquivo local.properties**

1. Navegue até a pasta raiz do projeto: `C:\Users\joaop\Downloads\FitTrack\`

2. Crie um arquivo chamado `local.properties` (se não existir)

3. Adicione a seguinte linha (ajuste para o seu caminho):

**Windows:**
```properties
sdk.dir=C\:\\Users\\SEU_USUARIO\\AppData\\Local\\Android\\Sdk
```

**Exemplo real:**
```properties
sdk.dir=C\:\\Users\\joaop\\AppData\\Local\\Android\\Sdk
```

**Linux/Mac:**
```properties
sdk.dir=/Users/SEU_USUARIO/Library/Android/sdk
```

4. Salve o arquivo

5. No Android Studio, clique em **File → Sync Project with Gradle Files**

### 🔍 Como Encontrar o Caminho do Android SDK

#### No Android Studio:
1. Abra o Android Studio
2. Vá em **File → Settings** (ou **Ctrl+Alt+S**)
3. Navegue para **Appearance & Behavior → System Settings → Android SDK**
4. Copie o caminho que aparece em **"Android SDK Location"**

#### Caminhos Padrão:

**Windows:**
```
C:\Users\[SEU_USUARIO]\AppData\Local\Android\Sdk
```

**Mac:**
```
/Users/[SEU_USUARIO]/Library/Android/sdk
```

**Linux:**
```
/home/[SEU_USUARIO]/Android/Sdk
```

---

## 🚀 Passos Completos para Executar o Projeto

### 1️⃣ Pré-requisitos

- [ ] Android Studio instalado (Arctic Fox ou superior)
- [ ] JDK 11 ou superior
- [ ] Android SDK instalado
- [ ] Emulador Android configurado OU dispositivo físico conectado

### 2️⃣ Abrir o Projeto

1. Abra o Android Studio
2. Clique em **File → Open**
3. Navegue até `C:\Users\joaop\Downloads\FitTrack`
4. Clique em **OK**

### 3️⃣ Configurar SDK (se necessário)

Siga as instruções da seção anterior para criar o arquivo `local.properties`

### 4️⃣ Sincronizar Gradle

1. Aguarde o Android Studio indexar o projeto
2. Clique em **File → Sync Project with Gradle Files**
3. Aguarde o download das dependências (pode demorar alguns minutos)

### 5️⃣ Executar o Aplicativo

1. Certifique-se de que um emulador está rodando ou um dispositivo está conectado
2. Clique no botão **Run** (▶️) ou pressione **Shift+F10**
3. Selecione o dispositivo/emulador
4. Aguarde a compilação e instalação

---

## 📱 Testando as Funcionalidades

### ✅ Checklist de Testes

#### Funcionalidades Básicas
- [ ] Abrir o app e ver a citação motivacional
- [ ] Adicionar um novo treino
- [ ] Visualizar a lista de treinos
- [ ] Clicar em um treino para ver seus exercícios

#### Funcionalidades de Treino
- [ ] Adicionar um treino
- [ ] Editar um treino existente
- [ ] Excluir um treino
- [ ] Buscar um treino por nome

#### Funcionalidades de Exercício
- [ ] Adicionar um exercício a um treino
- [ ] Editar um exercício
- [ ] Excluir um exercício
- [ ] Buscar um exercício por nome
- [ ] **Segurar (long press)** um exercício para marcar como concluído
- [ ] Verificar que o exercício concluído fica com fundo verde

#### Funcionalidades de Observação
- [ ] Abrir detalhes de um exercício
- [ ] Adicionar uma observação
- [ ] Visualizar observações salvas

---

## 🐛 Problemas Comuns e Soluções

### Problema 1: Gradle Build Failed

**Sintomas:** Erro ao compilar o projeto

**Possíveis Soluções:**
1. **Limpar e Rebuild:**
   - File → Invalidate Caches / Restart
   - Build → Clean Project
   - Build → Rebuild Project

2. **Verificar versão do Gradle:**
   - Abra `gradle/wrapper/gradle-wrapper.properties`
   - Verifique se a versão é compatível (7.0+)

3. **Atualizar dependências:**
   - Abra `build.gradle` (app)
   - Sincronize com Gradle

### Problema 2: App Crasha ao Abrir

**Sintomas:** O app fecha imediatamente após abrir

**Possíveis Causas:**
1. **Banco de dados desatualizado:**
   - Desinstale o app do emulador/dispositivo
   - Reinstale através do Android Studio

2. **Permissões de Internet:**
   - Verifique se o `AndroidManifest.xml` tem permissão de internet

3. **Logs de erro:**
   - Verifique o Logcat no Android Studio para detalhes

### Problema 3: Citação Motivacional Não Aparece

**Sintomas:** Aparece "Carregando frase..." indefinidamente

**Possíveis Soluções:**
1. **Verificar conexão com internet do emulador**
2. **API pode estar fora do ar** (fallback aparecerá)
3. **Verificar permissão de internet no manifest**

### Problema 4: Dados Não São Salvos

**Sintomas:** Ao fechar e reabrir o app, os dados desaparecem

**Possíveis Causas:**
1. **Usando emulador em modo temporário**
   - Use "Cold Boot" ao invés de "Quick Boot"

2. **Banco de dados não está sendo criado:**
   - Verifique logs do Logcat
   - Procure por erros relacionados ao Room

---

## 🔄 Atualizando o Banco de Dados

### Se Você Modificar as Entidades

Quando você modifica uma entidade (Treino, Exercicio, Observacao), é necessário:

1. **Incrementar a versão do banco:**
   ```kotlin
   @Database(
       entities = [...],
       version = 3, // ← Incrementar este número
       exportSchema = false
   )
   ```

2. **Para desenvolvimento (dados não importantes):**
   - Mantenha `.fallbackToDestructiveMigration()` no MainActivity
   - Desinstale e reinstale o app

3. **Para produção (dados importantes):**
   - Remova `.fallbackToDestructiveMigration()`
   - Implemente uma Migration:
   ```kotlin
   val MIGRATION_2_3 = object : Migration(2, 3) {
       override fun migrate(database: SupportSQLiteDatabase) {
           // SQL para migrar dados
       }
   }
   
   .addMigrations(MIGRATION_2_3)
   ```

---

## 📊 Verificando o Banco de Dados

### Usando Database Inspector do Android Studio

1. Execute o app no emulador
2. Vá em **View → Tool Windows → App Inspection**
3. Selecione a aba **Database Inspector**
4. Visualize as tabelas: `treinos`, `exercicios`, `observacoes`
5. Execute queries SQL diretamente

### Queries Úteis para Testes

```sql
-- Ver todos os treinos
SELECT * FROM treinos;

-- Ver exercícios de um treino específico
SELECT * FROM exercicios WHERE treinoId = 1;

-- Ver exercícios concluídos
SELECT * FROM exercicios WHERE concluido = 1;

-- Ver observações de um exercício
SELECT * FROM observacoes WHERE exercicioId = 1;

-- Buscar treino por nome
SELECT * FROM treinos WHERE nome LIKE '%peito%';
```

---

## 🎯 Dicas de Desenvolvimento

### Para Adicionar Novas Funcionalidades

1. **Adicionar nova propriedade a uma entidade:**
   - Modifique a data class da entidade
   - Incremente a versão do banco
   - Adicione migration (ou use fallbackToDestructiveMigration)

2. **Adicionar nova tela:**
   - Crie um novo arquivo Screen no pacote `screen/`
   - Adicione a rota no `MainActivity.kt`
   - Adicione navegação nas telas existentes

3. **Adicionar nova query:**
   - Adicione a função no DAO
   - Adicione o método no Repository
   - Adicione o método no ViewModel
   - Use na Screen

### Debugging

**Logs Úteis:**
```kotlin
Log.d("TAG", "Mensagem de debug")
Log.e("TAG", "Mensagem de erro", exception)
```

**Breakpoints:**
- Coloque breakpoints nos ViewModels para debugar lógica
- Use "Evaluate Expression" (Alt+F8) para testar código

---

## 📞 Suporte

Se encontrar problemas não listados aqui:

1. **Verifique o Logcat** no Android Studio
2. **Google o erro** específico
3. **Consulte a documentação:**
   - [Android Developers](https://developer.android.com/)
   - [Jetpack Compose](https://developer.android.com/jetpack/compose)
   - [Room Database](https://developer.android.com/training/data-storage/room)
   - [Retrofit](https://square.github.io/retrofit/)

---

## ✅ Projeto Funcionando!

Se você seguiu todos os passos e o app está rodando:

**Parabéns! 🎉** 

Agora você pode:
- Criar e gerenciar seus treinos
- Adicionar exercícios personalizados
- Marcar exercícios como concluídos
- Adicionar observações
- Buscar treinos e exercícios
- Se motivar com citações diárias

**Bom treino! 💪**

