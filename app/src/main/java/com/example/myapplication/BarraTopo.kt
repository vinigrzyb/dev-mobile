// =====================================================================================
// ARQUIVO: BarraTopo.kt
// PAPEL  : A faixa azul que aparece no topo das três telas (seta, logo, título, engrenagem).
//
// POR QUE NÃO USAMOS TopAppBar:
//   TopAppBar não apareceu em nenhuma aula. A barra aqui é montada com Surface + Row,
//   exatamente como o professor montou blocos coloridos na Aula 06 (função Home e
//   função CriarElemento, que são Surface colorido com Row/Box e Icon dentro).
//
// DE ONDE VEM CADA COISA USADA AQUI:
//   - @Composable ............................. Aula 06
//   - Surface(modifier, color) ................ Aula 06 -> app/MainActivity.kt
//   - Row(verticalAlignment, horizontalArrangement) ... Aula 06 -> função Home
//   - Box(contentAlignment) ................... Aula 06 -> função Home
//   - Icon(painter = painterResource(...), contentDescription, tint, modifier) ... Aula 06
//   - Card(modifier, shape = RoundedCornerShape(...)) ... Aula 08 -> ListaLazyColumn
//   - Modifier.clickable { } .................. Aula 07 -> Formulario (campo de data)
//   - Spacer(Modifier.width/height) ........... Aula 07 -> FormularioAula
//   - parâmetro com valor padrão .............. Aula 06 -> fun Home(name: String = "Visitante")
//   - callback () -> Unit ..................... Aula 03 (funções como parâmetro) e o
//                                               próprio onClick do Button da Aula 07
// =====================================================================================

// Mesmo pacote das outras telas.
package com.example.myapplication

// Arrangement: como distribuir os filhos dentro de Row/Column (SpaceBetween, spacedBy...).
import androidx.compose.foundation.layout.Arrangement
// Box: caixa que empilha e permite centralizar o conteúdo dentro dela.
import androidx.compose.foundation.layout.Box
// Row: coloca os filhos lado a lado, na horizontal.
import androidx.compose.foundation.layout.Row
// Spacer: espaço vazio, usado para equilibrar a barra quando não tem seta de voltar.
import androidx.compose.foundation.layout.Spacer
// fillMaxSize: ocupa toda a largura E altura do pai.
import androidx.compose.foundation.layout.fillMaxSize
// fillMaxWidth: ocupa toda a largura disponível.
import androidx.compose.foundation.layout.fillMaxWidth
// height: define a altura em dp.
import androidx.compose.foundation.layout.height
// padding: afastamento interno.
import androidx.compose.foundation.layout.padding
// size: define largura e altura ao mesmo tempo.
import androidx.compose.foundation.layout.size
// width: define só a largura.
import androidx.compose.foundation.layout.width
// clickable: deixa qualquer elemento clicável (o professor usou no campo de data da Aula 07).
import androidx.compose.foundation.clickable
// RoundedCornerShape: canto arredondado. Usado na Aula 08 dentro do Card.
import androidx.compose.foundation.shape.RoundedCornerShape
// Card: o container com sombra da Aula 08. Aqui vira o círculo do logo.
import androidx.compose.material3.Card
// Icon: desenha um vetor de res/drawable. Padrão da Aula 06 e da Aula 07.
import androidx.compose.material3.Icon
// Surface: bloco colorido. É o que dá a cor azul da barra.
import androidx.compose.material3.Surface
// Text: escreve texto na tela.
import androidx.compose.material3.Text
// Composable: marca a função como "desenhável" pelo Compose.
import androidx.compose.runtime.Composable
// Alignment: alinhamentos (Center, CenterVertically...).
import androidx.compose.ui.Alignment
// Modifier: o "encanamento" de tamanho, espaçamento e clique de todo composable.
import androidx.compose.ui.Modifier
// painterResource: carrega o desenho de res/drawable pelo R.drawable.
import androidx.compose.ui.res.painterResource
// FontWeight: peso da fonte (Bold, Normal...).
import androidx.compose.ui.text.font.FontWeight
// Preview: permite ver o composable no painel de pré-visualização do Android Studio.
import androidx.compose.ui.tooling.preview.Preview
// dp: unidade de tamanho.
import androidx.compose.ui.unit.dp
// sp: unidade de tamanho de texto.
import androidx.compose.ui.unit.sp
// Tema do projeto, o mesmo que o professor gera junto com o projeto.
import com.example.myapplication.ui.theme.MyApplicationTheme

