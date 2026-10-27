package com.example.aula_06.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import com.example.aula_06.ui.theme.AzulTopo

@Composable
fun Menu(aoReceitas: () -> Unit, aoCompras: () -> Unit, aoUsuarios: () -> Unit, aoSair: () -> Unit) {
    Row(Modifier.fillMaxWidth().background(AzulTopo), verticalAlignment = Alignment.CenterVertically) {
        TextButton(onClick = aoReceitas, modifier = Modifier.weight(1f)) { Text("Receitas") }
        TextButton(onClick = aoCompras, modifier = Modifier.weight(1.4f)) { Text("Lista de compras", textAlign = TextAlign.Center) }
        TextButton(onClick = aoUsuarios, modifier = Modifier.weight(1f)) { Text("Usuarios") }
        TextButton(onClick = aoSair, modifier = Modifier.weight(0.8f)) { Text("Sair") }
    }
}
