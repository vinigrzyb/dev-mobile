package com.example.aula_06.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aula_06.ui.theme.*

@Composable
fun CadastroReceitaScreen(modifier: Modifier = Modifier) {

    // campos do form
    var nome by remember { mutableStateOf("") }
    var tempo by remember { mutableStateOf("") }
    var porcoes by remember { mutableStateOf("") }
    var dificuldade by remember { mutableStateOf("") }
    var ingredientes by remember { mutableStateOf("") }
    var modoPreparo by remember { mutableStateOf("") }

    var receitaSalva by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AzulFundo)
    ) {
        // titulo da pag
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(AzulTopo)
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Nova Receita", color = AzulEscuro, fontWeight = FontWeight.Medium)
        }

        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = Branco)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text(text = "Nome da receita", color = AzulEscuro, fontWeight = FontWeight.Medium)
                    OutlinedTextField(
                        value = nome,
                        onValueChange = { nome = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Ex: Lasanha Bolonhesa Classica") }
                    )
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "Tempo", color = AzulEscuro, fontWeight = FontWeight.Medium)
                            OutlinedTextField(
                                value = tempo,
                                onValueChange = { tempo = it },
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = { Text("45 min") }
                            )
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "Porcoes", color = AzulEscuro, fontWeight = FontWeight.Medium)
                            OutlinedTextField(
                                value = porcoes,
                                onValueChange = { porcoes = it },
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = { Text("4") }
                            )
                        }
                    }
                }

                item {
                    Text(text = "Dificuldade", color = AzulEscuro, fontWeight = FontWeight.Medium)
                    OutlinedTextField(
                        value = dificuldade,
                        onValueChange = { dificuldade = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("EX: Facil") }
                    )
                }

                item {
                    Text(text = "Ingredientes", color = AzulEscuro, fontWeight = FontWeight.Medium)
                    OutlinedTextField(
                        value = ingredientes,
                        onValueChange = { ingredientes = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        placeholder = { Text("Ingredientes") }
                    )
                }

                item {
                    Text(text = "Modo de preparo", color = AzulEscuro, fontWeight = FontWeight.Medium)
                    OutlinedTextField(
                        value = modoPreparo,
                        onValueChange = { modoPreparo = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        placeholder = { Text("") }
                    )
                }

                item {
                    Button(
                        onClick = {},
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = AzulEscuro)
                    ) {
                        Text(text = "Salvar Receita", color = Color.White)
                    }
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CadastroReceitaScreenPreview() {
    Aula06Theme {
        CadastroReceitaScreen()
    }
}