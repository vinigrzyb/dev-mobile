package com.example.aula_06.validation

object ReceitaValidator {
    fun validar(nome: String, tempo: String, porcoes: String, dificuldade: String, ingredientes: String, modoPreparo: String) {
        require(nome.isNotBlank() &&
            tempo.isNotBlank() &&
            porcoes.isNotBlank() &&
            dificuldade.isNotBlank() &&
            ingredientes.isNotBlank() &&
            modoPreparo.isNotBlank()) {
            "Preencha todos os campos da receita."
        }
    }
}
