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

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {

    // Controla qual aba esta selecionada (Entrar ou Cadastrar)
    var abaEntrar by remember { mutableStateOf(true) }

    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

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
                        .clickable { abaEntrar = true },
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
                        .clickable { abaEntrar = false },
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

            // Campo de e-mail
            OutlinedTextField(
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
                value = senha,
                onValueChange = { senha = it },
                label = { Text("Senha") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Esqueci a senha?", color = AzulEscuro)

            Spacer(modifier = Modifier.height(16.dp))

            // Botao de entrar
            Button(
                onClick = { /* login sera implementado depois */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AzulEscuro)
            ) {
                Text(text = "Entrar", color = Color.White)
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Login social
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                LoginSocialIcone("G")
                LoginSocialIcone("F")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Nao tem conta? Cadastre-se aqui", color = AzulEscuro)
        }
    }
}

@Composable
fun LoginSocialIcone(letra: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(AzulTopo),
        contentAlignment = Alignment.Center
    ) {
        Text(text = letra, color = AzulEscuro, fontWeight = FontWeight.Medium)
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    Aula06Theme {
        LoginScreen()
    }
}
