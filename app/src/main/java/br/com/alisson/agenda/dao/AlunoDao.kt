package br.com.alisson.agenda.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.com.alisson.agenda.modelo.Aluno

class AlunoDao(context: Context) :
    SQLiteOpenHelper(context, "agenda", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {

        val sql = "CREATE TABLE Alunos (" +
                "id INTEGER PRIMARY KEY, " +
                "nome TEXT NOT NULL, " +
                "endereco TEXT, " +
                "telefone TEXT, " +
                "site TEXT, " +
                "nota REAL " +
                ");"

        db?.execSQL(sql)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val sql = "DROP TABLE IF EXISTS Aluno"
        db?.execSQL(sql)
        onCreate(db)
    }

    fun insere(aluno: Aluno) {
        val db = writableDatabase

        db.insert("Alunos", null, pegaDadosDoAluno(aluno))
    }

    private fun pegaDadosDoAluno(aluno: Aluno): ContentValues {
        val cv = ContentValues()
        cv.put("nome", aluno.nome)
        cv.put("endereco", aluno.endereco)
        cv.put("telefone", aluno.telefone)
        cv.put("site", aluno.site)
        cv.put("nota", aluno.nota)
        return cv
    }

    fun buscaAlunos(): ArrayList<Aluno> {
        val sql = "SELECT * FROM Alunos"
        val db = readableDatabase
        val cursor = db.rawQuery(sql, null)
        val alunos = ArrayList<Aluno>()

        while (cursor.moveToNext()) {
            val aluno = Aluno()
            aluno.id = cursor.getLong(cursor.getColumnIndexOrThrow("id"))
            aluno.nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"))
            aluno.endereco = cursor.getString(cursor.getColumnIndexOrThrow("endereco"))
            aluno.telefone = cursor.getString(cursor.getColumnIndexOrThrow("telefone"))
            aluno.site = cursor.getString(cursor.getColumnIndexOrThrow("site"))
            aluno.nota = cursor.getDouble(cursor.getColumnIndexOrThrow("nota"))

            alunos.add(aluno)
        }

        cursor.close()

        return alunos
    }

    fun deleta(aluno: Aluno) {

        val db = writableDatabase

        db.delete("Alunos", "id = ?", arrayOf(aluno.id.toString()))

    }

    fun edita(aluno: Aluno) {
        val db = writableDatabase

        db.update("Alunos", pegaDadosDoAluno(aluno),"id = ?", arrayOf(aluno.id.toString()))
    }
}