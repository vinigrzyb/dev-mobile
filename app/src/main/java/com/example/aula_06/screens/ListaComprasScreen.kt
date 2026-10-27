package com.example.aula_06.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.aula_06.ui.theme.*
import com.example.aula_06.ui.viewmodel.ListaComprasViewModel

@Composable
fun ListaComprasScreen(viewModel: ListaComprasViewModel, aoNovo: () -> Unit, aoEditar: (Long) -> Unit) {
    var excluirId by rememberSaveable { mutableStateOf<Long?>(null) }
    LazyColumn(modifier = Modifier.fillMaxSize().background(AzulFundo).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Text("Minha lista de compras", color = AzulEscuro, style = MaterialTheme.typography.titleLarge)
            Row {
                Button(onClick = aoNovo, enabled = !viewModel.carregando) { Text("Adicionar item") }
                TextButton(onClick = viewModel::listar, enabled = !viewModel.carregando) { Text("Atualizar") }
            }
            viewModel.mensagem?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            if (viewModel.carregando) Text("Carregando...")
            if (!viewModel.carregando && viewModel.itens.isEmpty()) Text("Nenhum item na lista.")
        }
        items(viewModel.itens, key = { it.id }) { item ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text(item.nome, color = AzulEscuro, style = MaterialTheme.typography.titleMedium)
                    Text(item.quantidade)
                    Row {
                        TextButton(onClick = { aoEditar(item.id) }, enabled = !viewModel.carregando) { Text("Editar") }
                        TextButton(onClick = { excluirId = item.id }, enabled = !viewModel.carregando) { Text("Excluir") }
                    }
                }
            }
        }
    }
    val selecionado = viewModel.itens.firstOrNull { it.id == excluirId }
    if (selecionado != null) {
        AlertDialog(onDismissRequest = { excluirId = null }, title = { Text("Excluir item?") },
            text = { Text("Excluir ${selecionado.nome}?") },
            confirmButton = { TextButton(onClick = { excluirId = null; viewModel.excluir(selecionado.id) },
                enabled = !viewModel.carregando) { Text("Confirmar exclusao") } },
            dismissButton = { TextButton(onClick = { excluirId = null }) { Text("Cancelar") } })
    }
}
