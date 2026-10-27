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
import com.example.aula_06.ui.viewmodel.ReceitaViewModel

@Composable
fun CadastroReceitaScreen(
    viewModel: ReceitaViewModel,
    receitaId: Long = 0,
    modifier: Modifier = Modifier,
    aoSalvar: () -> Unit,
    aoVoltar: () -> Unit
) {

    val receita = viewModel.receita
    LaunchedEffect(receitaId) {
        if (!viewModel.carregado) viewModel.carregar(receitaId)
    }
    LaunchedEffect(viewModel.salvo) {
        if (viewModel.salvo) { viewModel.consumirResultado(); aoSalvar() }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AzulFundo)
            .imePadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(AzulTopo)
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            TextButton(onClick = aoVoltar, enabled = !viewModel.carregando) { Text("Voltar") }
            Text(text = if (receitaId == 0L) "Nova Receita" else "Editar Receita", color = AzulEscuro, fontWeight = FontWeight.Medium)
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
                    OutlinedTextField(
                        value = receita.nome,
                        label = { Text("Nome da receita") },
                        enabled = viewModel.carregado && !viewModel.carregando,
                        onValueChange = { viewModel.editar(receita.copy(nome = it)) },
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
                            OutlinedTextField(
                                value = receita.tempo,
                        label = { Text("Tempo") },
                        enabled = viewModel.carregado && !viewModel.carregando,
                                onValueChange = { viewModel.editar(receita.copy(tempo = it)) },
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = { Text("45 min") }
                            )
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            OutlinedTextField(
                                value = receita.porcoes,
                        label = { Text("Porcoes") },
                        enabled = viewModel.carregado && !viewModel.carregando,
                                onValueChange = { viewModel.editar(receita.copy(porcoes = it)) },
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = { Text("4") }
                            )
                        }
                    }
                }

                item {
                    OutlinedTextField(
                        value = receita.dificuldade,
                        label = { Text("Dificuldade") },
                        enabled = viewModel.carregado && !viewModel.carregando,
                        onValueChange = { viewModel.editar(receita.copy(dificuldade = it)) },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("EX: Facil") }
                    )
                }

                item {
                    OutlinedTextField(
                        value = receita.ingredientes,
                        label = { Text("Ingredientes") },
                        enabled = viewModel.carregado && !viewModel.carregando,
                        onValueChange = { viewModel.editar(receita.copy(ingredientes = it)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        placeholder = { Text("Ingredientes") }
                    )
                }

                item {
                    OutlinedTextField(
                        value = receita.modoPreparo,
                        label = { Text("Modo de preparo") },
                        enabled = viewModel.carregado && !viewModel.carregando,
                        onValueChange = { viewModel.editar(receita.copy(modoPreparo = it)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        placeholder = { Text("") }
                    )
                }

                item {
                    viewModel.mensagem?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                    if (viewModel.carregando) Text("Aguarde...")
                    Button(
                        onClick = viewModel::salvar,
                        enabled = viewModel.carregado && !viewModel.carregando && !viewModel.salvo,
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
