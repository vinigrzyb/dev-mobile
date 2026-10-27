package com.example.aula_06

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.aula_06.auth.SessaoUsuario
import androidx.test.platform.app.InstrumentationRegistry
import android.database.sqlite.SQLiteDatabase
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.File

class EntregaTest {
    @get:Rule val tela = createAndroidComposeRule<MainActivity>()
    @Before fun iniciarSemSessao() { tela.runOnIdle { SessaoUsuario.encerrar() } }

    private fun aguardar(texto: String) {
        tela.waitUntil(10_000) { tela.onAllNodesWithText(texto).fetchSemanticsNodes().isNotEmpty() }
    }
    private fun botao(texto: String) = tela.onAllNodesWithText(texto).onLast()
    private fun campo(texto: String, valor: String) {
        tela.onNodeWithText(texto).performScrollTo().performTextReplacement(valor)
    }
    private fun cadastrar(): String {
        aguardar("E-mail")
        tela.onNodeWithText("Lista de compras").assertDoesNotExist()
        tela.onNodeWithText("Cadastrar").performClick()
        val email = "teste${System.nanoTime()}@exemplo.com"
        campo("Nome", "Pessoa Teste")
        campo("E-mail", email)
        campo("Senha", "senha123")
        botao("Cadastrar").performScrollTo().performClick()
        aguardar("Conta criada! Digite sua senha para entrar.")
        return email
    }
    private fun entrar() : String {
        val email = cadastrar()
        campo("Senha", "senha123")
        botao("Entrar").performScrollTo().performClick()
        aguardar("Nova receita")
        return email
    }
    private fun localizarReceita(nome: String) {
        tela.onNode(hasScrollToNodeAction()).performScrollToNode(hasText(nome))
        tela.onNodeWithText(nome).performClick()
        aguardar("Editar")
    }

    @Test fun rascunhoDeReceitaSobreviveRecriacaoDaTela() {
        entrar()
        tela.onNodeWithText("Nova receita").performClick()
        aguardar("Nome da receita")
        campo("Nome da receita", "Rascunho da receita")
        tela.activityRule.scenario.recreate()
        aguardar("Nome da receita")
        tela.onNodeWithText("Nome da receita").assertTextContains("Rascunho da receita")
    }

    @Test fun rascunhoDeCompraSobreviveRecriacaoDaTela() {
        entrar()
        tela.onNodeWithText("Lista de compras").performClick()
        aguardar("Adicionar item")
        tela.onNodeWithText("Adicionar item").performClick()
        aguardar("Nome do item")
        campo("Nome do item", "Rascunho da compra")
        campo("Quantidade", "3 pacotes")
        tela.activityRule.scenario.recreate()
        aguardar("Nome do item")
        tela.onNodeWithText("Nome do item").assertTextContains("Rascunho da compra")
        tela.onNodeWithText("Quantidade").assertTextContains("3 pacotes")
    }

    @Test fun abaCadastroSobreviveRecriacaoSemVirarLogin() {
        aguardar("E-mail")
        tela.onNodeWithText("Cadastrar").performClick()
        campo("Nome", "Pessoa Rascunho")
        campo("E-mail", "rascunho@exemplo.com")
        tela.activityRule.scenario.recreate()
        aguardar("E-mail")
        tela.onNodeWithText("Nome").assertExists().assertTextContains("Pessoa Rascunho")
        tela.onNodeWithText("E-mail").assertTextContains("rascunho@exemplo.com")
        tela.onNodeWithText("Lista de compras").assertDoesNotExist()
    }

