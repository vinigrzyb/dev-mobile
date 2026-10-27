package com.example.aula_06.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.aula_06.auth.Autenticacao
import com.example.aula_06.auth.SessaoUsuario
import com.example.aula_06.model.Usuario
import com.example.aula_06.data.dal.UsuarioDAO
import com.example.aula_06.validation.CadastroValidator
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UsuarioViewModel(application: Application): AndroidViewModel(application) {
    private val dao = UsuarioDAO(application)
    val usuarioAtualId: Long? get() = SessaoUsuario.usuarioId
    var usuarios by mutableStateOf<List<Usuario>>(emptyList())
        private set
    var usuario by mutableStateOf<Usuario?>(null)
        private set
    var nome by mutableStateOf("")
        private set
    var mensagem by mutableStateOf<String?>(null)
        private set
    var carregando by mutableStateOf(false)
        private set
    var salvo by mutableStateOf(false)
        private set

    private fun executar(operacao: suspend () -> Unit){
        if (carregando || salvo) return
        carregando = true
        mensagem = null

        viewModelScope.launch {
            try{
                SessaoUsuario.exigirUsuario()
                operacao()
            }catch(erro: CancellationException){
                throw  erro
            }catch(erro: IllegalArgumentException){
                mensagem = erro.message
            }catch(erro: Exception){
               mensagem = "Ocorreu um erro inesperado!"
            }finally {
                carregando = false
            }
        }
    }

    fun listarUsuarios() = executar {
        val lista = withContext(Dispatchers.IO) { dao.listarUsuarios()}
        usuarios = lista
    }

    fun buscarUsuarioPorId(id: Long){
        if(usuario?.id == id) return
        executar {
            val encontrado = withContext(Dispatchers.IO) {dao.buscarUsuarioPorId(id)}
            usuario = requireNotNull(encontrado) {"Usuario não encontrado"}
            nome = encontrado.nome
        }
    }

    fun atualizarNome(valor: String){
        if (!carregando && valor.length <= 100) nome = valor
    }

    fun salvarAlteracoes(novaSenha: String) = executar {
        val selecionado = requireNotNull(usuario) { "Selecione um usuário" }
        val nomeLimpo = nome.trim()
        CadastroValidator.validar(nomeLimpo, novaSenha)
        withContext(Dispatchers.IO){
            if (novaSenha.isEmpty()){
                dao.atualizarUsuario(selecionado.id, nomeLimpo)
            } else {
                val salt = Autenticacao.criarSalt()
                val hash = Autenticacao.gerarHash(novaSenha, salt)
                try {
                    dao.atualizarUsuario(selecionado.id, nomeLimpo, hash, salt)
                } finally {
                    hash.fill(0)
                }
            }
        }
        salvo = true
    }

    fun excluirUsuario(id: Long) = executar {
        val atual = SessaoUsuario.exigirUsuario()
        require(id != atual) { "Você não pode excluir o seu usuário"}
        withContext(Dispatchers.IO) { dao.excluirUsuario(id, atual)}
        usuarios = usuarios.filterNot { it.id ==id }
        mensagem = "Usuario Excluído."
    }

    fun limparConfirmacao(){ salvo = false}
}