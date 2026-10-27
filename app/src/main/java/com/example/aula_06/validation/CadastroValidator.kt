package com.example.aula_06.validation

import android.util.Patterns

object CadastroValidator {
    fun validar(nome: String, email: String, senha: String) {
        validarNome(nome)
        require(email.length <= 254 && Patterns.EMAIL_ADDRESS.matcher(email).matches()) { "Informe um e-mail valido." }
        validarSenha(senha)
    }

    fun validar(nome: String, senha: String) {
        validarNome(nome)
        validarSenha(senha)
    }

    private fun validarNome(nome: String) {
        require(nome.length in 3..100) { "Informe um nome entre 3 e 100 caracteres." }
    }

    private fun validarSenha(senha: String) {
        require(senha.length in 6..128 && senha.isNotBlank()) { "Use uma senha entre 6 e 128 caracteres." }
    }
}
