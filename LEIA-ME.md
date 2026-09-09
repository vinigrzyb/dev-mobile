# Receitas Diárias — 3 telas em Kotlin + Jetpack Compose

Entrega com **Login**, **Lista** e **Detalhe**, montada usando **somente** o que aparece
no código das aulas 01 a 08.

---

## 1. Como instalar

Extraia o `.zip` por cima do seu projeto. A árvore já está no formato certo:

```
app/src/main/java/com/example/myapplication/
├── MainActivity.kt      ← substitui o seu (é o roteador das 3 telas)
├── Receita.kt           ← modelo + lista de 8 receitas
├── CoresApp.kt          ← paleta do app
├── BarraTopo.kt         ← barra azul reutilizada pelas 3 telas
├── TelaLogin.kt         ← TELA 1
├── TelaLista.kt         ← TELA 2
└── TelaDetalhe.kt       ← TELA 3

app/src/main/res/drawable/
├── ic_voltar.xml  ic_email.xml  ic_cadeado.xml  ic_pessoa.xml
├── ic_busca.xml   ic_relogio.xml  ic_pessoas.xml  ic_dificuldade.xml
├── ic_estrela.xml  ic_engrenagem.xml
└── ic_prato_massa.xml  ic_prato_sopa.xml  ic_prato_salada.xml
    ic_prato_bolo.xml   ic_prato_copo.xml  ic_prato_panela.xml
```

**Nada muda no `build.gradle.kts`.** Nenhuma biblioteca nova foi adicionada.
**Nada muda em `ui/theme/`.** Os três arquivos de tema do professor ficam intactos.

Se o seu pacote não for `com.example.myapplication`, troque a primeira linha
(`package ...`) dos 7 arquivos.

---

## 2. Critério de aceitação — a afirmação, com a prova

> **Todo símbolo usado nestes 7 arquivos aparece no código que o professor
> escreveu nas aulas 01 a 08. Não há uma única API nova.**

Isso não é opinião. Foi verificado de duas formas:

### Prova 1 — os imports

Extraí os `import` de todos os `.kt` das aulas (95 imports distintos) e os `import`
dos 7 arquivos desta entrega (51 imports distintos), e comparei os dois conjuntos:

```
comm -23 meus_imports.txt imports_das_aulas.txt
→ (saída vazia)
```

Saída vazia significa: **os 51 imports que uso são um subconjunto exato dos 95 das
aulas.** Não existe um import aqui que o professor não tenha escrito.

### Prova 2 — os símbolos que não são import

Parâmetros nomeados, métodos de coleção e construções de linguagem foram conferidos
um a um contra o código das aulas. Nenhum ficou sem origem:

