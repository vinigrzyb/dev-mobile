package com.example.aula_06.data.dal

import android.content.ContentValues
import android.content.Context
import com.example.aula_06.data.local.AppDatabase
import com.example.aula_06.model.Usuario

data class Credenciais(val usuario: Usuario, val hash: ByteArray, val salt: ByteArray)

class UsuarioDAO(context: Context) {
    private val appContext = context.applicationContext
    fun cadastrar(nome: String, email: String, hash: ByteArray, salt: ByteArray): Long =
        AppDatabase(appContext).use { helper ->
            helper.writableDatabase.insertOrThrow("usuarios", null, ContentValues().apply {
                put("nome", nome); put("email", email); put("senha", hash); put("salt", salt)
            })
        }
    fun buscarSenha(email: String): Credenciais? = AppDatabase(appContext).use { helper ->
        helper.readableDatabase.query("usuarios", arrayOf("id", "nome", "email", "senha", "salt"),
            "email = ?", arrayOf(email), null, null, null).use { cursor ->
            if (cursor.moveToFirst()) Credenciais(
                Usuario(cursor.getLong(0), cursor.getString(1), cursor.getString(2)),
                cursor.getBlob(3), cursor.getBlob(4)
            ) else null
        }
    }
}
