package com.example.aula_06.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aula_06.ui.theme.*
import com.example.aula_06.ui.viewmodel.AuthViewModel

@Composable
fun LoginScreen(authViewModel: AuthViewModel, modifier: Modifier = Modifier, aoEntrar: () -> Unit) {

    // Controla qual aba esta selecionada (Entrar ou Cadastrar)
    var abaEntrar by rememberSaveable { mutableStateOf(true) }

    var nome by rememberSaveable { mutableStateOf("") }
    var mensagem by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

    LaunchedEffect(authViewModel.concluido) {
        if (authViewModel.concluido) {
            authViewModel.consumirResultado()
            senha = ""
            if (abaEntrar) aoEntrar() else {
                abaEntrar = true
                mensagem = "Conta criada! Digite sua senha para entrar."
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AzulFundo)
            .imePadding()
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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo circular grande
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .background(AzulEscuro),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "R", color = Color.White, fontWeight = FontWeight.Medium)
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "welcome", fontWeight = FontWeight.Medium)
            Text(text = "Acesse sua conta ou cadastre-se", color = AzulEscuro)

            Spacer(modifier = Modifier.height(16.dp))

            // Abas: Entrar / Cadastrar
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(enabled = !authViewModel.carregando) { abaEntrar = true; mensagem = ""; authViewModel.limparMensagem() },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Entrar",
                        color = if (abaEntrar) AzulEscuro else Color.Gray,
                        fontWeight = FontWeight.Medium
                    )
                    if (abaEntrar) {
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
                        .clickable(enabled = !authViewModel.carregando) { abaEntrar = false; mensagem = ""; authViewModel.limparMensagem() },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Cadastrar",
                        color = if (!abaEntrar) AzulEscuro else Color.Gray,
                        fontWeight = FontWeight.Medium
                    )
                    if (!abaEntrar) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp)
                                .background(AzulEscuro)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (!abaEntrar) {
                OutlinedTextField(enabled = !authViewModel.carregando, value = nome, onValueChange = { nome = it },
                    label = { Text("Nome") }, modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp))
                Spacer(modifier = Modifier.height(12.dp))
            }
            // Campo de e-mail
            OutlinedTextField(
                enabled = !authViewModel.carregando,
                value = email,
                onValueChange = { email = it },
                label = { Text("E-mail") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Campo de senha
            OutlinedTextField(
                enabled = !authViewModel.carregando,
                value = senha,
                onValueChange = { senha = it },
                label = { Text("Senha") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = authViewModel.mensagem ?: mensagem, color = AzulEscuro)
            if (authViewModel.carregando) Text("Aguarde...")

            Spacer(modifier = Modifier.height(16.dp))

            // Botao de entrar
            Button(
                onClick = {
                    mensagem = ""
                    if (abaEntrar) authViewModel.entrar(email, senha)
                    else authViewModel.cadastrar(nome, email, senha)
                    senha = ""
                },
                enabled = !authViewModel.carregando,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AzulEscuro)
            ) {
                Text(text = if (abaEntrar) "Entrar" else "Cadastrar", color = Color.White)
            }


            Text(text = if (abaEntrar) "Nao tem conta? Cadastre-se aqui" else "Ja tem conta? Entre aqui",
                color = AzulEscuro, modifier = Modifier.clickable(enabled = !authViewModel.carregando) { abaEntrar = !abaEntrar; mensagem = ""; authViewModel.limparMensagem() })
        }
    }
}
