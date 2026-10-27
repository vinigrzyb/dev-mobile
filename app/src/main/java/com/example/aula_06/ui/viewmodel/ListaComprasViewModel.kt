package com.example.aula_06.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.aula_06.auth.SessaoUsuario
import com.example.aula_06.data.dal.ItemCompraDAO
import com.example.aula_06.model.ItemCompra
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
            try { SessaoUsuario.exigirUsuario(); operacao()
            } catch (erro: CancellationException) { throw erro
            } catch (erro: IllegalArgumentException) { mensagem = erro.message
            } catch (erro: Exception) { mensagem = "Nao foi possivel acessar as compras. Tente novamente."
            } finally { carregando = false }
        }
    }
    fun listar() = executar { itens = withContext(Dispatchers.IO) { dao.listar() } }
    fun carregar(id: Long) = executar {
        carregado = false
        item = if (id == 0L) ItemCompra() else requireNotNull(withContext(Dispatchers.IO) { dao.buscarPorId(id) }) {
            "Item nao encontrado."
        }
        carregado = true
    }
    fun editar(valor: ItemCompra) { if (!carregando && !salvo) item = valor }
    fun salvar() = executar {
        require(carregado) { "Aguarde o carregamento do item." }
        val dados = item.copy(nome = item.nome.trim(), quantidade = item.quantidade.trim())
        require(dados.nome.isNotBlank() && dados.quantidade.isNotBlank()) { "Informe o nome e a quantidade." }
        withContext(Dispatchers.IO) {
            if (dados.id == 0L) dao.cadastrar(dados) else dao.alterar(dados)
        }
        salvo = true
    }
    fun excluir(id: Long) = executar {
        withContext(Dispatchers.IO) { dao.excluir(id) }
        itens = itens.filterNot { it.id == id }
    }
    fun consumirResultado() { salvo = false }
}
