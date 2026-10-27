package com.example.aula_06.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.aula_06.auth.SessaoUsuario
import com.example.aula_06.data.dal.ReceitaDAO
import com.example.aula_06.model.Receita
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReceitaViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = ReceitaDAO(application)
    var receitas by mutableStateOf<List<Receita>>(emptyList())
        private set
    var receita by mutableStateOf(Receita())
        private set
    var carregando by mutableStateOf(false)
        private set
    var mensagem by mutableStateOf<String?>(null)
        private set
    var carregado by mutableStateOf(false)
        private set
    var salvo by mutableStateOf(false)
        private set
    var excluido by mutableStateOf(false)
        private set

    private fun executar(operacao: suspend () -> Unit) {
        if (carregando || salvo || excluido) return
        carregando = true
        mensagem = null
        viewModelScope.launch {
            try { SessaoUsuario.exigirUsuario(); operacao()
            } catch (erro: CancellationException) { throw erro
            } catch (erro: IllegalArgumentException) { mensagem = erro.message
            } catch (erro: Exception) { mensagem = "Nao foi possivel acessar as receitas. Tente novamente."
            } finally { carregando = false }
        }
    }
    fun listar() = executar { receitas = withContext(Dispatchers.IO) { dao.listar() } }
    fun carregar(id: Long) = executar {
        carregado = false
        receita = if (id == 0L) Receita() else requireNotNull(withContext(Dispatchers.IO) { dao.buscarPorId(id) }) {
            "Receita nao encontrada."
        }
        carregado = true
    }
    fun editar(valor: Receita) { if (!carregando && !salvo) receita = valor }
    fun salvar() = executar {
        require(carregado) { "Aguarde o carregamento da receita." }
        val dados = receita.copy(nome = receita.nome.trim(), tempo = receita.tempo.trim(),
            porcoes = receita.porcoes.trim(), dificuldade = receita.dificuldade.trim(),
            ingredientes = receita.ingredientes.trim(), modoPreparo = receita.modoPreparo.trim())
        require(dados.nome.isNotBlank() && dados.tempo.isNotBlank() && dados.porcoes.isNotBlank() &&
            dados.dificuldade.isNotBlank() && dados.ingredientes.isNotBlank() && dados.modoPreparo.isNotBlank()) {
            "Preencha todos os campos da receita."
        }
        withContext(Dispatchers.IO) {
            if (dados.id == 0L) dao.cadastrar(dados) else dao.alterar(dados)
        }
        salvo = true
    }
    fun excluir() = executar {
        require(carregado && receita.id > 0) { "Selecione uma receita." }
        withContext(Dispatchers.IO) { dao.excluir(receita.id) }
        excluido = true
    }
    fun consumirResultado() { salvo = false; excluido = false }
}
