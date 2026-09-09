// =====================================================================================
// ARQUIVO: TelaLogin.kt
// TELA 1 : Entrar / Cadastrar
//
// O QUE ESTA TELA PROVA NA DEFESA:
//   - Formulário com OutlinedTextField, validação, isError e mensagem de erro ... Aula 07
//   - Estado com remember { mutableStateOf(...) } .............................. Aula 07
//   - Checkbox ................................................................ Aula 07
//   - Botão com enabled dependendo da validação ............................... Aula 07
//   - Senha mascarada com PasswordVisualTransformation ........................ Aula 07
//   - Teclado específico com KeyboardOptions/KeyboardType ..................... Aula 07
//   - Callback recebido por parâmetro (onEntrar) .............................. Aula 03
//   - when para trocar de aba ................................................. Aula 02
//
// DECISÃO DE PROJETO IMPORTANTE:
//   O aviso de erro NÃO usa Toast nem AlertDialog (nada disso apareceu em aula).
//   Ele é um Text vermelho embaixo do campo, exatamente como o professor fez no
//   FormularioAula.kt da Aula 07 com a mensagem "O nome deve conter pelo menos 3 caracteres".
// =====================================================================================

// Mesmo pacote dos outros arquivos do app.
package com.example.myapplication

// Arrangement: define o espaçamento entre os filhos de uma Column/Row.
import androidx.compose.foundation.layout.Arrangement
// Box: caixa usada para centralizar conteúdo.
import androidx.compose.foundation.layout.Box
// Column: empilha os filhos na vertical.
import androidx.compose.foundation.layout.Column
// Row: coloca os filhos lado a lado.
import androidx.compose.foundation.layout.Row
// Spacer: espaço vazio entre elementos.
import androidx.compose.foundation.layout.Spacer
// fillMaxSize: ocupa toda a área disponível.
import androidx.compose.foundation.layout.fillMaxSize
// fillMaxWidth: ocupa toda a largura disponível.
import androidx.compose.foundation.layout.fillMaxWidth
// height: altura fixa em dp.
import androidx.compose.foundation.layout.height
// padding: afastamento interno.
import androidx.compose.foundation.layout.padding
// size: largura e altura ao mesmo tempo.
import androidx.compose.foundation.layout.size
// width: largura fixa em dp.
import androidx.compose.foundation.layout.width
// clickable: torna o elemento clicável (Aula 07, campo de data).
import androidx.compose.foundation.clickable
// rememberScrollState: guarda a posição da rolagem (Aula 08, ListaColumn).
import androidx.compose.foundation.rememberScrollState
// verticalScroll: faz a Column rolar quando o conteúdo não cabe (Aula 08).
import androidx.compose.foundation.verticalScroll
// RoundedCornerShape: canto arredondado (Aula 08).
import androidx.compose.foundation.shape.RoundedCornerShape
// KeyboardOptions: configura o teclado do campo (Aula 07).
import androidx.compose.foundation.text.KeyboardOptions
// Button: botão principal (Aula 07).
import androidx.compose.material3.Button
// Card: bloco branco com sombra (Aula 08).
import androidx.compose.material3.Card
// Checkbox: caixa de marcar (Aula 07).
import androidx.compose.material3.Checkbox
// Icon: desenha um vetor de res/drawable (Aula 06/07).
import androidx.compose.material3.Icon
// MaterialTheme: acesso às cores e tipografia do tema (Aula 06/07).
import androidx.compose.material3.MaterialTheme
// OutlinedTextField: campo de texto com borda (Aula 07).
import androidx.compose.material3.OutlinedTextField
// OutlinedTextFieldDefaults: personaliza as cores da borda do campo (Aula 07).
import androidx.compose.material3.OutlinedTextFieldDefaults
// Surface: bloco colorido de fundo (Aula 06).
import androidx.compose.material3.Surface
// Text: escreve texto.
import androidx.compose.material3.Text
// TextButton: botão sem fundo, só texto (Aula 07, no DatePickerDialog).
import androidx.compose.material3.TextButton
// Composable: marca a função como desenhável.
import androidx.compose.runtime.Composable
// getValue: necessário para usar "by" na leitura do estado.
import androidx.compose.runtime.getValue
// mutableStateOf: cria um estado observável pelo Compose.
import androidx.compose.runtime.mutableStateOf
// remember: faz o estado sobreviver às redesenhadas da tela.
import androidx.compose.runtime.remember
// setValue: necessário para usar "by" na escrita do estado.
import androidx.compose.runtime.setValue
// Alignment: alinhamentos.
import androidx.compose.ui.Alignment
// Modifier: tamanho, espaçamento, clique.
import androidx.compose.ui.Modifier
// painterResource: carrega o desenho do res/drawable.
import androidx.compose.ui.res.painterResource
// FontWeight: peso da fonte.
import androidx.compose.ui.text.font.FontWeight
// KeyboardType: tipo de teclado (Email, Password...).
import androidx.compose.ui.text.input.KeyboardType
// PasswordVisualTransformation: troca os caracteres da senha por bolinhas (Aula 07).
import androidx.compose.ui.text.input.PasswordVisualTransformation
// Preview: pré-visualização no Android Studio.
import androidx.compose.ui.tooling.preview.Preview
// dp: unidade de tamanho.
import androidx.compose.ui.unit.dp
// sp: unidade de tamanho de texto.
import androidx.compose.ui.unit.sp
// Tema do projeto.
import com.example.myapplication.ui.theme.MyApplicationTheme

