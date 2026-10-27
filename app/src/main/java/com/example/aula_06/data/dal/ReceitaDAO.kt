package com.example.aula_06.data.dal

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.aula_06.data.local.AppDatabase
import com.example.aula_06.model.Receita

class ReceitaDAO(context: Context) {
    private val appContext = context.applicationContext
    private val colunas = arrayOf("id", "nome", "tempo", "porcoes", "dificuldade", "ingredientes", "modo_preparo", "imagem")
    private fun valores(receita: Receita) = ContentValues().apply {
        put("nome", receita.nome); put("tempo", receita.tempo); put("porcoes", receita.porcoes)
        put("dificuldade", receita.dificuldade); put("ingredientes", receita.ingredientes)
        put("modo_preparo", receita.modoPreparo); put("imagem", receita.imagem)
    }
    private fun ler(cursor: Cursor) = Receita(cursor.getLong(0), cursor.getString(1),
        cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5),
        cursor.getString(6), if (cursor.isNull(7)) null else cursor.getString(7))
    fun cadastrar(receita: Receita): Long = AppDatabase(appContext).use {
        it.writableDatabase.insertOrThrow("receitas", null, valores(receita))
    }
    fun listar(): List<Receita> = AppDatabase(appContext).use {
        it.readableDatabase.query("receitas", colunas, null, null, null, null, "id").use { cursor ->
            buildList { while (cursor.moveToNext()) add(ler(cursor)) }
        }
    }
    fun buscarPorId(id: Long): Receita? = AppDatabase(appContext).use {
        it.readableDatabase.query("receitas", colunas, "id = ?", arrayOf(id.toString()),
            null, null, null).use { cursor -> if (cursor.moveToFirst()) ler(cursor) else null }
    }
    fun alterar(receita: Receita) = AppDatabase(appContext).use {
        val total = it.writableDatabase.update("receitas", valores(receita), "id = ?", arrayOf(receita.id.toString()))
        require(total == 1) { "Receita nao encontrada. Atualize a lista." }
    }
    fun excluir(id: Long) = AppDatabase(appContext).use {
        val total = it.writableDatabase.delete("receitas", "id = ?", arrayOf(id.toString()))
        require(total == 1) { "Receita nao encontrada. Atualize a lista." }
    }
}
