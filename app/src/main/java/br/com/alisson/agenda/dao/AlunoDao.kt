package br.com.alisson.agenda.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.com.alisson.agenda.modelo.Aluno
import java.util.*
import kotlin.collections.ArrayList

class AlunoDao(context: Context) :
        SQLiteOpenHelper(context, "agenda", null, 4) {

    override fun onCreate(db: SQLiteDatabase?) {

        val sql = "CREATE TABLE Alunos (" +
                "id CHAR(36) PRIMARY KEY, " +
                "nome TEXT NOT NULL, " +
                "endereco TEXT, " +
                "telefone TEXT, " +
                "site TEXT, " +
                "nota REAL, " +
                "caminhoFoto TEXT" +
                ");"

        db?.execSQL(sql)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        var sql = ""
        if (oldVersion <= 1) {
            sql = "Alter table Alunos add column caminhoFoto TEXT;"
            db?.execSQL(sql)
        }
        if (oldVersion <= 2) {
            val sql = "CREATE TABLE Alunos_novo (" +
                    "id CHAR(36) PRIMARY KEY, " +
                    "nome TEXT NOT NULL, " +
                    "endereco TEXT, " +
                    "telefone TEXT, " +
                    "site TEXT, " +
                    "nota REAL, " +
                    "caminhoFoto TEXT" +
                    ");"
            db?.execSQL(sql)

            val insert = "INSERT INTO Alunos_novo " +
                    "(id, nome, endereco, telefone, site, nota, caminhoFoto) " +
                    "SELECT id, nome, endereco, telefone, site, nota, caminhoFoto " +
                    "FROM Alunos;"
            db?.execSQL(insert)

            val drop = "DROP TABLE Alunos"
            db?.execSQL(drop)

            val alter = "ALTER TABLE Alunos_novo " +
                    "RENAME TO Alunos;"
            db?.execSQL(alter)
        }
        if (oldVersion <= 3) {
            sql = "SELECT * FROM Alunos;"
            val cursor = db?.rawQuery(sql, null)
            val alunos = populaAlunos(cursor)

            val update = "UPDATE Alunos SET id = ? WHERE id = ?"

            for (aluno in alunos){
                db?.execSQL(update, arrayOf(geraUUID(), aluno.id))
            }
        }
    }

    private fun geraUUID(): String {
        return UUID.randomUUID().toString()
    }

    private fun populaAlunos(cursor: Cursor?): ArrayList<Aluno> {
        val alunos = ArrayList<Aluno>()
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val aluno = Aluno()
                aluno.id = cursor.getString(cursor.getColumnIndexOrThrow("id"))
                aluno.caminhoFoto = cursor.getString(cursor.getColumnIndexOrThrow("caminhoFoto"))
                aluno.endereco = cursor.getString(cursor.getColumnIndexOrThrow("endereco"))
                aluno.nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"))
                aluno.nota = cursor.getDouble(cursor.getColumnIndexOrThrow("nota"))
                aluno.site = cursor.getString(cursor.getColumnIndexOrThrow("site"))
                aluno.telefone = cursor.getString(cursor.getColumnIndexOrThrow("telefone"))

                alunos.add(aluno)
            }
            cursor.close()
        }
        return alunos
    }

    fun insere(aluno: Aluno) {
        val db = writableDatabase
        if (aluno.id == ""){
            aluno.id = UUID.randomUUID().toString()
        }
        db.insert("Alunos", null, pegaDadosDoAluno(aluno))
    }

    fun sincroniza(alunos: List<Aluno>) {
        val db = writableDatabase
        for (aluno in alunos){
            if (existe(aluno)){
                edita(aluno)
            }else{
                insere(aluno)
            }
        }
    }

    private fun existe(aluno: Aluno): Boolean {
        val db = readableDatabase
        val sql = "SELECT id FROM Alunos WHERE id = ? LIMIT 1"
        val cursor = db?.rawQuery(sql, arrayOf(aluno.id))
        if (cursor != null){
            val qnt = cursor.count
            cursor.close()

            return qnt > 0
        }

        return false
    }

    private fun pegaDadosDoAluno(aluno: Aluno): ContentValues {
        val cv = ContentValues()
        cv.put("id", aluno.id)
        cv.put("nome", aluno.nome)
        cv.put("endereco", aluno.endereco)
        cv.put("telefone", aluno.telefone)
        cv.put("site", aluno.site)
        cv.put("nota", aluno.nota)
        cv.put("caminhoFoto", aluno.caminhoFoto)
        return cv
    }

    fun buscaAlunos(): ArrayList<Aluno> {
        val sql = "SELECT * FROM Alunos"
        val db = readableDatabase
        val cursor = db.rawQuery(sql, null)

        return populaAlunos(cursor)
    }

    fun deleta(aluno: Aluno) {

        val db = writableDatabase

        db.delete("Alunos", "id = ?", arrayOf(aluno.id.toString()))

    }

    fun edita(aluno: Aluno) {
        val db = writableDatabase

        db.update("Alunos", pegaDadosDoAluno(aluno), "id = ?", arrayOf(aluno.id.toString()))
    }

    fun ehAluno(telefone: String): Boolean {

        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM alunos WHERE telefone = ?", arrayOf(telefone))
        val ehAluno = cursor.count > 0
        cursor.close()
        return ehAluno
    }
}