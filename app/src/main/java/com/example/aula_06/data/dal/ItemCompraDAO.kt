package com.example.aula_06.data.dal

import android.content.ContentValues
import android.content.Context
import com.example.aula_06.data.local.AppDatabase
import com.example.aula_06.model.ItemCompra

class ItemCompraDAO(context: Context) {
    private val appContext = context.applicationContext

    fun cadastrar(item: ItemCompra): Long = AppDatabase(appContext).use { db ->
        val dados = ContentValues().apply {
            put("nome", item.nome)
            put("quantidade", item.quantidade)
        }
        db.writableDatabase.insertOrThrow("itens_compra", null, dados)
    }

    fun listar(): List<ItemCompra> = AppDatabase(appContext).use { db ->
        db.readableDatabase.query(
            "itens_compra", arrayOf("id", "nome", "quantidade"),
            null, null, null, null, "id"
        ).use { cursor ->
            buildList {
                while (cursor.moveToNext()) {
                    add(ItemCompra(
                        cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getString(2)
                    ))
                }
            }
        }
    }

    fun buscarPorId(id: Long): ItemCompra? = AppDatabase(appContext).use { db ->
        db.readableDatabase.query(
            "itens_compra", arrayOf("id", "nome", "quantidade"),
            "id = ?", arrayOf(id.toString()), null, null, null
        ).use { cursor ->
            if (cursor.moveToFirst()) ItemCompra(
                cursor.getLong(0),
                cursor.getString(1),
                cursor.getString(2)
            ) else null
        }
    }

    fun alterar(item: ItemCompra) {
        AppDatabase(appContext).use { db ->
            val valores = ContentValues().apply {
                put("nome", item.nome)
                put("quantidade", item.quantidade)
            }
            val alterado = db.writableDatabase.update(
                "itens_compra", valores, "id = ?", arrayOf(item.id.toString())
            )
            require(alterado == 1) { "Item nao encontrado. Atualize a lista." }
        }
    }

    fun excluir(id: Long) {
        AppDatabase(appContext).use { db ->
            val excluido = db.writableDatabase.delete(
                "itens_compra", "id = ?", arrayOf(id.toString())
            )
            require(excluido == 1) { "Item nao encontrado. Atualize a lista." }
        }
    }
}
