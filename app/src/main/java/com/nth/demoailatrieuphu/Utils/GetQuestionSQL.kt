package com.nth.demoailatrieuphu.Utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Environment
import android.util.Log
import com.nth.demoailatrieuphu.model.ItemQuestion
import java.io.File
import java.io.FileOutputStream

class GetQuestionSQL(private val context: Context) {
    init {
        copyDataBase()
    }
    private lateinit var questions: ItemQuestion
    private var database: SQLiteDatabase? = null
    fun copyDataBase() {
        var path = Environment.getDataDirectory().path + "/data" + "/" +
                context.packageName + "/db"
        File(path).mkdirs()
        path += "/ailatriphu"
        if (File(path).exists()) {
            return
        }
        val accetManager = context.assets
        val input = accetManager.open("latrieuphu")
        val out = FileOutputStream(path)
        val b = ByteArray(1024)
        var le = input.read(b)
        while (le > -1) {
            out.write(b, 0, le)
            le = input.read(b)
        }
        input.close()
        out.close()
    }
    private fun openDatabase() {
        var path = Environment.getDataDirectory().path + "/data" + "/" +
                context.packageName + "/db"
        path += "/ailatriphu"
        database = SQLiteDatabase.openDatabase(
            path, null,
            SQLiteDatabase.OPEN_READWRITE
        )
    }
    private fun closeDatabase() {
        if (database != null && database!!.isOpen) {
            database!!.close()
            database = null
        }
    }

    fun query15Question() :ArrayList<ItemQuestion>{
        var listQuestion:ArrayList<ItemQuestion> = arrayListOf()
        openDatabase()
        val sql = "select * from (select * from Question order by RANDOM()) as que " +
                "group by level order by level asc"
        val c = database!!.rawQuery(sql, null)
        c.moveToFirst()
        val indexQuestion = c.getColumnIndex("question")
        val indexLevel = c.getColumnIndex("level")
        val indexCaseA = c.getColumnIndex("casea")
        val indexCaseB = c.getColumnIndex("caseb")
        val indexCaseC = c.getColumnIndex("casec")
        val indexCaseD = c.getColumnIndex("cased")
        val indexTrueCase = c.getColumnIndex("truecase")
        while (!c.isAfterLast){
            val level = c.getInt(indexLevel)
            val question = c.getString(indexQuestion)
            val caseA = c.getString(indexCaseA)
            val caseB = c.getString(indexCaseB)
            val caseC = c.getString(indexCaseC)
            val caseD = c.getString(indexCaseD)
            val trueCase = c.getString(indexTrueCase)
            listQuestion.add(ItemQuestion(question,level,caseA,caseB,caseC,caseD,trueCase))
            c.moveToNext()
        }
        c.close()
        closeDatabase()
        return listQuestion
    }

    fun changeTheQuestion(level:Int):ItemQuestion{
        openDatabase()
        val sql = "select * from (select * from Question where level="+level+" order by RANDOM()) as que " +
                "group by level order by level asc"
        val c = database!!.rawQuery(sql, null)
        c.moveToFirst()
        val indexQuestion = c.getColumnIndex("question")
        val indexLevel = c.getColumnIndex("level")
        val indexCaseA = c.getColumnIndex("casea")
        val indexCaseB = c.getColumnIndex("caseb")
        val indexCaseC = c.getColumnIndex("casec")
        val indexCaseD = c.getColumnIndex("cased")
        val indexTrueCase = c.getColumnIndex("truecase")
        while (!c.isAfterLast){
            val level = c.getInt(indexLevel)
            val question = c.getString(indexQuestion)
            val caseA = c.getString(indexCaseA)
            val caseB = c.getString(indexCaseB)
            val caseC = c.getString(indexCaseC)
            val caseD = c.getString(indexCaseD)
            val trueCase = c.getString(indexTrueCase)
            questions = ItemQuestion(question,level,caseA,caseB,caseC,caseD,trueCase)
            c.moveToNext()
        }
        c.close()
        closeDatabase()
        return questions

    }
}