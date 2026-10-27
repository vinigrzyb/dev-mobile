package com.example.aula_06.screens

import androidx.compose.foundation.background
import androidx.compose.material3.CardDefaults
import com.example.aula_06.ui.theme.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.aula_06.ui.viewmodel.UsuarioViewModel

@Composable
fun UsuarioScreen(
    viewModel: UsuarioViewModel,
    aoNovoUsuario: () -> Unit,
    aoEditarUsuario: (Long) -> Unit,
    aoVoltar: () -> Unit
) {
    val atual = viewModel.usuarioAtualId
    if(atual == null){
        Text("Faça login pra continuar")
        return
    }

    var excluirId by rememberSaveable { mutableStateOf<Long?>(null) }

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(AzulFundo).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Usuarios cadastrados", color = AzulEscuro, style = MaterialTheme.typography.headlineMedium)
                Button(onClick = aoNovoUsuario, enabled = !viewModel.carregando) {
                    Text("Novo usuario")
                }
                TextButton(onClick = viewModel::listarUsuarios, enabled = !viewModel.carregando) {
                    Text("Atualizar lista")
                }
                viewModel.mensagem?.let {Text(it)}
                if(viewModel.carregando) CircularProgressIndicator()
                if(!viewModel.carregando && viewModel.usuarios.isEmpty())
                    Text("Nenhum usuário encontrado")
            }
        }
        items(viewModel.usuarios, key = {it.id}){ usuario ->
            Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Branco)){
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(usuario.nome, style = MaterialTheme.typography.titleMedium)
                    Text(usuario.email)
                    if(usuario.id == atual) Text("Você está conectado.")
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        TextButton(
                            onClick = {aoEditarUsuario(usuario.id)},
                            enabled = !viewModel.carregando
                        ) { Text("Alterar") }
                        TextButton(
                            onClick = {excluirId = usuario.id},
                            enabled = !viewModel.carregando && usuario.id != atual
                        ){
                            Text("Excluir")
                        }
                    }
                }
            }

        }
        item { TextButton(onClick = aoVoltar) { Text("Voltar")} }
    }

    val selecionado = viewModel.usuarios.firstOrNull { it.id == excluirId }
    if (selecionado != null && selecionado.id != atual){
        AlertDialog(
            onDismissRequest = { excluirId = null},
            title = { Text("Excluir usuário?") },
            text = {Text("Excluir ${selecionado.nome}? Esta ação não pode ser desfeita")},
            confirmButton = {
                TextButton(
                    onClick = {
                        excluirId = null
                        viewModel.excluirUsuario(selecionado.id)
                    },
                    enabled = !viewModel.carregando
                ) { Text("Confirmar exclusao")}
            }, dismissButton = {
                TextButton(
                    onClick = {
                        excluirId = null
                    }
                ) { Text("Cancelar")}
            }
        )
    }

}