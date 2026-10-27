# Avaliacao da entrega na perspectiva da aula

## Escopo e criterio
Projeto Receitas Diarias na branch feat/jonas, na pasta original nosso-projeto/dev-mobile. Referencias: Trabalho de Android - 3.pdf e codigos locais das aulas 01 a 14. Referencia arquitetural principal: aula 14 (MyApplication(5).zip). Avaliacao tecnica, sem atribuir nota ou presumir aceite do professor.

## Requisitos e evidencia no codigo
| Requisito | Onde demonstrar | Avaliacao |
|---|---|---|
| MVVM | screens -> ui/viewmodel -> data/dal -> data/local | Telas sem SQL; ViewModels coordenam operacoes e estados; DAO acessa SQLite. |
| Model | model/Usuario.kt, Receita.kt, ItemCompra.kt | Objetos tipados, IDs Long provenientes do banco. |
| SQLiteOpenHelper | data/local/AppDatabase.kt | Um arquivo local, tres tabelas, versao 1. |
| writableDatabase | Tres DAOs | insertOrThrow para cadastro; update/delete nos dois CRUDs. |
| readableDatabase | Tres DAOs | query para login, listagem e busca por ID. |
| CRUD 1 | Receitas | Cadastro, listagem Home, detalhe por ID, edicao, exclusao com confirmacao. |
| CRUD 2 | Itens de compra | Cadastro, lista, edicao e exclusao com confirmacao. |
| Login/cadastro | LoginScreen + AuthViewModel + UsuarioDAO | Conta persistente, e-mail normalizado/unique, hash/salt conforme exemplo. |
| Sessao | auth/SessaoUsuario.kt | ID em memoria; login inicia, sair encerra; rotas e VMs exigem acesso. |
| Menu oculto | navigation/NavGraph.kt | Menu ausente na rota Login, que contem as abas Entrar e Cadastrar. |
| LaunchedEffect | NavGraph e formularios/login | Controle de sessao, carregamento inicial e reacao a sucesso. |
| LazyColumn | CadastroReceita e ListaComprasScreen | Formulario e listagem. |

## Padroes efetivamente presentes
- MVVM: fluxo e responsabilidades, nao apenas nomes de pastas.
- DAO: UsuarioDAO, ReceitaDAO e ItemCompraDAO encapsulam acesso aos dados.
- Objetos unicos Kotlin: Autenticacao e SessaoUsuario, como AuthManager/SessaoUsuario do professor.
- SQLiteOpenHelper: classe base da API para criacao/versionamento, igual ao exemplo.
- Nao foram introduzidos Repository, Room, injecao de dependencias, backend, sincronizacao ou gerenciamento administrativo de usuarios.

## Preservacao do projeto
Mantidos pacote com.example.aula_06, tema/cores, abas de login, campos do formulario de receita, grade da Home e imagens das seis receitas originais. O cadastro de usuario permanece na mesma tela: mudanca em relacao ao exemplo docente, coerente com o projeto existente. Dados compartilhados no dispositivo, sem vinculo usuario_id, conforme acordado.

## Adaptacoes necessarias e limites
1. As receitas e compras aplicam o DAO/ViewModel de usuarios do professor ao dominio do projeto. Nao sao copias literais dos arquivos de usuario.
2. Ingredientes e preparo usam texto multilinha no SQLite e formulario. Imagens antigas sao identificadas por nomes estaveis de recursos; novos cadastros nao exibem foto, pois nao existe seletor no projeto.
3. Os seis exemplos sao inseridos somente na criacao do banco. Exclusao posterior permanece efetiva.
4. Detalhe usa ID em vez de nome, evitando confundir receitas homonimas.
5. Controle de retorno usa DisposableEffect/ON_RESUME, encontrado no exemplo docente. Formularios evitam recarregar rascunhos do ViewModel ao recriar Activity. Aba e identificacao de acesso usam rememberSaveable, ensinado na aula 14; senha nao e salva no estado persistente.
6. onUpgrade: ainda nao existe uma segunda versao do schema; uma atualizacao nao prevista e rejeitada explicitamente. Nao se apagam tabelas nem se copia o TODO como se fosse migracao pronta.
7. Debug usa applicationIdSuffix .teste para coexistir com a instalacao anterior no emulador. A implementacao validada foi transferida para a pasta original a pedido do usuario, com backup anterior.