    @Test fun cadastroPersisteELogoutProtegeAsTelas() {
        val email = entrar()
        val contexto = InstrumentationRegistry.getInstrumentation().targetContext
        val arquivo = File(contexto.noBackupFilesDir, "receitas_diarias.db")
        assertTrue(arquivo.isFile)
        SQLiteDatabase.openDatabase(arquivo.path, null, SQLiteDatabase.OPEN_READONLY).use { db ->
            db.rawQuery("SELECT email, length(senha), length(salt) FROM usuarios WHERE email = ?", arrayOf(email)).use {
                assertTrue(it.moveToFirst()); assertEquals(email, it.getString(0))
                assertEquals(32, it.getInt(1)); assertEquals(16, it.getInt(2))
            }
        }
        tela.onNodeWithText("Sair").performClick()
        aguardar("E-mail")
        tela.onNodeWithText("Lista de compras").assertDoesNotExist()
        tela.runOnIdle { assertNull(SessaoUsuario.usuarioId) }
        // Recria a Activity e entra novamente com a conta persistida.
        tela.activityRule.scenario.recreate()
        aguardar("E-mail")
        campo("E-mail", email)
        campo("Senha", "errada123")
        botao("Entrar").performScrollTo().performClick()
        aguardar("E-mail ou senha incorretos.")
        tela.onNodeWithText("Lista de compras").assertDoesNotExist()
        campo("Senha", "senha123")
        botao("Entrar").performScrollTo().performClick()
        aguardar("Nova receita")
        tela.runOnIdle { assertNotNull(SessaoUsuario.usuarioId) }
    }

    @Test fun crudReceitasAtualizaListaEDetalhe() {
        entrar()
        val nome = "Receita ${System.nanoTime()}"
        tela.onNodeWithText("Nova receita").performClick()
        aguardar("Nome da receita")
        tela.waitUntil(10_000) { tela.onNodeWithText("Nome da receita").fetchSemanticsNode().config.contains(androidx.compose.ui.semantics.SemanticsActions.SetText) }
        campo("Nome da receita", nome)
        campo("Tempo", "20 min")
        campo("Porcoes", "2")
        campo("Dificuldade", "Facil")
        campo("Ingredientes", "Farinha\nOvos")
        campo("Modo de preparo", "Misturar e assar")
        botao("Salvar Receita").performScrollTo().performClick()
        aguardar("Nova receita")
        localizarReceita(nome)
        tela.onNodeWithText("Farinha").assertExists()
        tela.onNodeWithText("Editar").performClick()
        aguardar("Editar Receita")
        campo("Nome da receita", "$nome editada")
        botao("Salvar Receita").performScrollTo().performClick()
        aguardar("Editar")
        aguardar("$nome editada")
        tela.onNodeWithText("Excluir").performClick()
        tela.onNodeWithText("Cancelar").performClick()
        tela.onNodeWithText("$nome editada").assertExists()
        tela.onNodeWithText("Excluir").performClick()
        tela.onNodeWithText("Confirmar exclusao").performClick()
        aguardar("Nova receita")
        tela.onNodeWithText("$nome editada").assertDoesNotExist()
    }

    @Test fun crudComprasCompartilhadoEntreContas() {
        entrar()
        val nome = "Farinha ${System.nanoTime()}"
        tela.onNodeWithText("Lista de compras").performClick()
        aguardar("Adicionar item")
        tela.onNodeWithText("Adicionar item").performClick()
        aguardar("Nome do item")
        campo("Nome do item", nome)
        campo("Quantidade", "1 kg")
        botao("Salvar item").performScrollTo().performClick()
        aguardar("Adicionar item")
        tela.onNode(hasScrollToNodeAction()).performScrollToNode(hasText(nome))
        tela.onNode(hasText("Editar") and hasAnyAncestor(hasAnyChild(hasText(nome)))).performClick()
        aguardar("Editar item")
        campo("Quantidade", "2 kg")
        botao("Salvar item").performScrollTo().performClick()
        aguardar("Adicionar item")
        tela.onNodeWithText("Sair").performClick()
        entrar()
        tela.onNodeWithText("Lista de compras").performClick()
        aguardar("Adicionar item")
        tela.onNode(hasScrollToNodeAction()).performScrollToNode(hasText(nome))
        tela.onNodeWithText("2 kg").assertExists()
        tela.onNode(hasText("Excluir") and hasAnyAncestor(hasAnyChild(hasText(nome)))).performClick()
        tela.onNodeWithText("Confirmar exclusao").performClick()
        tela.waitUntil(10_000) { tela.onAllNodesWithText(nome).fetchSemanticsNodes().isEmpty() }
    }
}
