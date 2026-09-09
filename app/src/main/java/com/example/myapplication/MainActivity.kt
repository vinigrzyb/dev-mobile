// =====================================================================================
// ARQUIVO: MainActivity.kt
// PAPEL  : Ponto de entrada do app e "roteador" que decide qual das três telas aparece.
//
// POR QUE NÃO USAMOS NavHost / NavController:
//   Navegação com NavController não apareceu em nenhuma aula e exigiria adicionar a
//   biblioteca navigation-compose no build.gradle.kts. A troca de tela aqui é feita com
//   uma variável de estado e um "when", que são Aula 07 (estado) e Aula 02 (condicional).
//
// ONDE FICA O ESTADO COMPARTILHADO:
//   A lista de receitas, a receita aberta e a lista de favoritos ficam TODAS aqui.
//   As telas só recebem esses dados por parâmetro e devolvem avisos por callback (Aula 03).
//   É por isso que as três telas funcionam sozinhas no @Preview.
//
// DE ONDE VEM CADA COISA USADA AQUI:
//   - ComponentActivity / setContent / enableEdgeToEdge ... Aula 07 -> myapplication/MainActivity.kt
//   - Scaffold(modifier) { innerPadding -> } .............. Aula 07 -> myapplication/MainActivity.kt
//   - remember { mutableStateOf(...) } ................... Aula 07
//   - when .............................................. Aula 02
//   - mutableListOf / add / removeIf / contains / forEach . Aula 04
//   - Log.d ............................................. Aula 08 -> ListaLazyColumn
// =====================================================================================

// Mesmo pacote de todos os arquivos do app.
package com.example.myapplication

// Bundle: o "pacote" de estado que o Android entrega ao criar a Activity.
import android.os.Bundle
// Log: escreve mensagens no Logcat. O professor usou Log.d na Aula 08.
import android.util.Log
// ComponentActivity: a classe base da Activity usada em todas as aulas de Compose.
import androidx.activity.ComponentActivity
// setContent: liga a Activity à árvore de composables.
import androidx.activity.compose.setContent
// enableEdgeToEdge: faz o app desenhar atrás das barras do sistema (Aula 07).
import androidx.activity.enableEdgeToEdge
// fillMaxSize: ocupa toda a tela.
import androidx.compose.foundation.layout.fillMaxSize
// padding: afastamento interno, usado com o innerPadding do Scaffold.
import androidx.compose.foundation.layout.padding
// Scaffold: a estrutura base de tela do Material 3 (Aula 07).
import androidx.compose.material3.Scaffold
// Composable: marca a função como desenhável.
import androidx.compose.runtime.Composable
// getValue: para ler estado com "by".
import androidx.compose.runtime.getValue
// mutableStateOf: cria estado observável.
import androidx.compose.runtime.mutableStateOf
// remember: mantém o valor entre redesenhos.
import androidx.compose.runtime.remember
// setValue: para escrever estado com "by".
import androidx.compose.runtime.setValue
// Modifier: tamanho, espaçamento, clique.
import androidx.compose.ui.Modifier
// Preview: pré-visualização no Android Studio.
import androidx.compose.ui.tooling.preview.Preview
// Tema do projeto, gerado junto com o projeto pelo Android Studio.
import com.example.myapplication.ui.theme.MyApplicationTheme

// -------------------------------------------------------------------------------------
// A Activity: é ela que o Android abre quando o usuário toca no ícone do app.
// -------------------------------------------------------------------------------------
class MainActivity : ComponentActivity() {