## Pontos que impedem afirmar igualdade literal com as aulas
A grade LazyVerticalGrid/GridCells e usos de Image/ContentScale.Crop, CircleShape e ButtonDefaults vieram do visual existente, mas nao foram encontrados nos ZIPs das aulas examinados. Foram preservados por orientacao do usuario, sem presumir que o professor aceita componentes nao comprovados. Versoes de SDK/Gradle tambem diferem do projeto do professor: foi mantida a base local que compilava. Isso nao altera o padrao DAO/MVVM, mas igualdade total com o material nao foi comprovada.

## Revisao independente
Encontrou perda de rascunhos ao recriar formularios e perda da aba de cadastro durante recriacao. Tres testes reproduziram os defeitos antes da correcao. Nenhum defeito critico foi apontado. Correcao minima aplicada com guarda de carregamento e rememberSaveable. A verificacao final deve ser lida junto dos resultados abaixo.

## Roteiro de defesa por membro
1. Autenticacao: mostrar tabela usuarios, hash/salt, UsuarioDAO, AuthViewModel e SessaoUsuario. Explicar que conta persiste, sessao nao; mostrar menu oculto e acesso negado apos sair. Integracao do helper e navegacao.
2. Receitas: explicar model, campos, ID, DAO e ViewModel; demonstrar criar/listar/editar/excluir e retorno da lista atualizado. Mostrar que nomes repetidos nao confundem IDs.
3. Compras: explicar ItemCompra, DAO, ViewModel e tela LazyColumn; demonstrar os quatro passos e lista compartilhada com outra conta.

## Validacao
Comando final: gradlew.bat :app:testDebugUnitTest :app:connectedDebugAndroidTest :app:lintDebug. BUILD SUCCESSFUL.
- 3 testes unitarios: zero falhas, erros ou ignorados.
- 9 testes no emulador API 35: zero falhas, erros ou ignorados. Incluem login errado/correto, logout/ausencia de menu, SQLite/hash, CRUDs, dados compartilhados entre contas, IDs homonimos e tres regressoes de recriacao de tela.
- Lint: zero erros e 22 avisos (SDK/dependencias antigas, recursos/icones, parametro Modifier e label redundante). Nao foram feitas atualizacoes de ambiente ou polimento fora da entrega para eliminar avisos.
- APK instalado separadamente em com.example.aula_06.teste; processo encerrado e reaberto para inspecao da tela de login.
- git diff --check sem erros. Durante a validacao isolada, os 43 arquivos da base original estavam preservados. Depois, a implementacao foi transferida para a pasta original, com backup e conferencia dos arquivos por SHA256.

Parecer: requisitos funcionais exercitados passaram. MVVM/DAO e acesso SQLite seguem os exemplos. Nao equivale a nota ou aceite; os componentes visuais herdados sem evidencia nas aulas continuam sendo ressalva.

Relatorios desta validacao anterior a transferencia: C:/Users/Jonas/.codex/worktrees/teste-entrega-mvvm/dev-mobile/app/build/reports/ (tests/testDebugUnitTest, androidTests/connected/debug e lint-results-debug.html). A transferencia nao alterou o codigo validado; apenas esta documentacao foi atualizada para refletir a nova pasta e branch.

## Limite de complexidade e duas aulas
Entrega restrita a acesso + receitas + compras, com banco local compartilhado. Sem novos recursos alem disso.
Aula 1: cada membro le seu model, DAO e ViewModel; cadastra e consulta dados. Membro 1 explica helper, login e sessao.
Aula 2: cada membro demonstra seu fluxo completo; integrar apresentacao e conferir com o professor os componentes visuais herdados.
Os testes sao ferramenta de verificacao, nao uma quarta funcionalidade para defender. Para explicar qualquer CRUD, seguir: clique na tela -> funcao do ViewModel -> operacao do DAO -> SQLite -> lista atualizada.
