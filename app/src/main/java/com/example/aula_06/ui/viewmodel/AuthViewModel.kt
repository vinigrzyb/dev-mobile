package com.example.aula_06.ui.viewmodel

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.aula_06.auth.Autenticacao
import com.example.aula_06.auth.SessaoUsuario
import com.example.aula_06.data.dal.UsuarioDAO
import com.example.aula_06.validation.CadastroValidator
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = UsuarioDAO(application)
    var carregando by mutableStateOf(false)
        private set
    var mensagem by mutableStateOf<String?>(null)
        private set
    var concluido by mutableStateOf(false)
        private set

    private fun executar(operacao: suspend () -> Boolean) {
        if (carregando || concluido) return
        carregando = true
        mensagem = null
        viewModelScope.launch {
            try {
                concluido = operacao()
                if (!concluido) mensagem = "E-mail ou senha incorretos."
            } catch (erro: CancellationException) { throw erro
            } catch (erro: SQLiteConstraintException) {
                mensagem = "Este e-mail ja esta cadastrado."
            } catch (erro: IllegalArgumentException) { mensagem = erro.message
            } catch (erro: Exception) { mensagem = "Nao foi possivel acessar as contas. Tente novamente."
            } finally { carregando = false }
        }
    }

    fun cadastrar(nome: String, email: String, senha: String) = executar {
        val nomeLimpo = nome.trim()
        val emailLimpo = email.trim().lowercase(Locale.ROOT)
        CadastroValidator.validar(nomeLimpo, emailLimpo, senha)
        withContext(Dispatchers.IO) {
            val salt = Autenticacao.criarSalt()
            val hash = Autenticacao.gerarHash(senha, salt)
            try { dao.cadastrar(nomeLimpo, emailLimpo, hash, salt) }
            finally { hash.fill(0) }
        }
        true
    }

    fun entrar(email: String, senha: String) = executar {
        require(email.length <= 254 && senha.length in 6..128) { "E-mail ou senha incorretos." }
        val id = withContext(Dispatchers.IO) {
            val dados = dao.buscarSenha(email.trim().lowercase(Locale.ROOT))
            val hash = dados?.hash ?: ByteArray(32)
            val salt = dados?.salt ?: ByteArray(16)
            try {
                if (Autenticacao.validarSenha(senha, salt, hash)) dados?.usuario?.id else null
            } finally { hash.fill(0) }
        }
        if (id != null) { SessaoUsuario.iniciar(id); true } else false
    }
    fun consumirResultado() { concluido = false }
    fun limparMensagem() { mensagem = null }
}
