// =====================================================================================
// ARQUIVO: Receita.kt
// PAPEL  : Modelo de dados do app + a "fonte de dados" (lista fixa de receitas).
//
// DE ONDE VEM CADA COISA USADA AQUI:
//   - data class ................ Aula 05 (Objetos) -> aulaObj/Cliente.kt
//   - List<T> / listOf() ........ Aula 04 (Coleções) -> aula03/Main.kt
//   - função que devolve List ... Aula 08 -> myapplication(1)/MainActivity.kt (gerarAluno)
// =====================================================================================

// Declara o pacote do arquivo. Tem que ser IGUAL ao das telas para elas se enxergarem.
// O professor usa esse mesmo pacote em todos os projetos Android das aulas.
package com.example.myapplication

// -------------------------------------------------------------------------------------
// MODELO DE DADOS
// -------------------------------------------------------------------------------------
// "data class" é a classe de dados da Aula 05. O professor mostrou em aulaObj/Cliente.kt
// que ela já vem de graça com toString(), equals(), copy() e desestruturação.
// Na Aula 08 ele usou exatamente esse recurso para o Aluno (data class Aluno(id, nome)).
// Aqui é a mesma ideia, só que com mais campos porque uma receita tem mais informação.
data class Receita(
    // Identificador único da receita. É "val" porque nunca muda depois de criada.
    // Serve para o LazyColumn saber quem é quem (parâmetro key = { it.id } na Aula 08).
    val id: Int,

    // Nome que aparece no card da lista e no título da tela de detalhe.
    val nome: String,

    // "Salgadas" ou "Doces". É o campo que a tela de lista usa para filtrar por categoria.
    val categoria: String,

    // Tempo de preparo já formatado como texto ("45 min"), para só jogar dentro de um Text.
    val tempo: String,

    // Quantas pessoas a receita serve, também já como texto ("4 pessoas").
    val porcoes: String,

    // "Fácil", "Média" ou "Difícil". Texto puro, sem regra de negócio.
    val dificuldade: String,

    // Identificador do desenho que representa o prato (ex.: R.drawable.ic_prato_massa).
    // R.drawable.alguma_coisa é um número inteiro gerado pelo Android, por isso o tipo Int.
    // O professor usou esse mesmo R.drawable dentro de painterResource() na Aula 06 e na 07.
    val icone: Int,

    // Cor de fundo do bloco da imagem, guardada como Long no formato 0xAARRGGBB.
    // É exatamente o formato que o professor usa em ui/theme/Color.kt: Color(0xFFD0BCFF).
    // Guardamos o Long aqui e transformamos em Color na hora de desenhar.
    val cor: Long,

    // Lista de ingredientes. List<String> é coleção da Aula 04.
    val ingredientes: List<String>,

    // Lista com o passo a passo. Cada item já vem numerado ("1. ...") para não precisar
    // de nenhuma função de índice que não tenha aparecido em aula.
    val preparo: List<String>
)

