package com.example.aula_06.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.aula_06.ui.theme.*
import com.example.aula_06.ui.viewmodel.AuthViewModel

@Composable
fun CadastroUsuarioScreen(authViewModel: AuthViewModel, aoLogin: () -> Unit) {
    var nome by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

    LaunchedEffect(authViewModel.concluido) {
        if (authViewModel.concluido) {
            authViewModel.consumirResultado()
            aoLogin()
        }
    }

    Column(
        Modifier.fillMaxSize().background(AzulFundo).imePadding()
            .verticalScroll(rememberScrollState()).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Novo usuario", color = AzulEscuro, style = MaterialTheme.typography.headlineMedium)
        OutlinedTextField(value = nome, onValueChange = { if (it.length <= 100) nome = it },
            label = { Text("Nome") }, modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp), singleLine = true, enabled = !authViewModel.carregando)
        OutlinedTextField(value = email, onValueChange = { if (it.length <= 254) email = it },
            label = { Text("E-mail") }, modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            shape = RoundedCornerShape(24.dp), singleLine = true, enabled = !authViewModel.carregando)
        OutlinedTextField(value = senha, onValueChange = { if (it.length <= 128) senha = it },
            label = { Text("Senha") }, modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            shape = RoundedCornerShape(24.dp), singleLine = true, enabled = !authViewModel.carregando)
        authViewModel.mensagem?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        if (authViewModel.carregando) CircularProgressIndicator()
        Button(onClick = {
            authViewModel.cadastrar(nome, email, senha)
            senha = ""
        }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp),
            enabled = !authViewModel.carregando && !authViewModel.concluido) {
            Text("Cadastrar")
        }
        TextButton(onClick = aoLogin, enabled = !authViewModel.carregando) {
            Text("Voltar para o Login")
        }
    }
}
