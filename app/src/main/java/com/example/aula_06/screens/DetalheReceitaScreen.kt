package com.example.aula_06.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.TextButton
import com.example.aula_06.ui.viewmodel.ReceitaViewModel
import androidx.compose.material3.AlertDialog
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
fun DetalheReceitaScreen(viewModel: ReceitaViewModel, modifier: Modifier = Modifier,
    aoEditar: (Long) -> Unit, aoVoltar: () -> Unit) {
    val receita = viewModel.receita
    var confirmarExclusao by remember { mutableStateOf(false) }
    LaunchedEffect(viewModel.excluido) {
        if (viewModel.excluido) { viewModel.consumirResultado(); aoVoltar() }
    }
    if (!viewModel.carregado) {
        Column(modifier.padding(16.dp)) {
            Text(viewModel.mensagem ?: "Carregando...")
            TextButton(onClick = aoVoltar) { Text("Voltar") }
        }
        return
    }
    if (confirmarExclusao) {
        AlertDialog(onDismissRequest = { confirmarExclusao = false },
            title = { Text("Excluir receita?") }, text = { Text("Excluir ${receita.nome}?") },
            confirmButton = { TextButton(onClick = { confirmarExclusao = false; viewModel.excluir() },
                enabled = !viewModel.carregando) { Text("Confirmar exclusao") } },
            dismissButton = { TextButton(onClick = { confirmarExclusao = false }) { Text("Cancelar") } })
    }
    // Controla qual aba esta selecionada (Ingredientes ou Modo de Preparo)
    var abaIngredientes by remember { mutableStateOf(true) }

    val ingredientes = receita.ingredientes.lines().filter { it.isNotBlank() }
    val modoPreparo = receita.modoPreparo.lines().filter { it.isNotBlank() }

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
            TextButton(onClick = aoVoltar) { Text("Voltar") }
            Text(text = "Receitas Diarias", color = AzulEscuro, fontWeight = FontWeight.Medium)
        }

        Column(modifier = Modifier.padding(16.dp)) {

            // Imagem da receita
            imagemReceita(receita.imagem)?.let { imagem ->
            Image(
                painter = painterResource(id = imagem),
                contentDescription = receita.nome,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = receita.nome, fontWeight = FontWeight.Medium, color = AzulEscuro)

            Spacer(modifier = Modifier.height(12.dp))

            Row {
                TextButton(onClick = { aoEditar(receita.id) }, enabled = !viewModel.carregando) { Text("Editar") }
                TextButton(onClick = { confirmarExclusao = true }, enabled = !viewModel.carregando) { Text("Excluir") }
            }
            viewModel.mensagem?.let { Text(it) }
            // Informacoes rapidas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoReceita(titulo = "Tempo", valor = receita.tempo.ifBlank { "Nao informado" })
                InfoReceita(titulo = "Porcoes", valor = receita.porcoes.ifBlank { "Nao informado" })
                InfoReceita(titulo = "Dificuldade", valor = receita.dificuldade.ifBlank { "Nao informado" })
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
            if ((if (abaIngredientes) ingredientes else modoPreparo).isEmpty()) Text("Conteudo ainda nao cadastrado.")
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
    var marcado by remember(texto) { mutableStateOf(false) }
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = marcado, onCheckedChange = { marcado = it })
        Text(text = texto, color = AzulEscuro)
    }
}
