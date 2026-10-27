package com.example.aula_06.navigation

import kotlinx.serialization.Serializable

@Serializable object Login
@Serializable object Home
@Serializable data class DetalheReceita(val id: Long)
@Serializable data class CadastroReceita(val id: Long = 0)
@Serializable object ListaCompras
@Serializable data class CadastroItemCompra(val id: Long = 0)
