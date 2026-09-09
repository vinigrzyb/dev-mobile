// =====================================================================================
// ARQUIVO: CoresApp.kt
// PAPEL  : Guardar as cores fixas do app "Receitas Diárias" num lugar só.
//
// POR QUE ESTE ARQUIVO EXISTE:
//   O arquivo ui/theme/Color.kt do projeto do professor faz exatamente isto: um monte de
//   "val NomeDaCor = Color(0xFFRRGGBB)" soltos no arquivo, sem classe nenhuma.
//   Copiamos esse mesmo padrão, só que com as cores do nosso layout, e deixamos o
//   ui/theme/ do professor intacto (nenhum arquivo de tema foi alterado).
//
// DE ONDE VEM CADA COISA USADA AQUI:
//   - Color(0xFF......) ......... Aula 06 -> ui/theme/Color.kt (val Purple80 = Color(0xFFD0BCFF))
//   - "val" solto no arquivo .... Aula 01/02 (variáveis) + o próprio Color.kt
// =====================================================================================

// Mesmo pacote das telas, para que elas usem estas cores sem precisar de import.
package com.example.myapplication

// Import do tipo Color do Compose. É o mesmo import do ui/theme/Color.kt do professor.
import androidx.compose.ui.graphics.Color

// Azul claro da barra de cima. O 0xFF do começo é a opacidade (FF = 100% opaco),
// e os 6 dígitos seguintes são o vermelho, o verde e o azul, nessa ordem.
val AzulBarra = Color(0xFFA8CDE8)

// Azul escuro da marca. Usado no botão principal e nos títulos.
val AzulEscuro = Color(0xFF1565C0)

// Azul bem claro do fundo das telas.
val FundoApp = Color(0xFFEAF4FB)

// Cinza azulado usado nos textos de título dentro dos cards.
val TextoTitulo = Color(0xFF44637A)

// Cinza médio usado nos textos secundários (tempo, porções, legendas).
val TextoApoio = Color(0xFF6E8592)

// Branco dos cards e dos textos que ficam em cima de fundo escuro.
val BrancoCard = Color(0xFFFFFFFF)

// Cinza bem claro usado como linha divisória entre itens.
val LinhaDivisoria = Color(0xFFDCE7EF)