| Símbolo usado no projeto | Onde ele aparece no código das aulas |
|---|---|
| `Scaffold` | Aula 07 · `myapplication/MainActivity.kt:54` |
| `enableEdgeToEdge` | Aula 07 · `myapplication/MainActivity.kt:6` |
| `ComponentActivity` / `setContent` | Aula 07 · `myapplication/MainActivity.kt:48` |
| `Surface(modifier, color)` | Aula 06 · `app/MainActivity.kt:43` |
| `Box(contentAlignment)` | Aula 06 · `app/MainActivity.kt:91` |
| `Arrangement.SpaceBetween` | Aula 06 · `app/MainActivity.kt:81` |
| `Arrangement.spacedBy` | Aula 07 · `myapplication/MainActivity.kt:83` |
| `Icon(painter, contentDescription, tint)` | Aula 07 · `myapplication/MainActivity.kt:97` |
| `painterResource(R.drawable...)` | Aula 07 · `myapplication/MainActivity.kt:37` |
| `Modifier.weight(1f)` | Aula 06 · `app/MainActivity.kt:88` |
| `Modifier.width(...)` | Aula 06 · `app/MainActivity.kt:59` |
| `Modifier.clickable { }` | Aula 07 · `myapplication/MainActivity.kt:7` |
| `OutlinedTextFieldDefaults.colors` | Aula 07 · `myapplication/MainActivity.kt:74` |
| `OutlinedTextField(isError)` | Aula 07 · `FormularioAula.kt:76` |
| `OutlinedTextField(leadingIcon)` | Aula 07 · `myapplication/MainActivity.kt:97` |
| `visualTransformation = PasswordVisualTransformation()` | Aula 07 · `myapplication/MainActivity.kt:124` |
| `KeyboardOptions(keyboardType)` | Aula 07 · `myapplication/MainActivity.kt:111` |
| `MaterialTheme.colorScheme.error` | Aula 07 · `FormularioAula.kt:81` |
| `MaterialTheme.typography.titleLarge` | Aula 07 · `myapplication/MainActivity.kt:88` |
| `Checkbox(checked, onCheckedChange)` | Aula 07 · `FormularioAula.kt:135` |
| `Spacer(Modifier.height)` | Aula 07 · `FormularioAula.kt:144` |
| `Button(onClick, enabled)` | Aula 07 · `FormularioAula.kt:149` |
| `TextButton(onClick)` | Aula 07 · `myapplication/MainActivity.kt:155` |
| `LazyColumn` | Aula 08 · `myapplication(1)/MainActivity.kt:66` |
| `items(items =, key =)` | Aula 08 · `myapplication(1)/MainActivity.kt:95` |
| `Card(elevation = CardDefaults.cardElevation)` | Aula 08 · `myapplication(1)/MainActivity.kt:126` |
| `Card(shape = RoundedCornerShape)` | Aula 08 · `myapplication(1)/MainActivity.kt:84` |
| `Column` + `verticalScroll(rememberScrollState())` | Aula 08 · `myapplication(1)/MainActivity.kt:121` |
| `Log.d` | Aula 08 · `myapplication(1)/MainActivity.kt:101` |
| `fun` que devolve `List<T>` | Aula 08 · `myapplication(1)/MainActivity.kt:138` (`gerarAluno`) |
| `data class` | Aula 08 · `Aluno.kt:3` |
| `mutableListOf` | Aula 04 · `aula03/Main.kt:4` |
| `.add()` | Aula 04 · `aula03/Main.kt:8` |
| `.removeIf { }` | Aula 04 · `aula03/Main.kt:14` |
| `.contains()` | Aula 04 · `aula03/Main.kt:13` |
| `.forEach { }` | Aula 04 · `aula03/Main.kt:15` |
| `.size` | Aula 04 · `aula03/Main.kt:12` |
| `.uppercase()` | Aula 04 · `aula03/Main.kt:15` |
| `.trim()` e `.length` | Aula 07 · `FormularioAula.kt:76` |
| `.isNotEmpty()` | Aula 07 · `FormularioAula.kt:76` |
| `Color(0xFF......)` | Aula 06 · `ui/theme/Color.kt:5` |
| negação com `!` | Aula 07 · `FormularioAula.kt:133` (`aceitou = !aceitou`) |
| `@Preview(showBackground = true)` | Aula 07 · `myapplication/MainActivity.kt:190` |
| parâmetro com valor padrão | Aula 06 · `app/MainActivity.kt:76` (`fun Home(name: String = "Visitante")`) |
| função recebida por parâmetro (callback) | Aula 03 · `aula3/Main.kt:29` (`fun executar(acao: (...) -> Unit)`) |

---

## 3. O que foi trocado por causa desse critério

Cada linha aqui é uma coisa do layout que foi resolvida de outro jeito, porque a forma
"óbvia" usa algo que não apareceu em aula.

