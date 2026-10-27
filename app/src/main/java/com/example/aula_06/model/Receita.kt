package com.example.aula_06.model

data class Receita(
    val id: Long = 0,
    val nome: String = "",
    val tempo: String = "",
    val porcoes: String = "",
    val dificuldade: String = "",
    val ingredientes: String = "",
    val modoPreparo: String = "",
    // Nome estavel do recurso existente; novos cadastros podem nao ter foto.
    val imagem: String? = null
)
