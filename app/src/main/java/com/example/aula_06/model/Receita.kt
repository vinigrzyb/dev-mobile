package com.example.aula_06.model

import com.example.aula_06.R

// Representa uma receita da lista da Home
data class Receita(val nome: String, val imagem: Int)

// Lista de receitas exibidas na Home
val listaReceitas = listOf(
    Receita("Espaguete a Bolonhesa", R.drawable.espaguete),
    Receita("Tarta de Morango", R.drawable.tarta_morango),
    Receita("Sopa de Abobora Cremosa", R.drawable.sopa_abobora),
    Receita("Salada de Verao", R.drawable.salada_verao),
    Receita("Mini Hamburgueres de Festa", R.drawable.mini_hamburgueres),
    Receita("Suco de Laranja Fresco", R.drawable.suco_laranja)
)
