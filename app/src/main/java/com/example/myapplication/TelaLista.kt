// =====================================================================================
// ARQUIVO: TelaLista.kt
// TELA 2 : Busca + categorias + lista de receitas
//
// O QUE ESTA TELA PROVA NA DEFESA:
//   - LazyColumn com items(items = ..., key = { it.id }) ..... Aula 08 (ListaLazyColumn)
//   - Card com elevation = CardDefaults.cardElevation(4.dp) ... Aula 08 (ListaColumn)
//   - Percorrer uma List e montar outra com forEach + add ..... Aula 04 (Coleções)
//   - contains e uppercase para a busca ...................... Aula 04 (Coleções/Strings)
//   - OutlinedTextField com estado (campo de busca) .......... Aula 07
//   - Callbacks recebidos por parâmetro ...................... Aula 03
//
// POR QUE A BUSCA NÃO USA filter { }:
//   filter não aparece em nenhum código das aulas. O que aparece em aula03/Main.kt é
//   mutableListOf, add, forEach e contains. Então a filtragem é feita com esses quatro,
//   que é inclusive mais fácil de explicar linha a linha.
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
// LazyColumn: lista que só desenha o que está visível (Aula 08).
import androidx.compose.foundation.lazy.LazyColumn
// items: função que gera um item do LazyColumn para cada elemento da lista (Aula 08).
import androidx.compose.foundation.lazy.items
// RoundedCornerShape: canto arredondado.
import androidx.compose.foundation.shape.RoundedCornerShape
// Button: botão cheio.
import androidx.compose.material3.Button
// Card: bloco branco com sombra.
import androidx.compose.material3.Card
// CardDefaults: usado para definir a sombra do Card (Aula 08).
import androidx.compose.material3.CardDefaults
// Icon: desenha um vetor do res/drawable.
import androidx.compose.material3.Icon
// MaterialTheme: cores e tipografia do tema.
import androidx.compose.material3.MaterialTheme
// OutlinedTextField: campo de texto com borda.
import androidx.compose.material3.OutlinedTextField
// OutlinedTextFieldDefaults: cores da borda do campo.
import androidx.compose.material3.OutlinedTextFieldDefaults
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
// Color: tipo de cor. Usado para transformar o Long da receita em cor de verdade.
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
// TelaLista
// -------------------------------------------------------------------------------------
// Esta tela é "burra" de propósito: ela recebe a lista pronta e devolve avisos por callback.
// Ela não cria receita, não guarda favorito e não sabe navegar. Isso é a MainActivity.
@Composable
fun TelaLista(
    // Modifier vindo de fora, para aplicar o padding do Scaffold.
    modifier: Modifier = Modifier,
    // A lista completa de receitas, entregue pela MainActivity.
    receitas: List<Receita>,
    // Os ids das receitas favoritadas, para desenhar a estrelinha no card.
    favoritos: List<Int>,
    // Avisa a MainActivity que o usuário tocou numa receita, mandando qual foi.
    // É um callback com parâmetro, igual ao "f: (nome: String) -> Unit" da Aula 03.
    onAbrir: (Receita) -> Unit,
    // Avisa que o usuário quer sair e voltar para o login.
    onSair: () -> Unit
) {
    // ---------------------------------------------------------------------------------
    // ESTADOS DA TELA
    // ---------------------------------------------------------------------------------

    // Texto digitado na busca. Começa vazio.
    var busca by remember { mutableStateOf("") }

    // Categoria selecionada. Começa em "Todas", que não filtra nada.
    var categoria by remember { mutableStateOf("Todas") }

    // ---------------------------------------------------------------------------------
    // FILTRAGEM (Aula 04 - Coleções)
    // ---------------------------------------------------------------------------------
    // Cria uma lista vazia que pode receber itens. mutableListOf é da Aula 04.
    val encontradas = mutableListOf<Receita>()

    // Percorre todas as receitas uma por uma. forEach é da Aula 04.
    receitas.forEach { receita ->
        // Deixa os dois textos em maiúsculo para a busca não diferenciar maiúscula de minúscula.
        // uppercase() e contains() são os dois da Aula 04.
        // Detalhe: quando a busca está vazia, contains("") devolve true, então tudo passa.
        val casaComBusca = receita.nome.uppercase().contains(busca.uppercase())

        // A categoria bate se o filtro está em "Todas" ou se é exatamente a mesma categoria.
        val casaComCategoria = categoria == "Todas" || receita.categoria == categoria

        // Só entra na lista final quem passa nas duas condições.
        if (casaComBusca && casaComCategoria) {
            // add é o método de inserir numa MutableList, mostrado na Aula 04.
            encontradas.add(receita)
        }
    }

    // Cores das bordas do campo de busca, no mesmo formato da Aula 07.
    val coresInputs = OutlinedTextFieldDefaults.colors(
        // Cor da borda quando o campo está selecionado (Aula 07).
        focusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
        // Cor da borda quando o campo está parado (Aula 07).
        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
        // Cor da borda quando o campo está desabilitado (Aula 07).
        disabledBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
    )

    // ---------------------------------------------------------------------------------
    // DESENHO DA TELA
    // ---------------------------------------------------------------------------------
    // Fundo azul claro cobrindo a tela inteira.
    Surface(
        // Usa o Modifier recebido de fora e ocupa a tela inteira.
        modifier = modifier.fillMaxSize(),
        // Fundo azul claro do app, definido em CoresApp.kt.
        color = FundoApp
    ) {
        // Column empilha barra, busca, categorias e a lista.
        // Aqui NÃO usamos verticalScroll: quem rola é a LazyColumn lá embaixo.
        Column(
            // Ocupa toda a tela.
            modifier = Modifier.fillMaxSize()
        ) {

            // Barra do topo. A seta volta para a tela de login.
            BarraTopo(
                // Texto que aparece no meio da barra.
                titulo = "Receitas Diárias",
                // Liga a seta de voltar.
                mostrarVoltar = true,
                // O que acontece quando o usuário toca na seta.
                onVoltar = { onSair() }
            )

            // ------------------- CAMPO DE BUSCA -------------------
            OutlinedTextField(
                // Texto atual da busca.
                value = busca,
                // A cada tecla digitada o estado muda e a lista é refiltrada sozinha.
                onValueChange = { busca = it },
                // Rótulo do campo. Usamos label (e não placeholder) porque label é o
                // parâmetro que o professor usou em todos os campos da Aula 07.
                label = { Text("Buscar receitas...") },
                // Ocupa a largura toda com 16dp de margem.
                modifier = Modifier
                    // Ocupa toda a largura disponível.
                    .fillMaxWidth()
                    // Afasta o conteúdo 16dp de todas as bordas.
                    .padding(16.dp),
                // Lupa na esquerda do campo.
                leadingIcon = {
                    // Ícone carregado de res/drawable (Aula 06/07).
                    Icon(
                        // Carrega o desenho de res/drawable pelo R.drawable (Aula 06/07).
                        painter = painterResource(R.drawable.ic_busca),
                        // Descrição usada pelos leitores de tela.
                        contentDescription = "Buscar",
                        // Quadrado de 24dp de lado.
                        modifier = Modifier.size(24.dp)
                    )
                },
                // Cores definidas acima.
                colors = coresInputs
            )

            // ------------------- TÍTULO DAS CATEGORIAS -------------------
            Text(
                // Texto que aparece na tela.
                text = "Categorias",
                // Afasta 16dp do lado esquerdo.
                modifier = Modifier.padding(start = 16.dp),
                // Deixa o texto em negrito.
                fontWeight = FontWeight.Bold,
                // Tamanho da letra: 16sp.
                fontSize = 16.sp,
                // Cor de título, definida em CoresApp.kt.
                color = TextoTitulo
            )

            // ------------------- BOTÕES DE CATEGORIA -------------------
            // A categoria ativa vira Button cheio; as outras ficam como TextButton.
            Row(
                // Começa a montar o Modifier deste elemento.
                modifier = Modifier
                    // Ocupa toda a largura disponível.
                    .fillMaxWidth()
                    // Afasta o conteúdo 8dp de todas as bordas.
                    .padding(8.dp),
                // Deixa 8dp de distância entre um filho e outro (Aula 07).
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                // Alinha os filhos pelo meio na vertical (Aula 06).
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Categoria "Todas".
                if (categoria == "Todas") {
                    // Botão cheio, usado na opção que está ativa (Aula 07).
                    Button(
                        // Ação executada ao tocar no botão.
                        onClick = { categoria = "Todas" },
                        // Estica para ocupar todo o espaco que sobrar.
                        modifier = Modifier.weight(1f)
                    ) {
                        // Texto exibido: "Todas".
                        Text(text = "Todas")
                    }
                } else {
                    // Botão só de texto, usado na opção inativa (Aula 07).
                    TextButton(
                        // Ação executada ao tocar no botão.
                        onClick = { categoria = "Todas" },
                        // Estica para ocupar todo o espaco que sobrar.
                        modifier = Modifier.weight(1f)
                    ) {
                        // Texto exibido: "Todas".
                        Text(text = "Todas")
                    }
                }

                // Categoria "Salgadas".
                if (categoria == "Salgadas") {
                    // Botão cheio, usado na opção que está ativa (Aula 07).
                    Button(
                        // Ação executada ao tocar no botão.
                        onClick = { categoria = "Salgadas" },
                        // Estica para ocupar todo o espaco que sobrar.
                        modifier = Modifier.weight(1f)
                    ) {
                        // Texto exibido: "Salgadas".
                        Text(text = "Salgadas")
                    }
                } else {
                    // Botão só de texto, usado na opção inativa (Aula 07).
                    TextButton(
                        // Ação executada ao tocar no botão.
                        onClick = { categoria = "Salgadas" },
                        // Estica para ocupar todo o espaco que sobrar.
                        modifier = Modifier.weight(1f)
                    ) {
                        // Texto exibido: "Salgadas".
                        Text(text = "Salgadas")
                    }
                }

                // Categoria "Doces".
                if (categoria == "Doces") {
                    // Botão cheio, usado na opção que está ativa (Aula 07).
                    Button(
                        // Ação executada ao tocar no botão.
                        onClick = { categoria = "Doces" },
                        // Estica para ocupar todo o espaco que sobrar.
                        modifier = Modifier.weight(1f)
                    ) {
                        // Texto exibido: "Doces".
                        Text(text = "Doces")
                    }
                } else {
                    // Botão só de texto, usado na opção inativa (Aula 07).
                    TextButton(
                        // Ação executada ao tocar no botão.
                        onClick = { categoria = "Doces" },
                        // Estica para ocupar todo o espaco que sobrar.
                        modifier = Modifier.weight(1f)
                    ) {
                        // Texto exibido: "Doces".
                        Text(text = "Doces")
                    }
                }
            }

            // ------------------- CONTADOR DE RESULTADOS -------------------
            // size é o tamanho da lista, mostrado na Aula 04.
            Text(
                // Texto que aparece na tela.
                text = "${encontradas.size} receitas encontradas",
                // Afasta 16dp do lado esquerdo.
                modifier = Modifier.padding(start = 16.dp),
                // Tamanho da letra: 12sp.
                fontSize = 12.sp,
                // Cor de texto secundário, definida em CoresApp.kt.
                color = TextoApoio
            )

            // Espacinho antes da lista começar.
            Spacer(modifier = Modifier.height(8.dp))

            // ------------------- LISTA VAZIA -------------------
            // Quando nenhuma receita passa no filtro, mostra um aviso no lugar da lista.
            if (encontradas.size == 0) {
                // Box centraliza o aviso no meio do espaço que sobra.
                Box(
                    // Começa a montar o Modifier deste elemento.
                    modifier = Modifier
                        // Ocupa toda a largura e toda a altura disponíveis.
                        .fillMaxSize()
                        // Afasta o conteúdo 24dp de todas as bordas.
                        .padding(24.dp),
                    // Centraliza o conteúdo dentro da Box (Aula 06).
                    contentAlignment = Alignment.Center
                ) {
                    // Texto exibido neste ponto da tela.
                    Text(
                        // Texto que aparece na tela.
                        text = "Nenhuma receita encontrada para essa busca.",
                        // Cor de texto secundário, definida em CoresApp.kt.
                        color = TextoApoio,
                        // Tamanho da letra: 14sp.
                        fontSize = 14.sp
                    )
                }
            } else {
                // ------------------- LISTA DE RECEITAS -------------------
                // LazyColumn desenha só os cards visíveis na tela, exatamente como na Aula 08.
                // weight(1f) faz a lista ocupar todo o espaço que sobrou embaixo do cabeçalho.
                LazyColumn(
                    // Começa a montar o Modifier deste elemento.
                    modifier = Modifier
                        // Ocupa toda a largura disponível.
                        .fillMaxWidth()
                        // Estica para ocupar todo o espaco que sobrar.
                        .weight(1f)
                ) {
                    // items percorre a lista filtrada e gera um item para cada receita.
                    // O "key = { it.id }" é o mesmo parâmetro que o professor usou na Aula 08:
                    // ele diz ao Compose qual item é qual, evitando redesenho desnecessário.
                    items(
                        // A lista já filtrada que será percorrida.
                        items = encontradas,
                        // Diz ao Compose qual item é qual, pelo id (Aula 08).
                        key = { it.id }
                    ) { receita ->

                        // Card de uma receita. O clique avisa a MainActivity qual receita abrir.
                        Card(
                            // Começa a montar o Modifier deste elemento.
                            modifier = Modifier
                                // Ocupa toda a largura disponível.
                                .fillMaxWidth()
                                // Afasta o conteúdo 8dp de todas as bordas.
                                .padding(8.dp)
                                // Torna este elemento clicável (Aula 07).
                                .clickable { onAbrir(receita) },
                            // Sombra de 4dp, igual à que o professor usou no ListaColumn da Aula 08.
                            elevation = CardDefaults.cardElevation(4.dp),
                            // Cantos arredondados em 12dp (Aula 08).
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            // Linha interna: bloco colorido + textos + estrela.
                            Row(
                                // Começa a montar o Modifier deste elemento.
                                modifier = Modifier
                                    // Ocupa toda a largura disponível.
                                    .fillMaxWidth()
                                    // Afasta o conteúdo 12dp de todas as bordas.
                                    .padding(12.dp),
                                // Alinha os filhos pelo meio na vertical (Aula 06).
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                // Bloco colorido com o desenho do prato.
                                // É a mesma ideia da função CriarElemento da Aula 06:
                                // uma Surface colorida com um Icon branco por cima.
                                Surface(
                                    // Quadrado de 56dp de lado.
                                    modifier = Modifier.size(56.dp),
                                    // Color(Long) é o formato de ui/theme/Color.kt.
                                    color = Color(receita.cor)
                                ) {
                                    // Box centraliza o ícone dentro do quadrado colorido.
                                    Box(
                                        // Ocupa toda a tela.
                                        modifier = Modifier.fillMaxSize(),
                                        // Centraliza o conteúdo dentro da Box (Aula 06).
                                        contentAlignment = Alignment.Center
                                    ) {
                                        // Ícone carregado de res/drawable (Aula 06/07).
                                        Icon(
                                            // O desenho vem do campo icone da data class.
                                            painter = painterResource(receita.icone),
                                            // Descrição usada pelos leitores de tela.
                                            contentDescription = receita.nome,
                                            // Pinta o desenho de branco sobre o fundo colorido.
                                            tint = BrancoCard,
                                            // Quadrado de 30dp de lado.
                                            modifier = Modifier.size(30.dp)
                                        )
                                    }
                                }

                                // Espaço entre o bloco colorido e os textos.
                                Spacer(modifier = Modifier.width(12.dp))

                                // Coluna com o nome e as informações da receita.
                                // weight(1f) faz esta coluna esticar e empurrar a estrela para a direita.
                                Column(
                                    // Estica para ocupar todo o espaco que sobrar.
                                    modifier = Modifier.weight(1f)
                                ) {
                                    // Nome da receita.
                                    Text(
                                        // Texto que aparece na tela.
                                        text = receita.nome,
                                        // Deixa o texto em negrito.
                                        fontWeight = FontWeight.Bold,
                                        // Tamanho da letra: 16sp.
                                        fontSize = 16.sp,
                                        // Cor de título, definida em CoresApp.kt.
                                        color = TextoTitulo
                                    )

                                    // Espacinho entre o nome e a linha de informações.
                                    Spacer(modifier = Modifier.height(4.dp))

                                    // Linha com relógio + tempo e pessoas + porções.
                                    Row(
                                        // Alinha os filhos pelo meio na vertical (Aula 06).
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        // Ícone do relógio.
                                        Icon(
                                            // Carrega o desenho de res/drawable pelo R.drawable (Aula 06/07).
                                            painter = painterResource(R.drawable.ic_relogio),
                                            // Descrição usada pelos leitores de tela.
                                            contentDescription = "Tempo",
                                            // Cor com que o desenho é pintado (Aula 06).
                                            tint = TextoApoio,
                                            // Quadrado de 14dp de lado.
                                            modifier = Modifier.size(14.dp)
                                        )
                                        // Espaço entre o ícone e o texto.
                                        Spacer(modifier = Modifier.width(4.dp))
                                        // Tempo de preparo.
                                        Text(
                                            // Texto que aparece na tela.
                                            text = receita.tempo,
                                            // Tamanho da letra: 12sp.
                                            fontSize = 12.sp,
                                            // Cor de texto secundário, definida em CoresApp.kt.
                                            color = TextoApoio
                                        )

                                        // Espaço maior separando os dois pares.
                                        Spacer(modifier = Modifier.width(12.dp))

                                        // Ícone de pessoas.
                                        Icon(
                                            // Carrega o desenho de res/drawable pelo R.drawable (Aula 06/07).
                                            painter = painterResource(R.drawable.ic_pessoas),
                                            // Descrição usada pelos leitores de tela.
                                            contentDescription = "Porções",
                                            // Cor com que o desenho é pintado (Aula 06).
                                            tint = TextoApoio,
                                            // Quadrado de 14dp de lado.
                                            modifier = Modifier.size(14.dp)
                                        )
                                        // Espaço entre o ícone e o texto.
                                        Spacer(modifier = Modifier.width(4.dp))
                                        // Quantidade de porções.
                                        Text(
                                            // Texto que aparece na tela.
                                            text = receita.porcoes,
                                            // Tamanho da letra: 12sp.
                                            fontSize = 12.sp,
                                            // Cor de texto secundário, definida em CoresApp.kt.
                                            color = TextoApoio
                                        )
                                    }
                                }

                                // Estrela: só aparece se o id da receita estiver na lista de favoritos.
                                // contains é a busca em lista da Aula 04.
                                if (favoritos.contains(receita.id)) {
                                    // Ícone carregado de res/drawable (Aula 06/07).
                                    Icon(
                                        // Carrega o desenho de res/drawable pelo R.drawable (Aula 06/07).
                                        painter = painterResource(R.drawable.ic_estrela),
                                        // Descrição usada pelos leitores de tela.
                                        contentDescription = "Favorito",
                                        // Amarelo da estrela, escrito no mesmo formato do Color.kt.
                                        tint = Color(0xFFF5A623),
                                        // Quadrado de 20dp de lado.
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------------------------------
// Pré-visualização da tela no Android Studio.
// -------------------------------------------------------------------------------------
@Preview(showBackground = true)
@Composable
fun TelaListaPreview() {
    // Aplica o tema do projeto para as cores saírem certas.
    MyApplicationTheme {
        // Na prévia usamos a lista real e uma lista de favoritos com uma receita marcada.
        TelaLista(
            // A lista de receitas entregue pela MainActivity.
            receitas = listaDeReceitas(),
            // Os ids ja favoritados, para a tela desenhar a estrela.
            favoritos = listOf(1),
            // Callback disparado quando o usuário toca numa receita.
            onAbrir = { },
            // Callback disparado quando o usuário quer voltar ao login.
            onSair = { }
        )
    }
}
