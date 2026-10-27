package com.example.aula_06.validation

import android.util.Patterns

object CadastroValidator {
    fun validar(nome: String, email: String, senha: String) {
        require(nome.length in 3..100) { "Informe um nome entre 3 e 100 caracteres." }
        require(email.length <= 254 && Patterns.EMAIL_ADDRESS.matcher(email).matches()) { "Informe um e-mail valido." }
        require(senha.length in 6..128 && senha.isNotBlank()) { "Use uma senha entre 6 e 128 caracteres." }
    }
}
