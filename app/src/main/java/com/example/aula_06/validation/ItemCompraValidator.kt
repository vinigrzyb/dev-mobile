package com.example.aula_06.validation

object ItemCompraValidator {
    fun validar(nome: String, quantidade: String) {
        require(nome.isNotBlank() &&
            quantidade.isNotBlank()) {
            "Informe o nome e a quantidade."
        }
    }
}
