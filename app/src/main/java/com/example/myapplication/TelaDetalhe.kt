// =====================================================================================
// ARQUIVO: TelaDetalhe.kt
// TELA 3 : Detalhe da receita (imagem, dados, favorito, ingredientes e modo de preparo)
//
// O QUE ESTA TELA PROVA NA DEFESA:
//   - Column com verticalScroll(rememberScrollState()) ....... Aula 08 (ListaColumn)
//   - Checkbox com estado por item ........................... Aula 07 (FormularioAula)
//   - when para escolher o conteúdo da aba ................... Aula 02 (Condicionais)
//   - forEach percorrendo List<String> ...................... Aula 04 (Coleções)
//   - mutableListOf + add + removeIf + contains ............. Aula 04 (Coleções)
//   - Callback de favoritar devolvido para a MainActivity ... Aula 03 (Funções)
//
// POR QUE NÃO USAMOS LazyColumn AQUI:
//   Esta tela rola inteira, e colocar uma LazyColumn dentro de outra área que já rola
//   quebra o app. O professor mostrou a alternativa correta na Aula 08, na função
//   ListaColumn: Column + verticalScroll(rememberScrollState()) + forEach.
// =====================================================================================

// Mesmo pacote dos outros arquivos.
package com.example.myapplication

// Arrangement: espaçamento entre filhos.
import androidx.compose.foundation.layout.Arrangement
// Box: caixa para centralizar.
import androidx.compose.foundation.layout.Box
// Column: empilha na vertical.
import androidx.compose.foundation.layout.Column
// Row: coloca lado a lado.
import androidx.compose.foundation.layout.Row
// Spacer: espaço vazio.
import androidx.compose.foundation.layout.Spacer
// fillMaxSize: ocupa tudo.
import androidx.compose.foundation.layout.fillMaxSize
// fillMaxWidth: ocupa a largura toda.
import androidx.compose.foundation.layout.fillMaxWidth
// height: altura fixa.
import androidx.compose.foundation.layout.height
// padding: afastamento interno.
import androidx.compose.foundation.layout.padding
// size: largura e altura juntas.
import androidx.compose.foundation.layout.size
// width: largura fixa.
import androidx.compose.foundation.layout.width
// clickable: torna clicável.
import androidx.compose.foundation.clickable
// rememberScrollState: guarda a posição da rolagem (Aula 08).
import androidx.compose.foundation.rememberScrollState
// verticalScroll: permite rolar a Column (Aula 08).
import androidx.compose.foundation.verticalScroll
// RoundedCornerShape: canto arredondado.
import androidx.compose.foundation.shape.RoundedCornerShape
// Button: botão cheio.
import androidx.compose.material3.Button
// Card: bloco branco com sombra.
import androidx.compose.material3.Card
// CardDefaults: define a sombra do Card.
import androidx.compose.material3.CardDefaults
// Checkbox: caixa de marcar (Aula 07).
import androidx.compose.material3.Checkbox
// Icon: desenha um vetor do res/drawable.
import androidx.compose.material3.Icon
// MaterialTheme: cores e tipografia do tema.
import androidx.compose.material3.MaterialTheme
// Surface: bloco colorido.
import androidx.compose.material3.Surface
// Text: escreve texto.
import androidx.compose.material3.Text
// TextButton: botão só de texto.
import androidx.compose.material3.TextButton
// Composable: marca a função como desenhável.
import androidx.compose.runtime.Composable
// getValue: para ler estado com "by".
import androidx.compose.runtime.getValue
// mutableStateOf: cria estado observável.
import androidx.compose.runtime.mutableStateOf
// remember: mantém o estado entre redesenhos.
import androidx.compose.runtime.remember
// setValue: para escrever estado com "by".
import androidx.compose.runtime.setValue
// Alignment: alinhamentos.
import androidx.compose.ui.Alignment
// Modifier: tamanho, espaçamento, clique.
import androidx.compose.ui.Modifier
// Color: tipo de cor, para transformar o Long da receita em cor.
import androidx.compose.ui.graphics.Color
// painterResource: carrega o desenho do res/drawable.
import androidx.compose.ui.res.painterResource
// FontWeight: peso da fonte.
import androidx.compose.ui.text.font.FontWeight
// Preview: pré-visualização.
import androidx.compose.ui.tooling.preview.Preview
// dp: unidade de tamanho.
import androidx.compose.ui.unit.dp
// sp: unidade de tamanho de texto.
import androidx.compose.ui.unit.sp
// Tema do projeto.
import com.example.myapplication.ui.theme.MyApplicationTheme

