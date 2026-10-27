package com.example.aula_06.navigation

import kotlinx.serialization.Serializable

@Serializable object Login
@Serializable object Home
@Serializable data class DetalheReceita(val id: Long)
@Serializable object CadastroReceita
@Serializable data class ReceitaEditar(val id: Long)
@Serializable object ListaCompras
@Serializable object CadastroItemCompra
@Serializable data class ItemCompraEditar(val id: Long)
@Serializable object Usuarios
@Serializable object CadastrarUsuario
@Serializable data class UsuarioEditar(val id: Long)
