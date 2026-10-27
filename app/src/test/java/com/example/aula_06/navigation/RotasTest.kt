package com.example.aula_06.navigation

import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Test

class RotasTest {
    @Test fun preservaIdDaReceitaNaSerializacao() {
        val rota = DetalheReceita(42L)
        assertEquals(rota, Json.decodeFromString<DetalheReceita>(Json.encodeToString(rota)))
    }
}
