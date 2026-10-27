package com.example.aula_06.data.local

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.File

class AppDatabase(context: Context) : SQLiteOpenHelper(
    context.applicationContext,
    File(context.noBackupFilesDir, "receitas_diarias.db").absolutePath,
    null, 1
) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE usuarios (
                id INTEGER PRIMARY KEY,
                nome TEXT NOT NULL CHECK(length(nome) BETWEEN 3 AND 100),
                email TEXT NOT NULL UNIQUE CHECK(length(email) BETWEEN 3 AND 254),
                senha BLOB NOT NULL,
                salt BLOB NOT NULL
            )
        """.trimIndent())
        db.execSQL("""
            CREATE TABLE receitas (
                id INTEGER PRIMARY KEY,
                nome TEXT NOT NULL CHECK(length(trim(nome)) > 0),
                tempo TEXT NOT NULL,
                porcoes TEXT NOT NULL,
                dificuldade TEXT NOT NULL,
                ingredientes TEXT NOT NULL,
                modo_preparo TEXT NOT NULL,
                imagem TEXT
            )
        """.trimIndent())
        db.execSQL("""
            CREATE TABLE itens_compra (
                id INTEGER PRIMARY KEY,
                nome TEXT NOT NULL CHECK(length(trim(nome)) > 0),
                quantidade TEXT NOT NULL CHECK(length(trim(quantidade)) > 0)
            )
        """.trimIndent())
        // Preserva o catalogo da Home apenas na primeira criacao do banco.
        val iniciais = listOf(
            "Espaguete a Bolonhesa" to "espaguete",
            "Tarta de Morango" to "tarta_morango",
            "Sopa de Abobora Cremosa" to "sopa_abobora",
            "Salada de Verao" to "salada_verao",
            "Mini Hamburgueres de Festa" to "mini_hamburgueres",
            "Suco de Laranja Fresco" to "suco_laranja"
        )
        iniciais.forEach { (nome, imagem) ->
            db.insertOrThrow("receitas", null, ContentValues().apply {
                put("nome", nome); put("imagem", imagem)
                put("tempo", ""); put("porcoes", ""); put("dificuldade", "")
                put("ingredientes", ""); put("modo_preparo", "")
            })
        }
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Primeira versao. Futura alteracao exige migracao explicita, sem apagar dados.
        error("Migracao de $oldVersion para $newVersion ainda nao definida.")
    }
}
