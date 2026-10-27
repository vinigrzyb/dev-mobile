> Este documento registra o diagnostico anterior a integracao. Para o estado atual, leia `implementacao.md`.

# Receitas Diárias — leitura para apresentação

Diagnóstico em 29/09/2026. Código inspecionado; aplicativo não compilado nem executado nesta análise. Nenhum código funcional alterado. Plano acordado: preservar identidade e estrutura atuais e adaptar somente conceitos das aulas.

## 1. O que existe hoje

O aplicativo é Android nativo, escrito em Kotlin, com interface declarativa Jetpack Compose e componentes Material 3. Existem quatro telas, mas não há navegação entre elas. A entrada atual abre diretamente o formulário de receita.

| Parte | Implementação e localização | Limite atual |
|---|---|---|
| Entrada | `MainActivity.kt:15-27`: `onCreate` → `setContent` → `Aula06Theme` → `Surface` → `CadastroReceitaScreen` | As outras telas estão em chamadas comentadas; importar uma tela não a torna acessível |
| Home | `screens/HomeScreen.kt:29-67`: `LazyVerticalGrid`, duas colunas, seis receitas | Cards não têm ação de clique |
| Card | `screens/HomeScreen.kt:72-90`: imagem local e nome recebidos em `Receita` | Não abre detalhe |
| Modelo | `model/Receita.kt:6-16`: `data class Receita(nome, imagem)` e lista fixa | Sem ID, ingredientes, preparo, tempo, porções ou dificuldade |
| Login | `screens/LoginScreen.kt:25-31,119-155`: campos e senha mascarada | Botão Entrar vazio; sem autenticação |
| Aba Cadastrar | `screens/LoginScreen.kt:73-115` | Só muda seleção visual; não cadastra usuário |
| Social e recuperação | `screens/LoginScreen.kt:142,160-181` | Textos e círculos decorativos; sem integração |
| Detalhe | `screens/DetalheReceitaScreen.kt:22-35,57-79` | Sempre lasanha fixa, ausente da lista da Home |
| Abas e checklist | `screens/DetalheReceitaScreen.kt:84-155` | Alternam conteúdo e marcações locais; não persistem |
| Cadastro de receita | `screens/CadastroReceita.kt:19-29,60-136`: seis campos editáveis | Salvar tem `onClick = {}`; `receitaSalva` foi declarada mas não é usada |
| Tema | `ui/theme/Color.kt`, `Theme.kt`, `Type.kt` | Paleta azul e branco própria; esquemas claro/escuro usam as mesmas cores principais |
| Recursos | `res/drawable`, `res/values/strings.xml` | Imagens empacotadas no app; nome exibido Receitas Diárias |

Os caminhos Kotlin da tabela partem de `app/src/main/java/com/example/aula_06/`.

## 2. Como ler o código sem já conhecer Android

- `package`: endereço lógico do arquivo; `import`: disponibiliza nomes de classes/funções.
- `MainActivity`: porta de entrada declarada no `AndroidManifest.xml`; `onCreate` prepara a tela.
- `setContent`: começa a interface Compose. `@Composable`: função que descreve uma parte dessa interface.
- `Column`, `Row`, `Box`: organizam elementos em coluna, linha e sobreposição. `Spacer`: espaço vazio.
- `Modifier`: configura tamanho, margem interna, fundo, rolagem e clique. A ordem das operações pode mudar o resultado visual.
- `dp`: medida de layout; `sp`: medida de texto; `weight`: distribui espaço disponível.
- `remember { mutableStateOf(...) }`: estado local observável. Quando o usuário altera um campo, `onValueChange` atualiza esse estado e Compose recompõe os trechos que o leem.
- `remember` não é banco de dados e não garante manter valores após recriação da tela ou encerramento do processo.
- `by`: permite ler e atribuir o valor do estado diretamente, sem escrever `.value`.
- `value = nome` lê o estado; `onValueChange = { nome = it }` recebe o novo texto. `it` é o parâmetro implícito da função.
- `onClick`: ação do botão. Chaves vazias significam que clicar não executa trabalho.
- `data class`: estrutura de dados. `listOf`: lista somente de leitura usada aqui como catálogo fixo.
- `R.drawable`: identificador gerado para uma imagem local. `painterResource` carrega essa imagem.
- `LazyVerticalGrid` e `LazyColumn`: organizam listas com composição dos itens conforme necessário à exibição.
- `@Preview`: prévia no Android Studio; não cria rota e não comprova funcionamento no aparelho.
- `PasswordVisualTransformation`: mascara a exibição da senha; não autentica nem criptografa armazenamento.
- Funções auxiliares `ReceitaCard`, `InfoReceita`, `ItemComCheckbox`, `LoginSocialIcone` evitam repetir a construção dos elementos.

## 3. Padrão para conectar sem mudar a identidade

Manter `com.example.aula_06`, telas em `screens`, modelo em `model` e tema em `ui/theme`. Não renomear tudo perto da apresentação. Preservar cores, imagens e componentes existentes.

