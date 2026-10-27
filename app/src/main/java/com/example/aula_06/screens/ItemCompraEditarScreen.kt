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
fun ItemCompraEditarScreen(viewModel: ListaComprasViewModel, itemId: Long, aoVoltar: () -> Unit) {
    LaunchedEffect(itemId) {
        viewModel.buscarPorId(itemId)
    }
    LaunchedEffect(viewModel.salvo) {
        if (viewModel.salvo) { viewModel.consumirResultado(); aoVoltar() }
    }
    if (!viewModel.carregado) {
        Column(Modifier.fillMaxSize().background(AzulFundo).padding(16.dp)) {
            viewModel.mensagem?.let { Text(it) }
            if (viewModel.carregando) CircularProgressIndicator()
            else TextButton(onClick = { viewModel.buscarPorId(itemId) }) {
                Text("Tentar carregar novamente.")
            }
            TextButton(onClick = aoVoltar) { Text("Voltar") }
        }
        return
    }

    Column(Modifier.fillMaxSize().background(AzulFundo).imePadding()
        .verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        TextButton(onClick = aoVoltar, enabled = !viewModel.carregando) { Text("Voltar") }
        Text("Editar item", color = AzulEscuro,
            style = MaterialTheme.typography.titleLarge)
        OutlinedTextField(value = viewModel.nome, onValueChange = viewModel::mudarNome,
            label = { Text("Nome do item") }, modifier = Modifier.fillMaxWidth(),
            enabled = viewModel.carregado && !viewModel.carregando)
        OutlinedTextField(value = viewModel.quantidade, onValueChange = viewModel::mudarQuantidade,
            label = { Text("Quantidade") }, modifier = Modifier.fillMaxWidth(),
            enabled = viewModel.carregado && !viewModel.carregando)
        viewModel.mensagem?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        if (viewModel.carregando) Text("Aguarde...")
        Button(onClick = viewModel::salvarAlteracoes, enabled = viewModel.carregado && !viewModel.carregando && !viewModel.salvo,
            modifier = Modifier.fillMaxWidth()) { Text("Salvar item") }
    }
}
