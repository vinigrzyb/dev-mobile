package com.example.aula_06.screens

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
import com.example.aula_06.model.listaReceitas
import com.example.aula_06.ui.theme.*

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
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

        // Grid de receitas (2 colunas)
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(12.dp)
        ) {
            items(listaReceitas) { receita ->
                ReceitaCard(receita)
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
        Image(
            painter = painterResource(id = receita.imagem),
            contentDescription = receita.nome,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        )
        Text(
            text = receita.nome,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    Aula06Theme {
        HomeScreen()
    }
}
