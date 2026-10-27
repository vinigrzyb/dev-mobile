package com.example.aula_06.data.dal

import android.content.ContentValues
import android.content.Context
import com.example.aula_06.data.local.AppDatabase
import com.example.aula_06.model.Receita

class ReceitaDAO(context: Context) {
    private val appContext = context.applicationContext

    fun cadastrar(receita: Receita): Long = AppDatabase(appContext).use { db ->
        val dados = ContentValues().apply {
            put("nome", receita.nome)
            put("tempo", receita.tempo)
            put("porcoes", receita.porcoes)
            put("dificuldade", receita.dificuldade)
            put("ingredientes", receita.ingredientes)
            put("modo_preparo", receita.modoPreparo)
            put("imagem", receita.imagem)
        }
        db.writableDatabase.insertOrThrow("receitas", null, dados)
    }

    fun listar(): List<Receita> = AppDatabase(appContext).use { db ->
        db.readableDatabase.query(
            "receitas", arrayOf("id", "nome", "tempo", "porcoes", "dificuldade", "ingredientes", "modo_preparo", "imagem"),
            null, null, null, null, "id"
        ).use { cursor ->
            buildList {
                while (cursor.moveToNext()) {
                    add(Receita(
                        cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        if (cursor.isNull(7)) null else cursor.getString(7)
                    ))
                }
            }
        }
    }

    fun buscarPorId(id: Long): Receita? = AppDatabase(appContext).use { db ->
        db.readableDatabase.query(
            "receitas", arrayOf("id", "nome", "tempo", "porcoes", "dificuldade", "ingredientes", "modo_preparo", "imagem"),
            "id = ?", arrayOf(id.toString()), null, null, null
        ).use { cursor ->
            if (cursor.moveToFirst()) Receita(
                cursor.getLong(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                if (cursor.isNull(7)) null else cursor.getString(7)
            ) else null
        }
    }

    fun alterar(receita: Receita) {
        AppDatabase(appContext).use { db ->
            val valores = ContentValues().apply {
                put("nome", receita.nome)
                put("tempo", receita.tempo)
                put("porcoes", receita.porcoes)
                put("dificuldade", receita.dificuldade)
                put("ingredientes", receita.ingredientes)
                put("modo_preparo", receita.modoPreparo)
                put("imagem", receita.imagem)
            }
            val alterado = db.writableDatabase.update(
                "receitas", valores, "id = ?", arrayOf(receita.id.toString())
            )
            require(alterado == 1) { "Receita nao encontrada. Atualize a lista." }
        }
    }

    fun excluir(id: Long) {
        AppDatabase(appContext).use { db ->
            val excluido = db.writableDatabase.delete(
                "receitas", "id = ?", arrayOf(id.toString())
            )
            require(excluido == 1) { "Receita nao encontrada. Atualize a lista." }
        }
    }
}