// -------------------------------------------------------------------------------------
// TelaLogin
// -------------------------------------------------------------------------------------
// Recebe o Modifier de fora (padrão do professor) e um callback onEntrar.
// A tela NÃO sabe para onde vai depois do login: ela só avisa "o usuário entrou".
// Quem decide o que acontece é a MainActivity. Isso é o callback da Aula 03.
@Composable
fun TelaLogin(
    // Modifier vindo de fora, para a MainActivity aplicar o padding do Scaffold.
    modifier: Modifier = Modifier,
    // Função sem parâmetros que a MainActivity passa para trocar de tela.
    onEntrar: () -> Unit
) {
    // ---------------------------------------------------------------------------------
    // ESTADOS DA TELA (Aula 07)
    // ---------------------------------------------------------------------------------
    // "by remember { mutableStateOf(...) }" guarda o valor e redesenha a tela quando ele muda.

    // Guarda qual aba está aberta: "entrar" ou "cadastrar".
    var aba by remember { mutableStateOf("entrar") }

    // Texto digitado no campo Nome (só aparece na aba Cadastrar).
    var nome by remember { mutableStateOf("") }

    // Texto digitado no campo E-mail.
    var email by remember { mutableStateOf("") }

    // Texto digitado no campo Senha.
    var senha by remember { mutableStateOf("") }

    // Marca se o usuário aceitou os termos (só usado na aba Cadastrar).
    var aceitou by remember { mutableStateOf(false) }

    // Mensagem de aviso mostrada na tela no lugar de um alerta pop-up.
    var recado by remember { mutableStateOf("") }

    // ---------------------------------------------------------------------------------
    // REGRAS DE VALIDAÇÃO (Aula 01/02: operadores e condições)
    // ---------------------------------------------------------------------------------

    // O e-mail é considerado válido se tiver arroba e ponto.
    // "contains" é a mesma função de busca que o professor usou nas coleções da Aula 04.
    val emailValido = email.contains("@") && email.contains(".")

    // A senha precisa ter pelo menos 4 caracteres. "length" é o tamanho da String.
    val senhaValida = senha.length >= 4

    // O nome precisa ter pelo menos 3 letras depois de tirar os espaços das pontas.
    // É exatamente a regra que o professor escreveu no FormularioAula.kt da Aula 07.
    val nomeValido = nome.trim().length >= 3

    // O botão só libera se tudo estiver válido. Na aba Cadastrar exige nome e termos também.
    val podeEnviar = if (aba == "entrar") {
        // Na aba Entrar basta e-mail e senha corretos.
        emailValido && senhaValida
    } else {
        // Na aba Cadastrar precisa também do nome e do aceite dos termos.
        emailValido && senhaValida && nomeValido && aceitou
    }

    // Cores das bordas dos campos. É o mesmo bloco que o professor escreveu na Aula 07.
    val coresInputs = OutlinedTextFieldDefaults.colors(
        // Borda quando o campo está selecionado.
        focusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
        // Borda quando o campo está parado.
        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
        // Borda quando o campo está desabilitado.
        disabledBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
    )

    // ---------------------------------------------------------------------------------
    // DESENHO DA TELA
    // ---------------------------------------------------------------------------------
    // Surface pinta o fundo azul claro de toda a tela.
    Surface(
        // Usa o Modifier recebido de fora e ocupa a tela inteira.
        modifier = modifier.fillMaxSize(),
        // Fundo azul claro do app, definido em CoresApp.kt.
        color = FundoApp
    ) {
        // Column empilha barra + conteúdo, e rola quando o teclado ocupa espaço.
        Column(
            // Começa a montar o Modifier deste elemento.
            modifier = Modifier
                // Ocupa toda a largura e toda a altura disponíveis.
                .fillMaxSize()
                // Permite rolar o conteúdo quando ele não cabe na tela (Aula 08).
                .verticalScroll(rememberScrollState()),
            // Centraliza tudo na horizontal.
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Barra azul do topo. Sem seta, porque esta é a primeira tela do app.
            BarraTopo(
                // Texto que aparece no meio da barra.
                titulo = "Receitas Diárias",
                // Esconde a seta de voltar.
                mostrarVoltar = false
            )

            // Respiro entre a barra e o logo.
            Spacer(modifier = Modifier.height(24.dp))

            // Logo grande: Card de 100dp com canto de 50dp, o que resulta num círculo.
            Card(
                // Quadrado de 100dp de lado.
                modifier = Modifier.size(100.dp),
                // Cantos arredondados em 50dp (Aula 08).
                shape = RoundedCornerShape(50.dp)
            ) {
                // Box centraliza a letra dentro do círculo.
                Box(
                    // Ocupa toda a tela.
                    modifier = Modifier.fillMaxSize(),
                    // Centraliza o conteúdo dentro da Box (Aula 06).
                    contentAlignment = Alignment.Center
                ) {
                    // Letra do logo.
                    Text(
                        // Texto que aparece na tela.
                        text = "R",
                        // Azul escuro da marca, definido em CoresApp.kt.
                        color = AzulEscuro,
                        // Tamanho da letra: 48sp.
                        fontSize = 48.sp,
                        // Deixa o texto em negrito.
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Respiro entre o logo e o card do formulário.
            Spacer(modifier = Modifier.height(20.dp))

            // Card branco que segura o formulário inteiro.
            Card(
                // Começa a montar o Modifier deste elemento.
                modifier = Modifier
                    // Ocupa toda a largura disponível.
                    .fillMaxWidth()
                    // Afasta o conteúdo 20dp de todas as bordas.
                    .padding(20.dp),
                // Cantos arredondados em 16dp (Aula 08).
                shape = RoundedCornerShape(16.dp)
            ) {
                // Column interna com 12dp de distância entre cada elemento.
                Column(
                    // Começa a montar o Modifier deste elemento.
                    modifier = Modifier
                        // Ocupa toda a largura disponível.
                        .fillMaxWidth()
                        // Afasta o conteúdo 20dp de todas as bordas.
                        .padding(20.dp),
                    // Centraliza os filhos na horizontal (Aula 07).
                    horizontalAlignment = Alignment.CenterHorizontally,
                    // Deixa 12dp de distância entre um filho e outro (Aula 07).
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    // Título do card.
                    Text(
                        // Texto que aparece na tela.
                        text = "Bem-vindo",
                        // titleLarge é o estilo de título que o professor usou na Aula 07.
                        style = MaterialTheme.typography.titleLarge,
                        // Deixa o texto em negrito.
                        fontWeight = FontWeight.Bold,
                        // Cor de título, definida em CoresApp.kt.
                        color = TextoTitulo
                    )

                    // Subtítulo explicativo.
                    Text(
                        // Texto que aparece na tela.
                        text = "Acesse sua conta ou cadastre-se",
                        // Tamanho da letra: 14sp.
                        fontSize = 14.sp,
                        // Cor de texto secundário, definida em CoresApp.kt.
                        color = TextoApoio
                    )

                    // ------------------- ABAS ENTRAR / CADASTRAR -------------------
                    // Não existe TabRow nas aulas, então as abas são dois botões:
                    // a aba ativa vira Button (com fundo) e a inativa vira TextButton (sem fundo).
                    Row(
                        // Ocupa toda a largura.
                        modifier = Modifier.fillMaxWidth(),
                        // Deixa 8dp de distância entre um filho e outro (Aula 07).
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        // Alinha os filhos pelo meio na vertical (Aula 06).
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Aba "Entrar".
                        if (aba == "entrar") {
                            // Aba ativa: botão cheio, ocupando metade da linha.
                            Button(
                                // Ação executada ao tocar no botão.
                                onClick = { aba = "entrar" },
                                // Estica para ocupar todo o espaco que sobrar.
                                modifier = Modifier.weight(1f)
                            ) {
                                // Texto exibido: "Entrar".
                                Text(text = "Entrar")
                            }
                        } else {
                            // Aba inativa: botão de texto, que ao ser clicado troca a aba.
                            TextButton(
                                // Ação executada ao tocar no botão.
                                onClick = {
                                    // Troca a aba.
                                    aba = "entrar"
                                    // Limpa qualquer aviso antigo da tela.
                                    recado = ""
                                },
                                // Estica para ocupar todo o espaco que sobrar.
                                modifier = Modifier.weight(1f)
                            ) {
                                // Texto exibido: "Entrar".
                                Text(text = "Entrar")
                            }
                        }

                        // Aba "Cadastrar", com a mesma lógica invertida.
                        if (aba == "cadastrar") {
                            // Botão cheio, usado na opção que está ativa (Aula 07).
                            Button(
                                // Ação executada ao tocar no botão.
                                onClick = { aba = "cadastrar" },
                                // Estica para ocupar todo o espaco que sobrar.
                                modifier = Modifier.weight(1f)
                            ) {
                                // Texto exibido: "Cadastrar".
                                Text(text = "Cadastrar")
                            }
                        } else {
                            // Botão só de texto, usado na opção inativa (Aula 07).
                            TextButton(
                                // Ação executada ao tocar no botão.
                                onClick = {
                                    // Passa a mostrar a aba de cadastro.
                                    aba = "cadastrar"
                                    // Limpa qualquer aviso que estivesse na tela.
                                    recado = ""
                                },
                                // Estica para ocupar todo o espaco que sobrar.
                                modifier = Modifier.weight(1f)
                            ) {
                                // Texto exibido: "Cadastrar".
                                Text(text = "Cadastrar")
                            }
                        }
                    }

                    // ------------------- CAMPO NOME (só na aba Cadastrar) -------------------
                    // "if" decide se o campo entra ou não na tela.
                    if (aba == "cadastrar") {
                        // Campo de texto do nome.
                        OutlinedTextField(
                            // Valor atual do campo.
                            value = nome,
                            // Toda tecla digitada cai aqui e atualiza o estado.
                            onValueChange = { nome = it },
                            // Rótulo flutuante do campo.
                            label = { Text("Nome Completo") },
                            // Ocupa toda a largura do card.
                            modifier = Modifier.fillMaxWidth(),
                            // Ícone de pessoa na esquerda do campo.
                            leadingIcon = {
                                // Ícone carregado de res/drawable (Aula 06/07).
                                Icon(
                                    // Carrega o desenho de res/drawable pelo R.drawable (Aula 06/07).
                                    painter = painterResource(R.drawable.ic_pessoa),
                                    // Descrição usada pelos leitores de tela.
                                    contentDescription = "Nome",
                                    // Quadrado de 24dp de lado.
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            // Cores definidas lá em cima.
                            colors = coresInputs,
                            // Deixa a borda vermelha quando já digitou algo mas ainda está curto.
                            isError = nome.isNotEmpty() && !nomeValido
                        )

                        // Mensagem de erro do nome, no lugar de um alerta pop-up.
                        if (nome.isNotEmpty() && !nomeValido) {
                            // Texto exibido neste ponto da tela.
                            Text(
                                // Texto que aparece na tela.
                                text = "O nome deve conter pelo menos 3 caracteres",
                                // Vermelho de erro do tema, igual ao usado na Aula 07.
                                color = MaterialTheme.colorScheme.error,
                                // Tamanho da letra: 12sp.
                                fontSize = 12.sp
                            )
                        }
                    }

                    // ------------------- CAMPO E-MAIL -------------------
                    OutlinedTextField(
                        // Valor atual do campo, vindo do estado.
                        value = email,
                        // Recebe cada tecla digitada e atualiza o estado (Aula 07).
                        onValueChange = { email = it },
                        // Rotulo flutuante do campo (Aula 07).
                        label = { Text("E-mail") },
                        // Ocupa toda a largura.
                        modifier = Modifier.fillMaxWidth(),
                        // Ícone de envelope na esquerda.
                        leadingIcon = {
                            // Ícone carregado de res/drawable (Aula 06/07).
                            Icon(
                                // Carrega o desenho de res/drawable pelo R.drawable (Aula 06/07).
                                painter = painterResource(R.drawable.ic_email),
                                // Descrição usada pelos leitores de tela.
                                contentDescription = "E-mail",
                                // Quadrado de 24dp de lado.
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        // Cores da borda definidas no bloco coresInputs.
                        colors = coresInputs,
                        // Abre o teclado com a tecla de arroba, igual à Aula 07.
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        // Borda vermelha enquanto o e-mail estiver incompleto.
                        isError = email.isNotEmpty() && !emailValido
                    )

                    // Mensagem de erro do e-mail.
                    if (email.isNotEmpty() && !emailValido) {
                        // Texto exibido neste ponto da tela.
                        Text(
                            // Texto que aparece na tela.
                            text = "Digite um e-mail válido, com @ e ponto",
                            // Vermelho de erro do tema, igual ao usado na Aula 07.
                            color = MaterialTheme.colorScheme.error,
                            // Tamanho da letra: 12sp.
                            fontSize = 12.sp
                        )
                    }

                    // ------------------- CAMPO SENHA -------------------
                    OutlinedTextField(
                        // Valor atual do campo, vindo do estado.
                        value = senha,
                        // Recebe cada tecla digitada e atualiza o estado (Aula 07).
                        onValueChange = { senha = it },
                        // Rotulo flutuante do campo (Aula 07).
                        label = { Text("Senha") },
                        // Ocupa toda a largura.
                        modifier = Modifier.fillMaxWidth(),
                        // Ícone de cadeado na esquerda.
                        leadingIcon = {
                            // Ícone carregado de res/drawable (Aula 06/07).
                            Icon(
                                // Carrega o desenho de res/drawable pelo R.drawable (Aula 06/07).
                                painter = painterResource(R.drawable.ic_cadeado),
                                // Descrição usada pelos leitores de tela.
                                contentDescription = "Senha",
                                // Quadrado de 24dp de lado.
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        // Cores da borda definidas no bloco coresInputs.
                        colors = coresInputs,
                        // Esconde a senha atrás de bolinhas. Exatamente a linha da Aula 07.
                        visualTransformation = PasswordVisualTransformation(),
                        // Teclado de senha.
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        // Borda vermelha enquanto a senha for curta demais.
                        isError = senha.isNotEmpty() && !senhaValida
                    )

                    // Mensagem de erro da senha.
                    if (senha.isNotEmpty() && !senhaValida) {
                        // Texto exibido neste ponto da tela.
                        Text(
                            // Texto que aparece na tela.
                            text = "A senha deve ter pelo menos 4 caracteres",
                            // Vermelho de erro do tema, igual ao usado na Aula 07.
                            color = MaterialTheme.colorScheme.error,
                            // Tamanho da letra: 12sp.
                            fontSize = 12.sp
                        )
                    }

                    // ------------------- ACEITE DOS TERMOS (só na aba Cadastrar) -------------------
                    if (aba == "cadastrar") {
                        // Linha com a caixa de marcar e o texto ao lado.
                        Row(
                            // Alinha a caixinha com o texto pelo meio.
                            verticalAlignment = Alignment.CenterVertically,
                            // A linha inteira é clicável, e não só a caixinha (padrão da Aula 07).
                            modifier = Modifier
                                // Ocupa toda a largura disponível.
                                .fillMaxWidth()
                                // Torna este elemento clicável (Aula 07).
                                .clickable { aceitou = !aceitou }
                        ) {
                            // A caixa de marcar em si.
                            Checkbox(
                                // Diz se a caixa esta marcada ou nao.
                                checked = aceitou,
                                // Executado quando o usuário marca ou desmarca a caixa.
                                onCheckedChange = { aceitou = it }
                            )
                            // Texto ao lado da caixa.
                            Text(
                                // Texto que aparece na tela.
                                text = "Aceito os termos de uso",
                                // Tamanho da letra: 14sp.
                                fontSize = 14.sp,
                                // Cor de texto secundário, definida em CoresApp.kt.
                                color = TextoApoio
                            )
                        }
                    }

                    // ------------------- ESQUECI A SENHA -------------------
                    // Só faz sentido na aba Entrar.
                    if (aba == "entrar") {
                        // Botão só de texto, usado na opção inativa (Aula 07).
                        TextButton(
                            // Ação executada ao tocar no botão.
                            onClick = {
                                // No lugar de um alerta, escreve o recado na própria tela.
                                recado = "Enviamos as instruções para o e-mail informado."
                            }
                        ) {
                            // Texto exibido: "Esqueci a Senha?".
                            Text(text = "Esqueci a Senha?")
                        }
                    }

                    // ------------------- BOTÃO PRINCIPAL -------------------
                    Button(
                        // Ação executada ao tocar no botão.
                        onClick = {
                            // Limpa avisos antigos antes de entrar.
                            recado = ""
                            // Avisa a MainActivity que o usuário concluiu o login.
                            onEntrar()
                        },
                        // O botão fica apagado enquanto o formulário estiver inválido.
                        // É o mesmo "enabled" que o professor usou no fim do FormularioAula.
                        enabled = podeEnviar,
                        // Começa a montar o Modifier deste elemento.
                        modifier = Modifier
                            // Ocupa toda a largura disponível.
                            .fillMaxWidth()
                            // Altura fixa de 50dp.
                            .height(50.dp)
                    ) {
                        // O texto do botão muda conforme a aba aberta.
                        if (aba == "entrar") {
                            // Texto exibido: "Entrar".
                            Text(text = "Entrar")
                        } else {
                            // Texto exibido: "Criar conta".
                            Text(text = "Criar conta")
                        }
                    }

                    // Aviso mostrado quando o formulário ainda não está completo.
                    if (!podeEnviar) {
                        // Texto exibido neste ponto da tela.
                        Text(
                            // Texto que aparece na tela.
                            text = "Preencha os campos corretamente para continuar",
                            // Cor de texto secundário, definida em CoresApp.kt.
                            color = TextoApoio,
                            // Tamanho da letra: 12sp.
                            fontSize = 12.sp
                        )
                    }

                    // ------------------- RECADO NA TELA -------------------
                    // Aparece só quando existe alguma mensagem para mostrar.
                    if (recado.isNotEmpty()) {
                        // Surface azul clarinha destacando o recado.
                        Surface(
                            // Ocupa toda a largura.
                            modifier = Modifier.fillMaxWidth(),
                            // Fundo azul claro do app, definido em CoresApp.kt.
                            color = FundoApp
                        ) {
                            // Texto exibido neste ponto da tela.
                            Text(
                                // Texto que aparece na tela.
                                text = recado,
                                // Afasta o texto do recado 12dp das bordas.
                                modifier = Modifier.padding(12.dp),
                                // Azul escuro da marca, definido em CoresApp.kt.
                                color = AzulEscuro,
                                // Tamanho da letra: 13sp.
                                fontSize = 13.sp
                            )
                        }
                    }

                    // ------------------- ENTRAR COM OUTRAS CONTAS -------------------
                    // Texto separando a área dos botões sociais.
                    Text(
                        // Texto que aparece na tela.
                        text = "ou entre com",
                        // Tamanho da letra: 12sp.
                        fontSize = 12.sp,
                        // Cor de texto secundário, definida em CoresApp.kt.
                        color = TextoApoio
                    )

                    // Linha com os três botões redondos.
                    Row(
                        // Ocupa toda a largura.
                        modifier = Modifier.fillMaxWidth(),
                        // Espalha os três com o mesmo espaço entre eles.
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        // Alinha os filhos pelo meio na vertical (Aula 06).
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Botão do Google.
                        Card(
                            // Começa a montar o Modifier deste elemento.
                            modifier = Modifier
                                // Largura e altura de 48dp.
                                .size(48.dp)
                                // Torna este elemento clicável (Aula 07).
                                .clickable {
                                    // Mostra o recado em vez de um alerta.
                                    recado = "Login com Google ainda não está disponível."
                                },
                            // Cantos arredondados em 24dp (Aula 08).
                            shape = RoundedCornerShape(24.dp)
                        ) {
                            // Centraliza a letra dentro do círculo.
                            Box(
                                // Ocupa toda a tela.
                                modifier = Modifier.fillMaxSize(),
                                // Centraliza o conteúdo dentro da Box (Aula 06).
                                contentAlignment = Alignment.Center
                            ) {
                                // Texto exibido neste ponto da tela.
                                Text(
                                    // Texto que aparece na tela.
                                    text = "G",
                                    // Deixa o texto em negrito.
                                    fontWeight = FontWeight.Bold,
                                    // Cor de título, definida em CoresApp.kt.
                                    color = TextoTitulo
                                )
                            }
                        }

                        // Botão do Facebook, mesma estrutura.
                        Card(
                            // Começa a montar o Modifier deste elemento.
                            modifier = Modifier
                                // Largura e altura de 48dp.
                                .size(48.dp)
                                // Torna este elemento clicável (Aula 07).
                                .clickable {
                                    // Mostra o aviso na tela, sem usar alerta pop-up.
                                    recado = "Login com Facebook ainda não está disponível."
                                },
                            // Cantos arredondados em 24dp (Aula 08).
                            shape = RoundedCornerShape(24.dp)
                        ) {
                            // Caixa usada para centralizar o conteúdo (Aula 06).
                            Box(
                                // Ocupa toda a tela.
                                modifier = Modifier.fillMaxSize(),
                                // Centraliza o conteúdo dentro da Box (Aula 06).
                                contentAlignment = Alignment.Center
                            ) {
                                // Texto exibido neste ponto da tela.
                                Text(
                                    // Texto que aparece na tela.
                                    text = "f",
                                    // Deixa o texto em negrito.
                                    fontWeight = FontWeight.Bold,
                                    // Cor de título, definida em CoresApp.kt.
                                    color = TextoTitulo
                                )
                            }
                        }

                        // Botão da Apple, mesma estrutura.
                        Card(
                            // Começa a montar o Modifier deste elemento.
                            modifier = Modifier
                                // Largura e altura de 48dp.
                                .size(48.dp)
                                // Torna este elemento clicável (Aula 07).
                                .clickable {
                                    // Mostra o aviso na tela, sem usar alerta pop-up.
                                    recado = "Login com Apple ainda não está disponível."
                                },
                            // Cantos arredondados em 24dp (Aula 08).
                            shape = RoundedCornerShape(24.dp)
                        ) {
                            // Caixa usada para centralizar o conteúdo (Aula 06).
                            Box(
                                // Ocupa toda a tela.
                                modifier = Modifier.fillMaxSize(),
                                // Centraliza o conteúdo dentro da Box (Aula 06).
                                contentAlignment = Alignment.Center
                            ) {
                                // Texto exibido neste ponto da tela.
                                Text(
                                    // Texto que aparece na tela.
                                    text = "A",
                                    // Deixa o texto em negrito.
                                    fontWeight = FontWeight.Bold,
                                    // Cor de título, definida em CoresApp.kt.
                                    color = TextoTitulo
                                )
                            }
                        }
                    }

                    // ------------------- TROCA DE ABA PELO RODAPÉ -------------------
                    // Botão de texto que alterna entre as duas abas.
                    TextButton(
                        // Ação executada ao tocar no botão.
                        onClick = {
                            // Se está em "entrar" vai para "cadastrar", e vice-versa.
                            if (aba == "entrar") {
                                // Passa a mostrar a aba de cadastro.
                                aba = "cadastrar"
                            } else {
                                // Passa a mostrar a aba de entrar.
                                aba = "entrar"
                            }
                            // Limpa o recado ao trocar de aba.
                            recado = ""
                        }
                    ) {
                        // O texto do rodapé também muda conforme a aba.
                        if (aba == "entrar") {
                            // Texto exibido: "Não tem conta? Cadastre-se aqui".
                            Text(text = "Não tem conta? Cadastre-se aqui")
                        } else {
                            // Texto exibido: "Já tem conta? Entre aqui".
                            Text(text = "Já tem conta? Entre aqui")
                        }
                    }
                }
            }

            // Espaço final para o conteúdo não colar no fim da tela quando rolar.
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

// -------------------------------------------------------------------------------------
// Pré-visualização da tela no Android Studio.
// -------------------------------------------------------------------------------------
@Preview(showBackground = true)
@Composable
fun TelaLoginPreview() {
    // O tema envolve a tela para as cores saírem corretas na prévia.
    MyApplicationTheme {
        // Na prévia o callback fica vazio, porque não existe navegação aqui dentro.
        TelaLogin(onEntrar = { })
    }
}