| O layout pedia | Forma "óbvia" (não usada) | O que foi feito | Por quê |
|---|---|---|---|
| Barra azul no topo | `TopAppBar` | `Surface` colorida com um `Row` dentro | `TopAppBar` não aparece em nenhuma aula |
| Navegar entre telas | `NavHost` / `NavController` | `var tela by remember { mutableStateOf("login") }` + `when` | exigiria a biblioteca `navigation-compose` no Gradle |
| Abas Entrar/Cadastrar e Ingredientes/Preparo | `TabRow` | aba ativa vira `Button`, aba inativa vira `TextButton` | `TabRow` não aparece em nenhuma aula |
| Foto do prato | `Image` + `ContentScale.Crop` + Coil | `Surface` colorida com `Icon(painterResource(...), tint = branco)` | o professor sempre usou `painterResource` **dentro de `Icon`**, nunca `Image` — é a estrutura da função `CriarElemento` da Aula 06 |
| Olhinho para revelar a senha | `VisualTransformation.None` | senha sempre mascarada com `PasswordVisualTransformation()` | só a `PasswordVisualTransformation` aparece em aula |
| Filtrar a lista pela busca | `receitas.filter { }` | `mutableListOf` + `forEach` + `if` + `add` | `filter` não aparece no código das aulas; `mutableListOf`, `forEach` e `add` aparecem |
| Marcar/desmarcar favorito | `favoritos + id` / `favoritos - id` | copiar com `forEach`, e usar `add` ou `removeIf` | `removeIf` e `add` são exatamente os métodos da Aula 04 |
| Linha divisória | `Divider` / `HorizontalDivider` | `Surface` de `1.dp` de altura | `Divider` não aparece em nenhuma aula |
| Avisar erro de validação | `Toast` / `AlertDialog` | `Text` vermelho embaixo do campo | é exatamente o que o professor fez em `FormularioAula.kt:80-84` |
| Foto de perfil redonda / logo redondo | `Modifier.clip(CircleShape)` | `Card(shape = RoundedCornerShape(metade do tamanho))` | `clip` e `CircleShape` não aparecem; `Card(shape = RoundedCornerShape(...))` aparece na Aula 08 |
| Grade de 2 colunas | `LazyVerticalGrid` | tela de grade não entrou nesta entrega | `LazyVerticalGrid` não aparece em nenhuma aula |

**Não foi encostado em nada disso:** ViewModel, StateFlow, LiveData, Room, Retrofit,
Firebase, Hilt, corrotinas, `suspend`, `AlertDialog`, `Toast`, `SharedPreferences`,
`sealed class`, `object`, `navigation-compose`, Coil, `Icons.Default`, `mutableStateListOf`.

---

## 4. Mapa aula → código, para a defesa

Se o professor perguntar "onde está o conteúdo da aula X", a resposta está aqui:

| Aula | Conteúdo | Onde aparece nesta entrega |
|---|---|---|
| 01–02 | condicionais, operadores | `if` das validações no `TelaLogin.kt`; `when` do roteador no `MainActivity.kt` e das abas no `TelaDetalhe.kt` |
| 03 | funções e callbacks | `onEntrar`, `onAbrir`, `onSair`, `onFavoritar`, `onVoltar` — as telas nunca navegam sozinhas, só avisam a `MainActivity` |
| 04 | coleções | filtro da busca (`mutableListOf` + `forEach` + `add`), favoritos (`contains` + `removeIf`), `.size` nos contadores, `.uppercase()` na busca |
| 05 | objetos | `data class Receita` no `Receita.kt` |
| 06 | Compose básico | `BarraTopo.kt` inteiro: `Surface` + `Row` + `Box` + `Icon` + `Modifier.weight` |
| 07 | formulários | `TelaLogin.kt` inteiro: `OutlinedTextField`, `isError`, mensagem de erro, `Checkbox`, `Button(enabled = ...)`, senha mascarada |
| 08 | Card e LazyColumn | `TelaLista.kt`: `LazyColumn` + `items(items =, key = { it.id })` + `Card(elevation = ...)`. `TelaDetalhe.kt`: `Column` + `verticalScroll` |

---

## 5. O que o app faz

**Tela 1 — Login.** Abas Entrar e Cadastrar. Valida e-mail (precisa de `@` e `.`),
senha (mínimo 4 caracteres) e, no cadastro, nome (mínimo 3 letras) e aceite dos termos.
O botão só habilita quando tudo está válido. Erros aparecem em vermelho embaixo do campo.

**Tela 2 — Lista.** Busca por nome que filtra enquanto você digita, filtro por categoria
(Todas / Salgadas / Doces), contador de resultados, aviso de lista vazia, e a estrela nos
cards que já foram favoritados.

**Tela 3 — Detalhe.** Bloco colorido com o desenho do prato, tempo, porções, dificuldade,
botão de favorito, e as abas Ingredientes (com `Checkbox` por item e contador de progresso)
e Modo de Preparo.

Os favoritos são compartilhados entre as telas 2 e 3 porque o estado mora na
`MainActivity` e desce por parâmetro — é isso que faz a estrela aparecer na lista
depois de favoritar no detalhe.

---

## 6. Comentários

2.887 linhas no total: **1.267 de comentário para 1.456 de código** (0,87 para 1).
Zero linhas de lógica sem comentário explicando o que fazem, e zero código comentado
(nenhuma linha de código morta desativada com `//`).
