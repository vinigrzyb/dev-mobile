package com.example.aula_06.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.aula_06.ui.theme.*
import com.example.aula_06.ui.viewmodel.ListaComprasViewModel

@Composable
fun CadastroItemCompraScreen(viewModel: ListaComprasViewModel, itemId: Long, aoVoltar: () -> Unit) {
    LaunchedEffect(itemId) {
        if (!viewModel.carregado) viewModel.carregar(itemId)
    }
    LaunchedEffect(viewModel.salvo) {
        if (viewModel.salvo) { viewModel.consumirResultado(); aoVoltar() }
    }
    val item = viewModel.item
    Column(Modifier.fillMaxSize().background(AzulFundo).imePadding()
        .verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        TextButton(onClick = aoVoltar, enabled = !viewModel.carregando) { Text("Voltar") }
        Text(if (itemId == 0L) "Novo item" else "Editar item", color = AzulEscuro,
            style = MaterialTheme.typography.titleLarge)
        OutlinedTextField(value = item.nome, onValueChange = { viewModel.editar(item.copy(nome = it)) },
            label = { Text("Nome do item") }, modifier = Modifier.fillMaxWidth(),
            enabled = viewModel.carregado && !viewModel.carregando)
        OutlinedTextField(value = item.quantidade, onValueChange = { viewModel.editar(item.copy(quantidade = it)) },
            label = { Text("Quantidade") }, modifier = Modifier.fillMaxWidth(),
            enabled = viewModel.carregado && !viewModel.carregando)
        viewModel.mensagem?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        if (viewModel.carregando) Text("Aguarde...")
        Button(onClick = viewModel::salvar, enabled = viewModel.carregado && !viewModel.carregando && !viewModel.salvo,
            modifier = Modifier.fillMaxWidth()) { Text("Salvar item") }
    }
}
