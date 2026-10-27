package com.example.aula_06.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.aula_06.ui.theme.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.aula_06.ui.viewmodel.UsuarioViewModel

@Composable
fun UsuarioEditarScreen(
    usuarioId: Long, viewModel: UsuarioViewModel, aoVoltar: () -> Unit ) {

    LaunchedEffect(usuarioId) { viewModel.buscarUsuarioPorId(usuarioId) }
    LaunchedEffect(viewModel.salvo) {
        if (viewModel.salvo){
            viewModel.limparConfirmacao()
            aoVoltar()
        }
    }
    var novaSenha by remember { mutableStateOf("") }

    if(viewModel.usuarioAtualId == null){
        Text("Faca login para continuar.")
        return
    }

    Column(
        modifier = Modifier.fillMaxSize().background(AzulFundo).imePadding()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Alterar usuario", color = AzulEscuro, style = MaterialTheme.typography.headlineMedium)
        viewModel.mensagem?.let { Text(it) }
        if(viewModel.carregando) CircularProgressIndicator()

        val usuario = viewModel.usuario
        if(usuario != null){
            OutlinedTextField(
                value = viewModel.nome,
                onValueChange = viewModel::atualizarNome,
                label = { Text("Nome") },
                singleLine = true,
                shape = RoundedCornerShape(24.dp),
                enabled = !viewModel.carregando,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = usuario.email,
                onValueChange = {},
                readOnly = true,
                label = { Text("E-mail (nao pode ser alterado)") },
                singleLine = true,
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = novaSenha,
                onValueChange = { if(it.length <= 128) novaSenha = it },
                label = { Text("Senha") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password,
                    autoCorrect = false),
                singleLine = true,
                shape = RoundedCornerShape(24.dp),
                enabled = !viewModel.carregando,
                modifier = Modifier.fillMaxWidth()
            )
            Text("Informe a senha para salvar (6 a 128 caracteres).")
            Button(
                onClick = {
                    viewModel.salvarAlteracoes(novaSenha)
                    novaSenha = ""
                },
                enabled = !viewModel.carregando && !viewModel.salvo
            ){ Text("Salvar")}
        } else if(!viewModel.carregando){
            TextButton(onClick = { viewModel.buscarUsuarioPorId(usuarioId) }) {
                Text("Tentar carregar novamente.")
            }
        }

        TextButton(onClick = aoVoltar, enabled = !viewModel.carregando) {
            Text("Voltar para usuarios")
        }

    }
}