// -------------------------------------------------------------------------------------
// BarraTopo: composable reutilizado pelas três telas.
// -------------------------------------------------------------------------------------
@Composable
fun BarraTopo(
    // Texto que aparece no meio da barra.
    titulo: String,
    // Liga ou desliga a seta de voltar. Tem valor padrão true, igual ao "name" da Aula 06.
    mostrarVoltar: Boolean = true,
    // O que fazer quando clicar na seta. É um callback (Aula 03): quem chama decide a ação.
    // O valor padrão { } é uma função vazia, para o parâmetro ser opcional.
    onVoltar: () -> Unit = { }
) {
    // Surface é o retângulo colorido de fundo. A cor vem do nosso CoresApp.kt.
    Surface(
        // Ocupa toda a largura da tela e tem 60dp de altura fixa.
        modifier = Modifier
            // Ocupa toda a largura disponível.
            .fillMaxWidth()
            // Altura fixa de 60dp.
            .height(60.dp),
        // Azul claro da barra.
        color = AzulBarra
    ) {
        // Row organiza os três blocos da barra lado a lado.
        Row(
            // Preenche toda a Surface e afasta o conteúdo 12dp das bordas.
            modifier = Modifier
                // Ocupa toda a largura e toda a altura disponíveis.
                .fillMaxSize()
                // Afasta o conteúdo 12dp de todas as bordas.
                .padding(12.dp),
            // Centraliza tudo verticalmente dentro da altura de 60dp.
            verticalAlignment = Alignment.CenterVertically,
            // Empurra o primeiro item para a esquerda e o último para a direita.
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            // ---------- BLOCO 1: seta de voltar (ou um espaço vazio do mesmo tamanho) ----------
            // "if" simples da Aula 01/02, usado aqui para escolher o que desenhar.
            if (mostrarVoltar) {
                // Ícone da seta, carregado de res/drawable/ic_voltar.xml.
                Icon(
                    // painterResource lê o arquivo XML do desenho.
                    painter = painterResource(R.drawable.ic_voltar),
                    // Texto de acessibilidade, lido por leitores de tela.
                    contentDescription = "Voltar",
                    // Pinta o desenho com a cor do título.
                    tint = TextoTitulo,
                    // 26dp de tamanho e clique que dispara o callback recebido.
                    modifier = Modifier
                        // Largura e altura de 26dp.
                        .size(26.dp)
                        // Torna este elemento clicável (Aula 07).
                        .clickable { onVoltar() }
                )
            } else {
                // Quando não tem seta, um espaço vazio do mesmo tamanho mantém o título centralizado.
                Spacer(modifier = Modifier.width(26.dp))
            }

            // ---------- BLOCO 2: logo redondo + título ----------
            Row(
                // Alinha o logo e o texto pelo meio na vertical.
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Card com canto de 16dp vira um círculo, porque o Card tem 32dp de lado.
                Card(
                    // Quadrado de 32dp de lado.
                    modifier = Modifier.size(32.dp),
                    // Cantos arredondados em 16dp (Aula 08).
                    shape = RoundedCornerShape(16.dp)
                ) {
                    // Box centraliza a letra do logo dentro do círculo.
                    Box(
                        // Ocupa toda a tela.
                        modifier = Modifier.fillMaxSize(),
                        // Centraliza o conteúdo dentro da Box (Aula 06).
                        contentAlignment = Alignment.Center
                    ) {
                        // A letra "R" de Receitas.
                        Text(
                            // Texto que aparece na tela.
                            text = "R",
                            // Azul escuro da marca, definido em CoresApp.kt.
                            color = AzulEscuro,
                            // Tamanho da letra: 18sp.
                            fontSize = 18.sp,
                            // Deixa o texto em negrito.
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Espacinho entre o logo e o texto do título.
                Spacer(modifier = Modifier.width(10.dp))

                // O título propriamente dito.
                Text(
                    // Texto que aparece na tela.
                    text = titulo,
                    // Cor de título, definida em CoresApp.kt.
                    color = TextoTitulo,
                    // Tamanho da letra: 18sp.
                    fontSize = 18.sp,
                    // Deixa o texto em negrito.
                    fontWeight = FontWeight.Bold
                )
            }

            // ---------- BLOCO 3: engrenagem ----------
            // Ícone só decorativo, para a barra ficar igual ao layout aprovado.
            // Não tem clickable de propósito: nesta entrega não existe tela de configurações.
            Icon(
                // Carrega o desenho de res/drawable pelo R.drawable (Aula 06/07).
                painter = painterResource(R.drawable.ic_engrenagem),
                // Descrição usada pelos leitores de tela.
                contentDescription = "Configurações",
                // Cor com que o desenho é pintado (Aula 06).
                tint = TextoTitulo,
                // Quadrado de 26dp de lado.
                modifier = Modifier.size(26.dp)
            )
        }
    }
}

// -------------------------------------------------------------------------------------
// Pré-visualização no Android Studio, no mesmo formato que o professor usa em toda aula.
// -------------------------------------------------------------------------------------
@Preview(showBackground = true)
@Composable
fun BarraTopoPreview() {
    // Envolve no tema do app para as cores e fontes saírem certas na prévia.
    MyApplicationTheme {
        // Barra do topo definida em BarraTopo.kt.
        BarraTopo(titulo = "Receitas Diárias")
    }
}
