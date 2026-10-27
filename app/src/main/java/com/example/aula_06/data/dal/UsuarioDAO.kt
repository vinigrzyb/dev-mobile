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

    fun listarUsuarios(): List<Usuario> = AppDatabase(appContext).use { db ->
        db.readableDatabase.query(
            "usuarios", arrayOf("id", "nome", "email"),
            null, null, null, null, "nome COLLATE NOCASE, id"
        ).use { cursor ->
            buildList {
                while (cursor.moveToNext()) {
                    add(Usuario(cursor.getLong(0), cursor.getString(1), cursor.getString(2)))
                }
            }
        }
    }

    fun buscarUsuarioPorId(id: Long): Usuario? = AppDatabase(appContext).use { db ->
        db.readableDatabase.query(
            "usuarios", arrayOf("id", "nome", "email"),
            "id = ?", arrayOf(id.toString()), null, null, null
        ).use { cursor ->
            if (cursor.moveToFirst()) Usuario(
                cursor.getLong(0), cursor.getString(1), cursor.getString(2)
            ) else null
        }
    }

    fun atualizarUsuario(id: Long, nome: String, hash: ByteArray? = null, salt: ByteArray? = null) {
        AppDatabase(appContext).use { db ->
            val valores = ContentValues().apply {
                put("nome", nome)
                if (hash != null) put("senha", hash)
                if (salt != null) put("salt", salt)
            }
            val alterado = db.writableDatabase.update(
                "usuarios", valores, "id = ?", arrayOf(id.toString())
            )
            require(alterado == 1) { "Usuario nao encontrado. Atualize a lista." }
        }
    }

    fun excluirUsuario(id: Long, usuarioAtualId: Long) {
        require(usuarioAtualId > 0) { "Faca login para continuar." }
        require(id != usuarioAtualId) { "Voce nao pode excluir o seu usuario." }
        AppDatabase(appContext).use { db ->
            val excluido = db.writableDatabase.delete(
                "usuarios", "id = ? AND id <> ?",
                arrayOf(id.toString(), usuarioAtualId.toString())
            )
            require(excluido == 1) { "Usuario nao encontrado. Atualize a lista." }
        }
    }
}
