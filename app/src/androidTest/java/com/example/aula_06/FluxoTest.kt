package com.example.aula_06

import android.content.Context
import android.content.ContextWrapper
import androidx.test.platform.app.InstrumentationRegistry
import com.example.aula_06.data.dal.*
import com.example.aula_06.data.local.AppDatabase
import com.example.aula_06.model.*
import com.example.aula_06.auth.Autenticacao
import org.junit.Assert.*
import org.junit.Before
import org.junit.After
import org.junit.Test
import java.io.File

class FluxoTest {
    private lateinit var pasta: File
    private lateinit var contexto: Context
    @Before fun bancoIsolado() {
        val base = InstrumentationRegistry.getInstrumentation().targetContext
        pasta = File(base.cacheDir, "banco-teste-${System.nanoTime()}").apply { mkdirs() }
        contexto = object : ContextWrapper(base) {
            override fun getApplicationContext(): Context = this
            override fun getNoBackupFilesDir(): File = pasta
        }
    }
    @After fun limparSomenteBancoDoTeste() { pasta.deleteRecursively() }

    @Test fun doisCrudsPersistemSemConfundirNomesIguais() {
        val receitas = ReceitaDAO(contexto)
        val r = Receita(nome = "Sopa", tempo = "20", porcoes = "2", dificuldade = "Facil", ingredientes = "Agua", modoPreparo = "Cozinhar")
        val a = receitas.cadastrar(r)
        val b = receitas.cadastrar(r)
        assertNotEquals(a, b)
        receitas.alterar(r.copy(id = a, nome = "Sopa alterada"))
        assertEquals("Sopa", ReceitaDAO(contexto).buscarPorId(b)?.nome)
        assertEquals("Sopa alterada", ReceitaDAO(contexto).buscarPorId(a)?.nome)
        receitas.excluir(a)
        assertNull(receitas.buscarPorId(a))
        assertNotNull(receitas.buscarPorId(b))
        assertEquals(7, receitas.listar().size)
        val compras = ItemCompraDAO(contexto)
        val id = compras.cadastrar(ItemCompra(nome = "Farinha", quantidade = "1 kg"))
        compras.alterar(ItemCompra(id, "Farinha", "2 kg"))
        assertEquals("2 kg", ItemCompraDAO(contexto).buscarPorId(id)?.quantidade)
        assertEquals(1, compras.listar().size)
        compras.excluir(id)
        assertTrue(ItemCompraDAO(contexto).listar().isEmpty())
    }
    @Test fun contaPersistidaRejeitaDuplicidadeESenhaErrada() {
        val salt = Autenticacao.criarSalt()
        val hash = Autenticacao.gerarHash("senha123", salt)
        val id = UsuarioDAO(contexto).cadastrar("Pessoa", "pessoa@exemplo.com", hash, salt)
        val dados = requireNotNull(UsuarioDAO(contexto).buscarSenha("pessoa@exemplo.com"))
        assertEquals(id, dados.usuario.id)
        assertTrue(Autenticacao.validarSenha("senha123", dados.salt, dados.hash))
        assertFalse(Autenticacao.validarSenha("errada123", dados.salt, dados.hash))
        assertThrows(android.database.sqlite.SQLiteConstraintException::class.java) {
            UsuarioDAO(contexto).cadastrar("Outra", "pessoa@exemplo.com", hash, salt)
        }
    }
    @Test fun idsInexistentesNaoAlteramOutrosRegistros() {
        assertThrows(IllegalArgumentException::class.java) { ReceitaDAO(contexto).excluir(9999) }
        assertThrows(IllegalArgumentException::class.java) { ItemCompraDAO(contexto).alterar(ItemCompra(9999, "Leite", "1 L")) }
        assertEquals(6, ReceitaDAO(contexto).listar().size)
    }
}
