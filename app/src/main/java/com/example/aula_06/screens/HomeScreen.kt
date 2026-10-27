package com.example.aula_06.screens

import androidx.compose.foundation.clickable
import androidx.compose.material3.TextButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aula_06.model.Receita
import com.example.aula_06.ui.viewmodel.ReceitaViewModel
import com.example.aula_06.R
import com.example.aula_06.ui.theme.*

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: ReceitaViewModel,
    aoClicarReceita: (Long) -> Unit,
    aoNovaReceita: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AzulFundo)
    ) {
        // Topo com logo e titulo
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(AzulTopo)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(AzulEscuro),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "R", color = Color.White, fontWeight = FontWeight.Medium)
            }
            Text(
                text = "  Receitas Diarias",
                color = AzulEscuro,
                fontWeight = FontWeight.Medium
            )
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            TextButton(onClick = aoNovaReceita) { Text("Nova receita") }
            TextButton(onClick = viewModel::listar, enabled = !viewModel.carregando) { Text("Atualizar") }
        }
        viewModel.mensagem?.let { Text(it, modifier = Modifier.padding(12.dp)) }
        if (viewModel.carregando) Text("Carregando...", modifier = Modifier.padding(12.dp))
        if (!viewModel.carregando && viewModel.receitas.isEmpty()) Text("Nenhuma receita cadastrada.", modifier = Modifier.padding(12.dp))
        // Grid de receitas (2 colunas)
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(12.dp)
        ) {
            items(viewModel.receitas, key = { it.id }) { receita ->
                ReceitaCard(receita, Modifier.clickable { aoClicarReceita(receita.id) })
            }
        }
    }
}

@Composable
fun ReceitaCard(receita: Receita, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Branco)
    ) {
        imagemReceita(receita.imagem)?.let { imagem ->
        Image(
            painter = painterResource(id = imagem),
            contentDescription = receita.nome,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        )
        }
        Text(
            text = receita.nome,
            modifier = Modifier.padding(8.dp)
        )
    }
}

internal fun imagemReceita(chave: String?): Int? = when (chave) {
    "espaguete" -> R.drawable.espaguete
    "tarta_morango" -> R.drawable.tarta_morango
    "sopa_abobora" -> R.drawable.sopa_abobora
    "salada_verao" -> R.drawable.salada_verao
    "mini_hamburgueres" -> R.drawable.mini_hamburgueres
    "suco_laranja" -> R.drawable.suco_laranja
    else -> null
}
