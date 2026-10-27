package com.example.aula_06.auth

import org.junit.Assert.*
import org.junit.Test

class AutenticacaoTest {
    @Test fun hashValidaSomenteASenhaCorreta() {
        val salt = Autenticacao.criarSalt()
        val hash = Autenticacao.gerarHash("senha123", salt)
        assertTrue(Autenticacao.validarSenha("senha123", salt, hash))
        assertFalse(Autenticacao.validarSenha("errada123", salt, hash))
        assertFalse(Autenticacao.validarSenha("senha123", Autenticacao.criarSalt(), hash))
        assertFalse(hash.contentEquals("senha123".toByteArray()))
    }
    @Test fun espacosDaSenhaNaoSaoRemovidos() {
        val salt = Autenticacao.criarSalt()
        val hash = Autenticacao.gerarHash(" senha123 ", salt)
        assertTrue(Autenticacao.validarSenha(" senha123 ", salt, hash))
        assertFalse(Autenticacao.validarSenha("senha123", salt, hash))
    }
}
