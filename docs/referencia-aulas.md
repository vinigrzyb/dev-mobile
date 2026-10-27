# Referências das aulas: navegação e login

Inspeção de código em 29/09/2026. Nenhum código da aplicação foi alterado ou executado nesta análise.

## Onde está o código completo

Raiz das referências: `C:/Users/Jonas/pessoal/facul/2026-2/dev-aplicativos-moveis/colocar-no-nosso-projeto`.

As pastas extraídas `MyApplication/MyApplication` e `MyApplication (1)/MyApplication` não contêm `app/src` nem `gradle/libs.versions.toml`. Os ZIPs contêm esses arquivos completos, lidos diretamente sem extração. Não foi encontrado AGENTS.md sob a pasta da disciplina.

- `MyApplication (1).zip`: exemplo de navegação, sem autenticação.
- `MyApplication.zip`: navegação mais cadastro e login em memória.

A associação aos títulos Aula 09/Navegação e Aula 10/Login é inferida pelo conteúdo; o nome dos ZIPs não contém o número da aula.

As referências abaixo usam `ZIP!caminho interno:linhas`. Para os Kotlin, o prefixo interno abreviado `src/` significa `MyApplication/app/src/main/java/com/example/myapplication/`. Os números consideram uma linha por LF, ignorando CR duplicado presente nos arquivos.

## Navegação, passo a passo

1. MainActivity cria um único `rememberNavController()` dentro do tema e do `setContent`.
2. `AppNavigation` concentra o `NavHost` e liga cada rota à sua tela.
3. `Routes.kt` declara destinos com `@Serializable`: objetos para telas sem argumentos e `ProdutoDetalhes(val id: Int)` para uma tela parametrizada.
4. As telas recebem callbacks (`irParaProdutos`, `voltar`) e não precisam controlar o NavController diretamente.
5. `navigate()` avança; `popBackStack()` volta; `toRoute<ProdutoDetalhes>()` recupera o ID.
6. A tela de produtos passa o ID fixo 10. A tela de detalhes apenas mostra esse número; não busca um produto real.

Evidências: `MyApplication (1).zip!src/MainActivity.kt:23-31`; `src/navigation/Routes.kt:6-16`; `src/navigation/AppNavigation.kt:17-73`; `src/ui/screens/ProdutosScreen.kt:14-30`; `src/ui/screens/ProdutosDetalhesScreen.kt:14-34`.

O Menu é TopAppBar com botões Home, Produtos e Categoria, chamando `navigate()` diretamente. Não há `launchSingleTop` para impedir entradas repetidas na pilha. As animações do detalhe usam subida de 600 ms e saída à esquerda de 900 ms. Evidências: `MyApplication (1).zip!src/navigation/Menu.kt:15-34` e `src/navigation/AppNavigation.kt:44-55`.

## Login, passo a passo

1. A versão de login inicia o NavHost na rota Login.
2. O cadastro coleta nome, email e senha com `remember { mutableStateOf(...) }`.
3. Campos em branco produzem Toast. Caso contrário, chama `AuthManager.cadastrar()`.
4. AuthManager é um `object` Kotlin com lista mutável inicialmente vazia. Recusa email idêntico já cadastrado e guarda nome/email/senha na lista.
5. Cadastro bem-sucedido retorna ao login com `popBackStack()`.
6. Login chama `AuthManager.login(email, senha)`: compara exatamente email e senha com os da lista. Não há credenciais predefinidas; é preciso cadastrar primeiro.
7. Sucesso navega à Home e remove Login da pilha com `popUpTo(Login) { inclusive = true }`.
8. A MainActivity oculta o menu quando o nome da rota contém Login ou Cadastro.

Evidências: `MyApplication.zip!src/auth/AuthManager.kt:3-20`; `src/ui/screens/CadastroScreen.kt:39-42,94-125`; `src/ui/screens/LoginScreen.kt:40-42,87-103`; `src/navigation/AppNavigation.kt:19-22,76-91`; `src/MainActivity.kt:27-44`.

## Limites que precisam ser explicados na defesa

- É uma simulação local de autenticação: não existe API, banco ou armazenamento persistente no AuthManager. A lista desaparece ao encerrar o processo do aplicativo.
- As senhas ficam como Strings sem hash na memória. Ocultar caracteres com PasswordVisualTransformation é apenas apresentação visual.
- Não existe usuário autenticado armazenado, token ou logout nesse exemplo.
- Email não é normalizado e não tem validação de formato. A duplicidade é por igualdade exata; maiúsculas e espaços podem produzir valores diferentes.
- Formulários usam remember, não rememberSaveable; não prometem preservar os dados após recriação da Activity.
- O modifier recebido por AppNavigation não é aplicado ao NavHost; portanto o padding do Scaffold é passado, mas ignorado.
- A versão sem login contém `Text("Teste")` na Activity (linha 33) e ambas conservam Greeting/Preview de template.

Essas observações vêm da leitura estática. Não foi confirmado build ou comportamento em emulador.

## Dependências declaradas nas duas referências

| Item | Valor declarado |
|---|---|
| Android Gradle Plugin | 9.0.1 |
| Plugin Kotlin Compose/Serialization | 2.0.21 |
| Compose BOM | 2024.09.00 |
| Navigation Compose | 2.8.9 |
| Kotlin serialization JSON | 1.7.+ |
| SDK de compilação | 36, minorApiLevel 1 |
| SDK mínimo / alvo | 24 / 36 |
| Compatibilidade Java | 11 |

Evidências em ambos os ZIPs: `MyApplication/gradle/libs.versions.toml:1-10` e `MyApplication/app/build.gradle.kts:8-21,35-40,61-64`. Material3 e material-icons-extended também são usados.

## Como aproveitar preservando o projeto do grupo

Adotar o conceito: controlador único, rotas centralizadas, telas com callbacks e fluxo cadastro → login → tela principal. Adaptar nomes, textos, estilo e destinos ao domínio do projeto existente. Não trazer telas genéricas de Produtos/Categoria, IDs fixos, pacote com.example.myapplication, Greeting, caches, build ou local.properties.

Antes de mudar Gradle, comparar as versões atuais do projeto e adicionar apenas dependências necessárias. O exemplo da aula mostra uma técnica; não exige substituir a configuração existente pela configuração inteira dos ZIPs.

Na apresentação, diferenciar claramente o que o projeto do grupo implementa daquilo que os exemplos apenas demonstram. Se adotado o AuthManager simples, descrevê-lo como cadastro/login local em memória, sem afirmar backend ou persistência.