    // onCreate roda uma vez, no momento em que a tela é criada.
    override fun onCreate(savedInstanceState: Bundle?) {
        // Chama o onCreate da classe pai. É obrigatório e sempre vem primeiro.
        super.onCreate(savedInstanceState)

        // Deixa o app desenhar até as bordas da tela, atrás da barra de status.
        enableEdgeToEdge()

        // setContent troca o XML tradicional pela árvore de composables.
        setContent {
            // MyApplicationTheme aplica as cores e as fontes do projeto em tudo que estiver dentro.
            MyApplicationTheme {
                // Scaffold é a moldura padrão do Material 3.
                // Ele devolve o innerPadding, que é o espaço ocupado pelas barras do sistema.
                Scaffold(
                    // Ocupa toda a tela.
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    // Repassa esse espaço para o app, para nada ficar embaixo da barra de status.
                    // É exatamente a linha que o professor escreveu na Aula 07.
                    AppReceitasDiarias(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// -------------------------------------------------------------------------------------
// AppReceitasDiarias: o roteador do app.
// -------------------------------------------------------------------------------------
// Guarda o estado que as três telas compartilham e escolhe qual delas desenhar.
@Composable
// Função que guarda o estado do app e escolhe a tela.
fun AppReceitasDiarias(modifier: Modifier = Modifier) {

    // ---------------------------------------------------------------------------------
    // ESTADO COMPARTILHADO
    // ---------------------------------------------------------------------------------

    // A lista de receitas, criada uma única vez graças ao remember.
    // É o mesmo padrão do "val alunos = remember { gerarAluno() }" da Aula 08.
    val receitas = remember { listaDeReceitas() }

    // Qual tela está aberta. Começa no login.
    // Trocar o valor desta variável é o que faz o app "navegar".
    var tela by remember { mutableStateOf("login") }

    // Qual receita o usuário abriu. Começa com a primeira da lista só para ter um valor
    // inicial válido, evitando trabalhar com nulo. O acesso por índice [0] é da Aula 04.
    var receitaAberta by remember { mutableStateOf(receitas[0]) }

    // Ids das receitas favoritadas. Começa vazia.
    // Guardamos só o id, e não a receita inteira, porque o id já identifica cada uma.
    var favoritos by remember { mutableStateOf(listOf<Int>()) }

    // ---------------------------------------------------------------------------------
    // ROTEAMENTO
    // ---------------------------------------------------------------------------------
    // when olha o valor da variável "tela" e desenha o composable correspondente.
    when (tela) {

        // ---------- TELA 1: LOGIN ----------
        "login" -> {
            // Desenha a tela 1 passando o estado e recebendo o aviso de login.
            TelaLogin(
                // Repassa o padding do Scaffold.
                modifier = modifier,
                // Quando o login termina, trocamos a tela para a lista.
                onEntrar = {
                    // Registra no Logcat para conferir o fluxo durante a apresentação.
                    Log.d("ReceitasDiarias", "Login concluído, indo para a lista")
                    // A troca de tela é só mudar o valor da variável de estado.
                    tela = "lista"
                }
            )
        }

        // ---------- TELA 2: LISTA ----------
        "lista" -> {
            // Desenha a tela 2 passando a lista e os favoritos.
            TelaLista(
                // Repassa o padding do Scaffold.
                modifier = modifier,
                // Entrega a lista completa; quem filtra é a própria tela.
                receitas = receitas,
                // Entrega os favoritos para a tela desenhar a estrelinha.
                favoritos = favoritos,
                // Quando o usuário toca num card, a tela devolve qual receita foi.
                onAbrir = { receitaTocada ->
                    // Registra qual receita foi aberta.
                    Log.d("ReceitasDiarias", "Abrindo receita: ${receitaTocada.nome}")
                    // Guarda a receita escolhida para a tela de detalhe usar.
                    receitaAberta = receitaTocada
                    // E troca para a tela de detalhe.
                    tela = "detalhe"
                },
                // A seta da barra volta para o login.
                onSair = {
                    // Escreve no Logcat para acompanhar o fluxo na apresentação (Aula 08).
                    Log.d("ReceitasDiarias", "Saindo para o login")
                    // Muda a variável de estado, e o when redesenha a tela de login.
                    tela = "login"
                }
            )
        }

        // ---------- TELA 3: DETALHE ----------
        "detalhe" -> {
            // Desenha a tela 3 passando a receita escolhida.
            TelaDetalhe(
                // Repassa o padding do Scaffold.
                modifier = modifier,
                // A receita que foi escolhida na lista.
                receita = receitaAberta,
                // Calcula na hora se esta receita está entre os favoritos.
                // contains é a busca em lista da Aula 04.
                favorito = favoritos.contains(receitaAberta.id),
                // Quando o usuário clica na estrela, quem muda a lista é aqui, e não a tela.
                onFavoritar = {
                    // Cria uma lista nova, porque o Compose só percebe a mudança
                    // quando o objeto guardado no estado é substituído por outro.
                    val novaLista = mutableListOf<Int>()

                    // Copia todos os ids que já estavam favoritados. forEach é da Aula 04.
                    favoritos.forEach { id ->
                        // Copia este id para a lista nova.
                        novaLista.add(id)
                    }

                    // Se a receita já era favorita, tira da lista. Se não era, coloca.
                    if (novaLista.contains(receitaAberta.id)) {
                        // removeIf apaga quem satisfaz a condição. É o método da Aula 04.
                        novaLista.removeIf { id -> id == receitaAberta.id }
                        // Escreve no Logcat para acompanhar o fluxo na apresentação (Aula 08).
                        Log.d("ReceitasDiarias", "Desfavoritou: ${receitaAberta.nome}")
                    } else {
                        // add insere o novo id no fim da lista.
                        novaLista.add(receitaAberta.id)
                        // Escreve no Logcat para acompanhar o fluxo na apresentação (Aula 08).
                        Log.d("ReceitasDiarias", "Favoritou: ${receitaAberta.nome}")
                    }

                    // Substitui o estado pela lista nova, o que redesenha a tela.
                    favoritos = novaLista
                },
                // A seta da barra volta para a lista.
                onVoltar = {
                    // Escreve no Logcat para acompanhar o fluxo na apresentação (Aula 08).
                    Log.d("ReceitasDiarias", "Voltando para a lista")
                    // Muda a variável de estado, e o when redesenha a tela de lista.
                    tela = "lista"
                }
            )
        }
    }
}

// -------------------------------------------------------------------------------------
// Pré-visualização do app inteiro, começando pela tela de login.
// -------------------------------------------------------------------------------------
@Preview(showBackground = true)
@Composable
// Função que guarda o estado do app e escolhe a tela.
fun AppReceitasDiariasPreview() {
    // Aplica o tema do projeto para as cores saírem certas.
    MyApplicationTheme {
        // Mostra o app inteiro na prévia, começando pelo login.
        AppReceitasDiarias()
    }
}
