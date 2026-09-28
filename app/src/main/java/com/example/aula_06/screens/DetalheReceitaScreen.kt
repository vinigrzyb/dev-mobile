package com.example.aula_06.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aula_06.R
import com.example.aula_06.ui.theme.*

@Composable
fun DetalheReceitaScreen(modifier: Modifier = Modifier) {

    // Controla qual aba esta selecionada (Ingredientes ou Modo de Preparo)
    var abaIngredientes by remember { mutableStateOf(true) }

    val ingredientes = listOf(
        "Massa de lasanha, carne moida, queijo",
        "Parmesao, molho de tomate, queijo mussarela"
    )

    val modoPreparo = listOf(
        "Cozinhe a massa e prepare o molho",
        "Monte as camadas e leve ao forno por 30 minutos"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AzulFundo)
            .verticalScroll(rememberScrollState())
    ) {
        // Topo com titulo
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(AzulTopo)
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Receitas Diarias", color = AzulEscuro, fontWeight = FontWeight.Medium)
        }

        Column(modifier = Modifier.padding(16.dp)) {

            // Imagem da receita
            Image(
                painter = painterResource(id = R.drawable.lasanha_bolonhesa),
                contentDescription = "Lasanha Bolonhesa Classica",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "Lasanha Bolonhesa Classica", fontWeight = FontWeight.Medium, color = AzulEscuro)

            Spacer(modifier = Modifier.height(12.dp))

            // Informacoes rapidas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoReceita(titulo = "Tempo", valor = "45 min")
                InfoReceita(titulo = "Porcoes", valor = "4 pessoas")
                InfoReceita(titulo = "Dificuldade", valor = "Media")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Abas: Ingredientes / Modo de Preparo
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { abaIngredientes = true },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Ingredientes",
                        color = if (abaIngredientes) AzulEscuro else Color.Gray,
                        fontWeight = FontWeight.Medium
                    )
                    if (abaIngredientes) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp)
                                .background(AzulEscuro)
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { abaIngredientes = false },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Modo de Preparo",
                        color = if (!abaIngredientes) AzulEscuro else Color.Gray,
                        fontWeight = FontWeight.Medium
                    )
                    if (!abaIngredientes) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp)
                                .background(AzulEscuro)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Conteudo da aba selecionada
            if (abaIngredientes) {
                ingredientes.forEach { item -> ItemComCheckbox(texto = item) }
            } else {
                modoPreparo.forEach { item -> ItemComCheckbox(texto = item) }
            }
        }
    }
}

@Composable
fun InfoReceita(titulo: String, valor: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "$titulo:", color = Color.Gray)
        Text(text = valor, color = AzulEscuro, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun ItemComCheckbox(texto: String, modifier: Modifier = Modifier) {
    var marcado by remember { mutableStateOf(false) }
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = marcado, onCheckedChange = { marcado = it })
        Text(text = texto, color = AzulEscuro)
    }
}

@Preview(showBackground = true)
@Composable
fun DetalheReceitaScreenPreview() {
    Aula06Theme {
        DetalheReceitaScreen()
    }
}
