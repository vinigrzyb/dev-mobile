# Receitas Diarias - entrega 3

Branch: feat/jonas, na pasta original nosso-projeto/dev-mobile. A implementacao foi transferida para esta pasta a pedido do usuario, com copia de seguranca dos arquivos anteriores e conferencia por SHA256. O commit e o envio desta versao ao GitHub foram autorizados pelo usuario.

## Organizacao
- screens: telas existentes de acesso e receitas, mais lista/formulario de compras.
- ui/viewmodel: AuthViewModel, ReceitaViewModel e ListaComprasViewModel, no padrao AndroidViewModel da aula 14.
- model: Usuario, Receita e ItemCompra.
- data/dal: UsuarioDAO, ReceitaDAO e ItemCompraDAO.
- data/local/AppDatabase: SQLiteOpenHelper versao 1, tres tabelas no arquivo receitas_diarias.db.
- auth: Autenticacao com hash/salt, SessaoUsuario em memoria.
- navigation: rotas tipadas por ID, menu e controle de acesso.

Cada CRUD segue Tela -> ViewModel -> DAO -> AppDatabase. Operacoes do banco executam em Dispatchers.IO e estados sao atualizados no ViewModel.

## Dados
Contas persistem no banco; sessao e encerrada ao sair ou terminar o processo. As contas compartilham receitas e compras nesta instalacao. Nao existe sincronizacao entre dispositivos.
As seis receitas iniciais e imagens sao preservadas apenas na primeira criacao do banco. Excluir uma receita inicial nao faz ela reaparecer. Novas receitas nao exigem foto, pois nao havia seletor no formulario.
Ingredientes e preparo ficam como texto, com quebras de linha preservadas. ID do SQLite identifica detalhe/edicao/exclusao, mesmo com nomes repetidos.

## Responsabilidades dos tres membros
1. Acesso: Usuario, UsuarioDAO, Autenticacao, SessaoUsuario, CadastroValidator, AuthViewModel e LoginScreen; integra AppDatabase, Rotas, NavGraph, Menu e MainActivity.
2. Receitas: Receita, ReceitaDAO, ReceitaViewModel, HomeScreen, CadastroReceita e DetalheReceitaScreen.
3. Compras: ItemCompra, ItemCompraDAO, ListaComprasViewModel, ListaComprasScreen e CadastroItemCompraScreen.

## Como demonstrar
1. Abrir o aplicativo de teste; abas Entrar/Cadastrar sem menu.
2. Cadastrar conta, mostrar erro com senha incorreta e entrar corretamente.
3. Criar receita, listar, editar, abrir detalhe atualizado, cancelar exclusao e depois confirmar.
4. Adicionar item de compra, editar quantidade, listar e excluir.
5. Sair, entrar com outra conta e mostrar dados compartilhados.
6. Fechar o processo e reabrir: login e exigido; contas e registros permanecem.

O package de debug recebe .teste para nao substituir a instalacao anterior. Evidencias e ressalvas docentes em teste-entrega.md.
