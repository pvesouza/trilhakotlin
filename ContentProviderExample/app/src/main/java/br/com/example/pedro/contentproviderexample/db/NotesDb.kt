package br.com.example.pedro.contentproviderexample.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns._ID

class NotesDb(private val context: Context): SQLiteOpenHelper(context, "notes.db", null, 1) {

    companion object{
        const val TABLE_NOTES = "Notes"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DESCRIPTION = "description"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val sqlExec = "CREATE TABLE $TABLE_NOTES(" +
                "$_ID INTEGER PRIMARY KEY NOT NULL," +
                "$COLUMN_TITLE TEXT NOT NULL," +
                "$COLUMN_DESCRIPTION TEXT NOT NULL)"

        db?.execSQL(sqlExec)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

}