// -------------------------------------------------------------------------------------
// TelaDetalhe
// -------------------------------------------------------------------------------------
// Recebe a receita já escolhida e devolve apenas dois avisos: "favoritar" e "voltar".
@Composable
fun TelaDetalhe(
    // Modifier vindo de fora, para o padding do Scaffold.
    modifier: Modifier = Modifier,
    // A receita que o usuário tocou na tela anterior.
    receita: Receita,
    // Diz se esta receita já está favoritada. Quem sabe disso é a MainActivity.
    favorito: Boolean,
    // Avisa a MainActivity que o usuário clicou na estrela.
    onFavoritar: () -> Unit,
    // Avisa a MainActivity que o usuário quer voltar para a lista.
    onVoltar: () -> Unit
) {
    // ---------------------------------------------------------------------------------
    // ESTADOS DA TELA
    // ---------------------------------------------------------------------------------

    // Qual aba está aberta: "ingredientes" ou "preparo".
    var aba by remember { mutableStateOf("ingredientes") }

    // Lista com os ingredientes que o usuário já marcou na caixinha.
    // Guardamos o texto do ingrediente porque cada um é único dentro da receita.
    var marcados by remember { mutableStateOf(listOf<String>()) }

    // ---------------------------------------------------------------------------------
    // DESENHO DA TELA
    // ---------------------------------------------------------------------------------
    // Fundo azul claro cobrindo tudo.
    Surface(
        // Usa o Modifier recebido de fora e ocupa a tela inteira.
        modifier = modifier.fillMaxSize(),
        // Fundo azul claro do app, definido em CoresApp.kt.
        color = FundoApp
    ) {
        // Column externa: barra fixa em cima e conteúdo rolável embaixo.
        Column(
            // Ocupa toda a tela.
            modifier = Modifier.fillMaxSize()
        ) {

            // Barra do topo com a seta que volta para a lista.
            BarraTopo(
                // Texto que aparece no meio da barra.
                titulo = "Receitas Diárias",
                // Liga a seta de voltar.
                mostrarVoltar = true,
                // O que acontece quando o usuário toca na seta.
                onVoltar = { onVoltar() }
            )

            // Column rolável com todo o conteúdo da receita.
            // verticalScroll é exatamente o que o professor usou na função ListaColumn.
            Column(
                // Começa a montar o Modifier deste elemento.
                modifier = Modifier
                    // Ocupa toda a largura e toda a altura disponíveis.
                    .fillMaxSize()
                    // Permite rolar o conteúdo quando ele não cabe na tela (Aula 08).
                    .verticalScroll(rememberScrollState())
            ) {

                // Card branco que segura a receita inteira.
                Card(
                    // Começa a montar o Modifier deste elemento.
                    modifier = Modifier
                        // Ocupa toda a largura disponível.
                        .fillMaxWidth()
                        // Afasta o conteúdo 16dp de todas as bordas.
                        .padding(16.dp),
                    // Sombra igual à usada na Aula 08.
                    elevation = CardDefaults.cardElevation(4.dp),
                    // Cantos arredondados em 16dp (Aula 08).
                    shape = RoundedCornerShape(16.dp)
                ) {

                    // ------------------- FAIXA COLORIDA COM O DESENHO DO PRATO -------------------
                    // Mesma ideia da função CriarElemento da Aula 06: Surface colorida + Icon branco.
                    Surface(
                        // Começa a montar o Modifier deste elemento.
                        modifier = Modifier
                            // Ocupa toda a largura disponível.
                            .fillMaxWidth()
                            // Altura fixa de 180dp.
                            .height(180.dp),
                        // Cor da receita, convertida de Long para Color.
                        color = Color(receita.cor)
                    ) {
                        // Box centraliza o desenho grande no meio da faixa.
                        Box(
                            // Ocupa toda a tela.
                            modifier = Modifier.fillMaxSize(),
                            // Centraliza o conteúdo dentro da Box (Aula 06).
                            contentAlignment = Alignment.Center
                        ) {
                            // Ícone carregado de res/drawable (Aula 06/07).
                            Icon(
                                // Carrega o desenho de res/drawable pelo R.drawable (Aula 06/07).
                                painter = painterResource(receita.icone),
                                // Descrição usada pelos leitores de tela.
                                contentDescription = receita.nome,
                                // Cor com que o desenho é pintado (Aula 06).
                                tint = BrancoCard,
                                // Quadrado de 90dp de lado.
                                modifier = Modifier.size(90.dp)
                            )
                        }
                    }

                    // Column com todo o texto da receita, afastado 16dp das bordas.
                    Column(
                        // Começa a montar o Modifier deste elemento.
                        modifier = Modifier
                            // Ocupa toda a largura disponível.
                            .fillMaxWidth()
                            // Afasta o conteúdo 16dp de todas as bordas.
                            .padding(16.dp)
                    ) {

                        // ------------------- NOME + BOTÃO FAVORITO -------------------
                        Row(
                            // Ocupa toda a largura.
                            modifier = Modifier.fillMaxWidth(),
                            // Alinha os filhos pelo meio na vertical (Aula 06).
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Nome da receita. weight(1f) empurra a estrela para a direita.
                            Text(
                                // Texto que aparece na tela.
                                text = receita.nome,
                                // Estica para ocupar todo o espaco que sobrar.
                                modifier = Modifier.weight(1f),
                                // Estilo de título do tema, usado pelo professor na Aula 07.
                                style = MaterialTheme.typography.titleLarge,
                                // Deixa o texto em negrito.
                                fontWeight = FontWeight.Bold,
                                // Cor de título, definida em CoresApp.kt.
                                color = TextoTitulo
                            )

                            // Coluna da estrela, com a legenda embaixo.
                            Column(
                                // Centraliza os filhos na horizontal (Aula 07).
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                // Card circular clicável que funciona como botão de favorito.
                                Card(
                                    // Começa a montar o Modifier deste elemento.
                                    modifier = Modifier
                                        // Largura e altura de 48dp.
                                        .size(48.dp)
                                        // O clique não altera nada aqui: só avisa a MainActivity.
                                        .clickable { onFavoritar() },
                                    // Cantos arredondados em 24dp (Aula 08).
                                    shape = RoundedCornerShape(24.dp)
                                ) {
                                    // Box centraliza a estrela dentro do círculo.
                                    Box(
                                        // Ocupa toda a tela.
                                        modifier = Modifier.fillMaxSize(),
                                        // Centraliza o conteúdo dentro da Box (Aula 06).
                                        contentAlignment = Alignment.Center
                                    ) {
                                        // A cor da estrela depende de estar favoritada ou não.
                                        // if usado como expressão que devolve um valor (Aula 02).
                                        Icon(
                                            // Carrega o desenho de res/drawable pelo R.drawable (Aula 06/07).
                                            painter = painterResource(R.drawable.ic_estrela),
                                            // Descrição usada pelos leitores de tela.
                                            contentDescription = "Favorito",
                                            // Cor com que o desenho é pintado (Aula 06).
                                            tint = if (favorito) {
                                                // Amarelo quando está favoritada.
                                                Color(0xFFF5A623)
                                            } else {
                                                // Cinza claro quando não está.
                                                LinhaDivisoria
                                            },
                                            // Quadrado de 28dp de lado.
                                            modifier = Modifier.size(28.dp)
                                        )
                                    }
                                }

                                // Legenda embaixo da estrela.
                                Text(
                                    // Texto que aparece na tela.
                                    text = "Favorito",
                                    // Tamanho da letra: 11sp.
                                    fontSize = 11.sp,
                                    // Cor de texto secundário, definida em CoresApp.kt.
                                    color = TextoApoio
                                )
                            }
                        }

                        // Espaço entre o título e a linha de informações.
                        Spacer(modifier = Modifier.height(16.dp))

                        // ------------------- TEMPO, PORÇÕES E DIFICULDADE -------------------
                        Row(
                            // Ocupa toda a largura.
                            modifier = Modifier.fillMaxWidth(),
                            // Alinha os filhos pelo meio na vertical (Aula 06).
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            // Bloco 1: tempo. weight(1f) divide a linha em três partes iguais.
                            Column(
                                // Estica para ocupar todo o espaco que sobrar.
                                modifier = Modifier.weight(1f)
                            ) {
                                // Linha com o ícone e a palavra "Tempo".
                                Row(
                                    // Alinha os filhos pelo meio na vertical (Aula 06).
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Ícone carregado de res/drawable (Aula 06/07).
                                    Icon(
                                        // Carrega o desenho de res/drawable pelo R.drawable (Aula 06/07).
                                        painter = painterResource(R.drawable.ic_relogio),
                                        // Descrição usada pelos leitores de tela.
                                        contentDescription = "Tempo",
                                        // Cor com que o desenho é pintado (Aula 06).
                                        tint = TextoApoio,
                                        // Quadrado de 18dp de lado.
                                        modifier = Modifier.size(18.dp)
                                    )
                                    // Espaço horizontal de 4dp.
                                    Spacer(modifier = Modifier.width(4.dp))
                                    // Texto exibido neste ponto da tela.
                                    Text(
                                        // Texto que aparece na tela.
                                        text = "Tempo",
                                        // Tamanho da letra: 11sp.
                                        fontSize = 11.sp,
                                        // Cor de texto secundário, definida em CoresApp.kt.
                                        color = TextoApoio
                                    )
                                }
                                // O valor em si, em negrito.
                                Text(
                                    // Texto que aparece na tela.
                                    text = receita.tempo,
                                    // Tamanho da letra: 13sp.
                                    fontSize = 13.sp,
                                    // Deixa o texto em negrito.
                                    fontWeight = FontWeight.Bold,
                                    // Cor de título, definida em CoresApp.kt.
                                    color = TextoTitulo
                                )
                            }

                            // Bloco 2: porções.
                            Column(
                                // Estica para ocupar todo o espaco que sobrar.
                                modifier = Modifier.weight(1f)
                            ) {
                                // Linha que coloca os filhos lado a lado (Aula 06).
                                Row(
                                    // Alinha os filhos pelo meio na vertical (Aula 06).
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Ícone carregado de res/drawable (Aula 06/07).
                                    Icon(
                                        // Carrega o desenho de res/drawable pelo R.drawable (Aula 06/07).
                                        painter = painterResource(R.drawable.ic_pessoas),
                                        // Descrição usada pelos leitores de tela.
                                        contentDescription = "Porções",
                                        // Cor com que o desenho é pintado (Aula 06).
                                        tint = TextoApoio,
                                        // Quadrado de 18dp de lado.
                                        modifier = Modifier.size(18.dp)
                                    )
                                    // Espaço horizontal de 4dp.
                                    Spacer(modifier = Modifier.width(4.dp))
                                    // Texto exibido neste ponto da tela.
                                    Text(
                                        // Texto que aparece na tela.
                                        text = "Porções",
                                        // Tamanho da letra: 11sp.
                                        fontSize = 11.sp,
                                        // Cor de texto secundário, definida em CoresApp.kt.
                                        color = TextoApoio
                                    )
                                }
                                // Texto exibido neste ponto da tela.
                                Text(
                                    // Texto que aparece na tela.
                                    text = receita.porcoes,
                                    // Tamanho da letra: 13sp.
                                    fontSize = 13.sp,
                                    // Deixa o texto em negrito.
                                    fontWeight = FontWeight.Bold,
                                    // Cor de título, definida em CoresApp.kt.
                                    color = TextoTitulo
                                )
                            }

                            // Bloco 3: dificuldade.
                            Column(
                                // Estica para ocupar todo o espaco que sobrar.
                                modifier = Modifier.weight(1f)
                            ) {
                                // Linha que coloca os filhos lado a lado (Aula 06).
                                Row(
                                    // Alinha os filhos pelo meio na vertical (Aula 06).
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Ícone carregado de res/drawable (Aula 06/07).
                                    Icon(
                                        // Carrega o desenho de res/drawable pelo R.drawable (Aula 06/07).
                                        painter = painterResource(R.drawable.ic_dificuldade),
                                        // Descrição usada pelos leitores de tela.
                                        contentDescription = "Dificuldade",
                                        // Cor com que o desenho é pintado (Aula 06).
                                        tint = TextoApoio,
                                        // Quadrado de 18dp de lado.
                                        modifier = Modifier.size(18.dp)
                                    )
                                    // Espaço horizontal de 4dp.
                                    Spacer(modifier = Modifier.width(4.dp))
                                    // Texto exibido neste ponto da tela.
                                    Text(
                                        // Texto que aparece na tela.
                                        text = "Dificuldade",
                                        // Tamanho da letra: 11sp.
                                        fontSize = 11.sp,
                                        // Cor de texto secundário, definida em CoresApp.kt.
                                        color = TextoApoio
                                    )
                                }
                                // Texto exibido neste ponto da tela.
                                Text(
                                    // Texto que aparece na tela.
                                    text = receita.dificuldade,
                                    // Tamanho da letra: 13sp.
                                    fontSize = 13.sp,
                                    // Deixa o texto em negrito.
                                    fontWeight = FontWeight.Bold,
                                    // Cor de título, definida em CoresApp.kt.
                                    color = TextoTitulo
                                )
                            }
                        }

                        // Espaço antes das abas.
                        Spacer(modifier = Modifier.height(16.dp))

                        // ------------------- ABAS INGREDIENTES / MODO DE PREPARO -------------------
                        // Mesma solução das abas do login: a ativa é Button, a inativa é TextButton.
                        Row(
                            // Ocupa toda a largura.
                            modifier = Modifier.fillMaxWidth(),
                            // Deixa 8dp de distância entre um filho e outro (Aula 07).
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            // Alinha os filhos pelo meio na vertical (Aula 06).
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Aba dos ingredientes.
                            if (aba == "ingredientes") {
                                // Botão cheio, usado na opção que está ativa (Aula 07).
                                Button(
                                    // Ação executada ao tocar no botão.
                                    onClick = { aba = "ingredientes" },
                                    // Estica para ocupar todo o espaco que sobrar.
                                    modifier = Modifier.weight(1f)
                                ) {
                                    // Texto exibido: "Ingredientes".
                                    Text(text = "Ingredientes")
                                }
                            } else {
                                // Botão só de texto, usado na opção inativa (Aula 07).
                                TextButton(
                                    // Ação executada ao tocar no botão.
                                    onClick = { aba = "ingredientes" },
                                    // Estica para ocupar todo o espaco que sobrar.
                                    modifier = Modifier.weight(1f)
                                ) {
                                    // Texto exibido: "Ingredientes".
                                    Text(text = "Ingredientes")
                                }
                            }

                            // Aba do modo de preparo.
                            if (aba == "preparo") {
                                // Botão cheio, usado na opção que está ativa (Aula 07).
                                Button(
                                    // Ação executada ao tocar no botão.
                                    onClick = { aba = "preparo" },
                                    // Estica para ocupar todo o espaco que sobrar.
                                    modifier = Modifier.weight(1f)
                                ) {
                                    // Texto exibido: "Modo de Preparo".
                                    Text(text = "Modo de Preparo")
                                }
                            } else {
                                // Botão só de texto, usado na opção inativa (Aula 07).
                                TextButton(
                                    // Ação executada ao tocar no botão.
                                    onClick = { aba = "preparo" },
                                    // Estica para ocupar todo o espaco que sobrar.
                                    modifier = Modifier.weight(1f)
                                ) {
                                    // Texto exibido: "Modo de Preparo".
                                    Text(text = "Modo de Preparo")
                                }
                            }
                        }

                        // Linha fina separando as abas do conteúdo.
                        // É uma Surface de 1dp de altura, porque Divider não apareceu em aula.
                        Surface(
                            // Começa a montar o Modifier deste elemento.
                            modifier = Modifier
                                // Ocupa toda a largura disponível.
                                .fillMaxWidth()
                                // Altura fixa de 1dp.
                                .height(1.dp),
                            // Cinza claro da linha divisoria, definido em CoresApp.kt.
                            color = LinhaDivisoria
                        ) { }

                        // Espaço depois da linha.
                        Spacer(modifier = Modifier.height(12.dp))

                        // ------------------- CONTEÚDO DA ABA -------------------
                        // when escolhe o bloco a desenhar, igual ao when da Aula 02.
                        when (aba) {

                            // ---------- ABA INGREDIENTES ----------
                            "ingredientes" -> {
                                // Column com todos os ingredientes empilhados.
                                Column(
                                    // Ocupa toda a largura.
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    // Contador de progresso: quantos de quantos já foram separados.
                                    // size é o tamanho da lista, da Aula 04.
                                    Text(
                                        // Texto que aparece na tela.
                                        text = "${marcados.size} de ${receita.ingredientes.size} ingredientes separados",
                                        // Tamanho da letra: 12sp.
                                        fontSize = 12.sp,
                                        // Cor de texto secundário, definida em CoresApp.kt.
                                        color = TextoApoio
                                    )

                                    // Espaço entre o contador e a lista.
                                    Spacer(modifier = Modifier.height(8.dp))

                                    // forEach percorre a lista de ingredientes (Aula 04).
                                    receita.ingredientes.forEach { ingrediente ->

                                        // Verifica se este ingrediente já está marcado.
                                        // contains é a busca em lista da Aula 04.
                                        val estaMarcado = marcados.contains(ingrediente)

                                        // Linha com a caixinha e o texto do ingrediente.
                                        Row(
                                            // Começa a montar o Modifier deste elemento.
                                            modifier = Modifier
                                                // Ocupa toda a largura disponível.
                                                .fillMaxWidth()
                                                // A linha inteira é clicável, padrão da Aula 07.
                                                .clickable {
                                                    // Cria uma lista nova para o Compose perceber a mudança.
                                                    val novaLista = mutableListOf<String>()
                                                    // Copia tudo o que já estava marcado.
                                                    marcados.forEach { item ->
                                                        // Copia este ingrediente para a lista nova.
                                                        novaLista.add(item)
                                                    }
                                                    // Se já estava marcado, remove. Se não, adiciona.
                                                    // removeIf e add são os métodos da Aula 04.
                                                    if (estaMarcado) {
                                                        // removeIf apaga quem satisfaz a condição (Aula 04).
                                                        novaLista.removeIf { item -> item == ingrediente }
                                                    } else {
                                                        // add insere o ingrediente na lista nova (Aula 04).
                                                        novaLista.add(ingrediente)
                                                    }
                                                    // Troca o estado pela lista nova.
                                                    marcados = novaLista
                                                },
                                            // Alinha os filhos pelo meio na vertical (Aula 06).
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            // A caixa de marcar. O clique dela repete a mesma regra.
                                            Checkbox(
                                                // Diz se a caixa esta marcada ou nao.
                                                checked = estaMarcado,
                                                // Executado quando o usuário marca ou desmarca a caixa.
                                                onCheckedChange = {
                                                    // Cria uma lista nova a partir da atual.
                                                    val novaLista = mutableListOf<String>()
                                                    // Copia os itens já marcados.
                                                    marcados.forEach { item ->
                                                        // Copia este ingrediente para a lista nova.
                                                        novaLista.add(item)
                                                    }
                                                    // Marca ou desmarca conforme o estado atual.
                                                    if (estaMarcado) {
                                                        // removeIf apaga quem satisfaz a condição (Aula 04).
                                                        novaLista.removeIf { item -> item == ingrediente }
                                                    } else {
                                                        // add insere o ingrediente na lista nova (Aula 04).
                                                        novaLista.add(ingrediente)
                                                    }
                                                    // Atualiza o estado.
                                                    marcados = novaLista
                                                }
                                            )
                                            // Texto do ingrediente ao lado da caixinha.
                                            Text(
                                                // Texto que aparece na tela.
                                                text = ingrediente,
                                                // Tamanho da letra: 14sp.
                                                fontSize = 14.sp,
                                                // Cor de título, definida em CoresApp.kt.
                                                color = TextoTitulo
                                            )
                                        }
                                    }
                                }
                            }

                            // ---------- ABA MODO DE PREPARO ----------
                            "preparo" -> {
                                // Column com os passos empilhados.
                                Column(
                                    // Ocupa toda a largura.
                                    modifier = Modifier.fillMaxWidth(),
                                    // 10dp de distância entre um passo e outro.
                                    verticalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    // forEach percorre a lista de passos (Aula 04).
                                    // Os passos já vêm numerados dentro do próprio texto.
                                    receita.preparo.forEach { passo ->
                                        // Texto exibido neste ponto da tela.
                                        Text(
                                            // Texto que aparece na tela.
                                            text = passo,
                                            // Tamanho da letra: 14sp.
                                            fontSize = 14.sp,
                                            // Cor de título, definida em CoresApp.kt.
                                            color = TextoTitulo
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Espaço no fim para o conteúdo não colar na borda inferior ao rolar.
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

// -------------------------------------------------------------------------------------
// Pré-visualização da tela no Android Studio.
// -------------------------------------------------------------------------------------
@Preview(showBackground = true)
@Composable
fun TelaDetalhePreview() {
    // Aplica o tema do projeto para as cores saírem certas.
    MyApplicationTheme {
        // Mostra a primeira receita da lista, já marcada como favorita.
        // listaDeReceitas()[0] usa o acesso por índice mostrado na Aula 04.
        TelaDetalhe(
            // A receita escolhida na tela anterior.
            receita = listaDeReceitas()[0],
            // Diz se esta receita ja esta favoritada.
            favorito = true,
            // Callback disparado quando o usuário toca na estrela.
            onFavoritar = { },
            // O que acontece quando o usuário toca na seta.
            onVoltar = { }
        )
    }
}
