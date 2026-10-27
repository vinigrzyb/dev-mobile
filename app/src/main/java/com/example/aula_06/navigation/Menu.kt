package com.example.aula_06.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.aula_06.ui.theme.AzulTopo

@Composable
fun Menu(aoReceitas: () -> Unit, aoCompras: () -> Unit, aoSair: () -> Unit) {
    Row(Modifier.fillMaxWidth().background(AzulTopo), horizontalArrangement = Arrangement.SpaceEvenly) {
        TextButton(onClick = aoReceitas) { Text("Receitas") }
        TextButton(onClick = aoCompras) { Text("Lista de compras") }
        TextButton(onClick = aoSair) { Text("Sair") }
    }
}
