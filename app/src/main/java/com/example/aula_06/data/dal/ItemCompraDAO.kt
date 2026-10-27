package com.example.aula_06.data.dal

import android.content.ContentValues
import android.content.Context
import com.example.aula_06.data.local.AppDatabase
import com.example.aula_06.model.ItemCompra

class ItemCompraDAO(context: Context) {
    private val appContext = context.applicationContext
    private fun valores(item: ItemCompra) = ContentValues().apply {
        put("nome", item.nome); put("quantidade", item.quantidade)
    }
    fun cadastrar(item: ItemCompra): Long = AppDatabase(appContext).use {
        it.writableDatabase.insertOrThrow("itens_compra", null, valores(item))
    }
    fun listar(): List<ItemCompra> = AppDatabase(appContext).use {
        it.readableDatabase.query("itens_compra", arrayOf("id", "nome", "quantidade"),
            null, null, null, null, "id").use { cursor ->
            buildList { while (cursor.moveToNext()) add(ItemCompra(cursor.getLong(0), cursor.getString(1), cursor.getString(2))) }
        }
    }
    fun buscarPorId(id: Long): ItemCompra? = AppDatabase(appContext).use {
        it.readableDatabase.query("itens_compra", arrayOf("id", "nome", "quantidade"),
            "id = ?", arrayOf(id.toString()), null, null, null).use { cursor ->
            if (cursor.moveToFirst()) ItemCompra(cursor.getLong(0), cursor.getString(1), cursor.getString(2)) else null
        }
    }
    fun alterar(item: ItemCompra) = AppDatabase(appContext).use {
        val total = it.writableDatabase.update("itens_compra", valores(item), "id = ?", arrayOf(item.id.toString()))
        require(total == 1) { "Item nao encontrado. Atualize a lista." }
    }
    fun excluir(id: Long) = AppDatabase(appContext).use {
        val total = it.writableDatabase.delete("itens_compra", "id = ?", arrayOf(id.toString()))
        require(total == 1) { "Item nao encontrado. Atualize a lista." }
    }
}
