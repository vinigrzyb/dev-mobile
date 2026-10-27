package com.example.aula_06.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.aula_06.auth.SessaoUsuario
import com.example.aula_06.data.dal.ItemCompraDAO
import com.example.aula_06.model.ItemCompra
import com.example.aula_06.validation.ItemCompraValidator
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListaComprasViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = ItemCompraDAO(application)
    var itens by mutableStateOf<List<ItemCompra>>(emptyList())
        private set
    var item by mutableStateOf(ItemCompra())
        private set
    var nome by mutableStateOf("")
        private set
    var quantidade by mutableStateOf("")
        private set
    var carregando by mutableStateOf(false)
        private set
    var mensagem by mutableStateOf<String?>(null)
        private set
    var carregado by mutableStateOf(false)
        private set
    var salvo by mutableStateOf(false)
        private set

    private fun executar(operacao: suspend () -> Unit) {
        if (carregando || salvo) return
        carregando = true
        mensagem = null
        viewModelScope.launch {
            try {
                SessaoUsuario.exigirUsuario()
                operacao()
            } catch (erro: CancellationException) {
                throw erro
            } catch (erro: IllegalArgumentException) {
                mensagem = erro.message
            } catch (erro: Exception) {
                mensagem = "Nao foi possivel acessar as compras. Tente novamente."
            } finally {
                carregando = false
            }
        }
    }
    fun listar() = executar { itens = withContext(Dispatchers.IO) { dao.listar() } }
    fun buscarPorId(id: Long) {
        if (carregado && item.id == id) return
        carregar(id)
    }

    fun carregar(id: Long) = executar {
        carregado = false
        val encontrado = requireNotNull(withContext(Dispatchers.IO) { dao.buscarPorId(id) }) {
            "Item nao encontrado."
        }
        item = encontrado
        nome = encontrado.nome
        quantidade = encontrado.quantidade
        carregado = true
    }

    fun mudarNome(valor: String) {
        if (!carregando && !salvo) nome = valor
    }
    fun mudarQuantidade(valor: String) {
        if (!carregando && !salvo) quantidade = valor
    }

    fun cadastrar() = executar {
        ItemCompraValidator.validar(
            nome.trim(),
            quantidade.trim()
        )
        withContext(Dispatchers.IO) {
            dao.cadastrar(ItemCompra(
                nome = nome.trim(),
                quantidade = quantidade.trim()
            ))
        }
        salvo = true
    }

    fun salvarAlteracoes() = executar {
        require(carregado && item.id > 0) { "Selecione um registro para alterar." }
        ItemCompraValidator.validar(
            nome.trim(),
            quantidade.trim()
        )
        withContext(Dispatchers.IO) {
            dao.alterar(ItemCompra(
                id = item.id,
                nome = nome.trim(),
                quantidade = quantidade.trim()
            ))
        }
        salvo = true
    }
    fun excluir(id: Long) = executar {
        withContext(Dispatchers.IO) { dao.excluir(id) }
        itens = itens.filterNot { it.id == id }
    }
    fun consumirResultado() { salvo = false }
}