Fluxo proposto: Login → Home → Detalhe da receita escolhida; Home → Nova Receita → Home atualizada. Cadastro de usuário retorna ao Login. Cadastro de usuário e cadastro de receita são coisas diferentes.

1. Preparar execução: configurar SDK local e recuperar wrapper Gradle correspondente ao projeto. Fazer uma compilação da base antes das alterações.
2. Criar a navegação central em `navigation`, usando os conceitos de controlador, destinos e pilha de retorno das aulas. A MainActivity passa a abrir esse fluxo.
3. Passar ações para as telas, como `aoEntrar`, `aoAbrirReceita`, `aoNovaReceita` e `aoVoltar`. A tela desenha e informa eventos; a navegação decide o destino.
4. Implementar cadastro/login didáticos seguindo a referência: lista de usuários em memória, rejeição de e-mail já cadastrado e comparação das credenciais. Explicar explicitamente que não é autenticação de produção e que usuários desaparecem quando o processo termina.
5. Ampliar Receita com ID e dados do detalhe. Selecionar por ID e mostrar os dados da receita selecionada; tratar ID inexistente.
6. Compartilhar a lista entre Home, cadastro e detalhe. No cadastro, validar os campos, adicionar de fato à lista e voltar para Home. Uma lista observável em memória basta para esse escopo; persistência é outra etapa.
7. Definir imagem padrão para nova receita, já que o formulário atual não possui seleção de foto.
8. Validar o percurso inteiro, retorno do Android e reinício. Após login, remover Login da pilha conforme conceito da aula. Se existir Sair, limpar o fluxo autenticado.

Compatibilidade: o projeto atual declara AGP 8.1.4, Kotlin 1.9.10, Compose Compiler 1.5.3, BOM 2023.10.01, Gradle 8.2 e SDK 34, mínimo 24. O exemplo de login declara versões diferentes e usa rotas serializáveis. Não copiar o Gradle do exemplo inteiro. A versão da biblioteca de navegação e a sintaxe de rotas precisam ser verificadas contra a base antes de implementar. Rotas simples podem preservar o conceito sem exigir a migração completa; se o professor exigir a sintaxe tipada exata, isso muda o escopo.

## 4. O que falar na defesa

“Nosso aplicativo é o Receitas Diárias, feito em Kotlin e Jetpack Compose. A interface foi dividida em quatro telas e componentes menores. A Home usa um modelo Receita e uma grade com imagens locais. Formulários e abas usam estado observável do Compose. Na versão atual, as telas estão construídas, mas navegação, autenticação e gravação ainda não estão conectadas. A próxima etapa é adaptar os conceitos das aulas preservando o visual do projeto.”

Perguntas prováveis:

| Pergunta | Resposta fiel ao código atual |
|---|---|
| Onde começa? | Manifest aponta para MainActivity; ela chama CadastroReceitaScreen dentro de setContent |
| Onde ficam as receitas? | Lista fixa em model/Receita.kt, sem banco |
| Por que digitar atualiza a tela? | onValueChange altera mutableStateOf, observado pelo Compose |
| O botão salva? | Ainda não; o callback está vazio |
| Login já funciona? | A interface existe, mas não há verificação de credenciais |
| Por que o detalhe não corresponde ao card? | Ainda é conteúdo fixo e não recebe a receita selecionada |
| Funciona sem internet? | Os conteúdos atuais são locais; não há backend integrado |
| A senha está protegida? | Está apenas mascarada visualmente; autenticação ainda não existe |
| As quatro telas são acessíveis? | Não pelo fluxo atual; existem no código e nas previews |
| O que veio da aula? | A integração planejada usa conceitos de navegação e login em memória; ainda não foi implementada neste projeto |

## 5. Validação para fazer na hora

Estado atual: ao executar o fonte, espera-se Nova Receita. Digitar altera campos; Salvar não produz resultado. As outras telas podem ser inspecionadas nas previews do Android Studio, se o ambiente estiver configurado. Não anunciar fluxo completo como pronto.

Após integração: cadastrar usuário → tentar senha errada → entrar corretamente → abrir duas receitas diferentes e conferir dados → voltar → criar receita → conferir na Home → abrir novo detalhe → usar Voltar do Android. Fechar o processo e reabrir evidencia a perda dos dados em memória. Testar campos vazios e e-mail repetido.

Ambiente: `local.properties` aponta para `/home/vini/Android/Sdk`. Faltam `gradlew`, `gradlew.bat` e `gradle-wrapper.jar`; há somente wrapper properties. Há APK e caches antigos versionados, que não provam que o fonte atual compila aqui. Não houve build nem execução nesta análise.

## 6. Seus 15 minutos

1. Minutos 0–4: leia a tabela do estado atual e abra MainActivity.
2. Minutos 4–8: entenda Receita, Home e o par value/onValueChange no cadastro.
3. Minutos 8–11: revise Login e Detalhe; memorize quais ações são apenas visuais.
4. Minutos 11–15: ensaie a fala e as perguntas acima. Leve o plano de conexão como próxima etapa, separado do que já existe.