// -------------------------------------------------------------------------------------
// FONTE DE DADOS
// -------------------------------------------------------------------------------------
// Função normal (Aula 03) que devolve uma List<Receita> (Aula 04).
// É o mesmo padrão do "fun gerarAluno(): List<Aluno>" que o professor escreveu na Aula 08:
// uma função solta no arquivo, sem classe, que só monta e devolve a lista pronta.
fun listaDeReceitas(): List<Receita> {
    // "return listOf(...)" devolve uma lista imutável já preenchida.
    // listOf() é a criação de lista mostrada na Aula 04.
    return listOf(

        // ----------------------------- RECEITA 1 -----------------------------
        Receita(
            // id 1: primeiro item da lista.
            id = 1,
            // Nome exibido na lista e no detalhe.
            nome = "Lasanha Bolonhesa Clássica",
            // Entra no filtro "Salgadas" da tela de lista.
            categoria = "Salgadas",
            // Texto do bloco do relógio na tela de detalhe.
            tempo = "45 min",
            // Texto do bloco das pessoas na tela de detalhe.
            porcoes = "4 pessoas",
            // Texto do bloco das barrinhas na tela de detalhe.
            dificuldade = "Média",
            // Desenho do prato: massa. O arquivo está em res/drawable/ic_prato_massa.xml.
            icone = R.drawable.ic_prato_massa,
            // Vermelho tomate para o fundo do bloco da imagem.
            cor = 0xFFB5482F,
            // Lista de ingredientes; cada String vira uma linha com Checkbox no detalhe.
            ingredientes = listOf(
                "500 g de massa de lasanha",
                "500 g de carne moída",
                "400 g de queijo mussarela",
                "200 g de queijo parmesão ralado",
                "800 ml de molho de tomate",
                "1 cebola picada",
                "2 dentes de alho picados",
                "Sal e orégano a gosto"
            ),
            // Passo a passo já numerado dentro do próprio texto.
            preparo = listOf(
                "1. Refogue a cebola e o alho na panela até dourarem.",
                "2. Junte a carne moída e cozinhe até perder a cor rosada.",
                "3. Acrescente o molho de tomate, o sal e o orégano.",
                "4. Deixe o molho apurar por 15 minutos em fogo baixo.",
                "5. Monte camadas alternando massa, molho e queijo.",
                "6. Cubra com parmesão e leve ao forno a 200 graus por 30 minutos."
            )
        ),

        // ----------------------------- RECEITA 2 -----------------------------
        Receita(
            // Identificador único desta receita.
            id = 2,
            // Nome exibido na lista e no título do detalhe.
            nome = "Espaguete à Bolonhesa",
            // Categoria usada pelo filtro da tela de lista.
            categoria = "Salgadas",
            // Tempo de preparo, já em formato de texto.
            tempo = "30 min",
            // Quantas pessoas a receita serve.
            porcoes = "3 pessoas",
            // Nível de dificuldade mostrado na tela de detalhe.
            dificuldade = "Fácil",
            // Reaproveita o mesmo desenho de massa da receita 1.
            icone = R.drawable.ic_prato_massa,
            // Laranja queimado.
            cor = 0xFFC85A2B,
            // Cada String desta lista vira uma linha com Checkbox no detalhe.
            ingredientes = listOf(
                "400 g de espaguete",
                "400 g de carne moída",
                "500 ml de molho de tomate",
                "1 cebola picada",
                "Queijo parmesão ralado a gosto",
                "Sal e pimenta a gosto"
            ),
            // Passos já numerados dentro do próprio texto.
            preparo = listOf(
                "1. Cozinhe o espaguete em água com sal até ficar al dente.",
                "2. Refogue a cebola e junte a carne moída.",
                "3. Adicione o molho de tomate e tempere.",
                "4. Cozinhe o molho por 10 minutos.",
                "5. Misture o molho com a massa e finalize com parmesão."
            )
        ),

        // ----------------------------- RECEITA 3 -----------------------------
        Receita(
            // Identificador único desta receita.
            id = 3,
            // Nome exibido na lista e no título do detalhe.
            nome = "Sopa de Abóbora Cremosa",
            // Categoria usada pelo filtro da tela de lista.
            categoria = "Salgadas",
            // Tempo de preparo, já em formato de texto.
            tempo = "40 min",
            // Quantas pessoas a receita serve.
            porcoes = "4 pessoas",
            // Nível de dificuldade mostrado na tela de detalhe.
            dificuldade = "Fácil",
            // Desenho da tigela de sopa.
            icone = R.drawable.ic_prato_sopa,
            // Laranja abóbora.
            cor = 0xFFE8892B,
            // Cada String desta lista vira uma linha com Checkbox no detalhe.
            ingredientes = listOf(
                "1 kg de abóbora cabotiá",
                "1 cebola picada",
                "2 dentes de alho",
                "1 litro de caldo de legumes",
                "200 ml de creme de leite",
                "Sal, pimenta e noz-moscada a gosto"
            ),
            // Passos já numerados dentro do próprio texto.
            preparo = listOf(
                "1. Descasque e corte a abóbora em cubos.",
                "2. Refogue a cebola e o alho na panela.",
                "3. Junte a abóbora e o caldo de legumes.",
                "4. Cozinhe por 25 minutos até a abóbora desmanchar.",
                "5. Bata tudo no liquidificador até virar creme.",
                "6. Volte para a panela, junte o creme de leite e tempere."
            )
        ),

        // ----------------------------- RECEITA 4 -----------------------------
        Receita(
            // Identificador único desta receita.
            id = 4,
            // Nome exibido na lista e no título do detalhe.
            nome = "Salada de Verão",
            // Categoria usada pelo filtro da tela de lista.
            categoria = "Salgadas",
            // Tempo de preparo, já em formato de texto.
            tempo = "15 min",
            // Quantas pessoas a receita serve.
            porcoes = "2 pessoas",
            // Nível de dificuldade mostrado na tela de detalhe.
            dificuldade = "Fácil",
            // Desenho da folha.
            icone = R.drawable.ic_prato_salada,
            // Verde folha.
            cor = 0xFF4C8B3F,
            // Cada String desta lista vira uma linha com Checkbox no detalhe.
            ingredientes = listOf(
                "1 pé de alface americana",
                "2 tomates em rodelas",
                "1 pepino em fatias",
                "1 cebola roxa em tiras",
                "1 pimentão amarelo",
                "Azeite, limão e sal a gosto"
            ),
            // Passos já numerados dentro do próprio texto.
            preparo = listOf(
                "1. Lave bem todas as folhas e legumes.",
                "2. Rasgue a alface com as mãos e coloque na tigela.",
                "3. Corte os legumes e misture com a alface.",
                "4. Tempere com azeite, limão e sal na hora de servir."
            )
        ),

        // ----------------------------- RECEITA 5 -----------------------------
        Receita(
            // Identificador único desta receita.
            id = 5,
            // Nome exibido na lista e no título do detalhe.
            nome = "Strogonoff de Frango",
            // Categoria usada pelo filtro da tela de lista.
            categoria = "Salgadas",
            // Tempo de preparo, já em formato de texto.
            tempo = "35 min",
            // Quantas pessoas a receita serve.
            porcoes = "4 pessoas",
            // Nível de dificuldade mostrado na tela de detalhe.
            dificuldade = "Média",
            // Desenho da panela.
            icone = R.drawable.ic_prato_panela,
            // Marrom claro.
            cor = 0xFF8A5A3B,
            // Cada String desta lista vira uma linha com Checkbox no detalhe.
            ingredientes = listOf(
                "600 g de peito de frango em cubos",
                "1 cebola picada",
                "2 colheres de sopa de ketchup",
                "1 colher de sopa de mostarda",
                "200 g de champignon",
                "300 ml de creme de leite",
                "Sal e pimenta a gosto"
            ),
            // Passos já numerados dentro do próprio texto.
            preparo = listOf(
                "1. Tempere o frango com sal e pimenta.",
                "2. Doure o frango na panela e reserve.",
                "3. Refogue a cebola na mesma panela.",
                "4. Volte o frango e junte ketchup, mostarda e champignon.",
                "5. Desligue o fogo e misture o creme de leite."
            )
        ),

        // ----------------------------- RECEITA 6 -----------------------------
        Receita(
            // Identificador único desta receita.
            id = 6,
            // Nome exibido na lista e no título do detalhe.
            nome = "Bolo de Cenoura",
            // Categoria usada pelo filtro da tela de lista.
            categoria = "Doces",
            // Tempo de preparo, já em formato de texto.
            tempo = "50 min",
            // Quantas pessoas a receita serve.
            porcoes = "8 pessoas",
            // Nível de dificuldade mostrado na tela de detalhe.
            dificuldade = "Média",
            // Desenho do bolo.
            icone = R.drawable.ic_prato_bolo,
            // Laranja cenoura.
            cor = 0xFFE0A852,
            // Cada String desta lista vira uma linha com Checkbox no detalhe.
            ingredientes = listOf(
                "3 cenouras médias",
                "3 ovos",
                "1 xícara de óleo",
                "2 xícaras de açúcar",
                "2 xícaras e meia de farinha de trigo",
                "1 colher de sopa de fermento em pó",
                "Chocolate em pó para a cobertura"
            ),
            // Passos já numerados dentro do próprio texto.
            preparo = listOf(
                "1. Bata no liquidificador a cenoura, os ovos e o óleo.",
                "2. Passe para uma tigela e misture o açúcar e a farinha.",
                "3. Acrescente o fermento mexendo devagar.",
                "4. Asse a 180 graus por 40 minutos.",
                "5. Faça a cobertura de chocolate e despeje sobre o bolo morno."
            )
        ),

        // ----------------------------- RECEITA 7 -----------------------------
        Receita(
            // Identificador único desta receita.
            id = 7,
            // Nome exibido na lista e no título do detalhe.
            nome = "Tarta de Morango",
            // Categoria usada pelo filtro da tela de lista.
            categoria = "Doces",
            // Tempo de preparo, já em formato de texto.
            tempo = "60 min",
            // Quantas pessoas a receita serve.
            porcoes = "6 pessoas",
            // Nível de dificuldade mostrado na tela de detalhe.
            dificuldade = "Difícil",
            // Reaproveita o desenho de bolo/doce.
            icone = R.drawable.ic_prato_bolo,
            // Vermelho morango.
            cor = 0xFFD1344B,
            // Cada String desta lista vira uma linha com Checkbox no detalhe.
            ingredientes = listOf(
                "1 massa podre pronta",
                "500 g de morangos frescos",
                "500 ml de leite",
                "3 gemas",
                "1 xícara de açúcar",
                "2 colheres de sopa de amido de milho",
                "Geleia de morango para brilho"
            ),
            // Passos já numerados dentro do próprio texto.
            preparo = listOf(
                "1. Forre a forma com a massa e asse por 20 minutos.",
                "2. Faça o creme com leite, gemas, açúcar e amido em fogo baixo.",
                "3. Espere o creme esfriar e espalhe sobre a massa assada.",
                "4. Corte os morangos ao meio e distribua por cima.",
                "5. Pincele a geleia e leve à geladeira por 2 horas."
            )
        ),

        // ----------------------------- RECEITA 8 -----------------------------
        Receita(
            // Identificador único desta receita.
            id = 8,
            // Nome exibido na lista e no título do detalhe.
            nome = "Suco de Laranja Fresco",
            // Categoria usada pelo filtro da tela de lista.
            categoria = "Doces",
            // Tempo de preparo, já em formato de texto.
            tempo = "5 min",
            // Quantas pessoas a receita serve.
            porcoes = "2 pessoas",
            // Nível de dificuldade mostrado na tela de detalhe.
            dificuldade = "Fácil",
            // Desenho do copo.
            icone = R.drawable.ic_prato_copo,
            // Amarelo laranja.
            cor = 0xFFF5A623,
            // Cada String desta lista vira uma linha com Checkbox no detalhe.
            ingredientes = listOf(
                "8 laranjas maduras",
                "Gelo a gosto",
                "Açúcar opcional"
            ),
            // Passos já numerados dentro do próprio texto.
            preparo = listOf(
                "1. Lave bem as laranjas.",
                "2. Corte ao meio e esprema uma a uma.",
                "3. Coe o suco para tirar as sementes.",
                "4. Sirva imediatamente com gelo."
            )
        )
    )
}
