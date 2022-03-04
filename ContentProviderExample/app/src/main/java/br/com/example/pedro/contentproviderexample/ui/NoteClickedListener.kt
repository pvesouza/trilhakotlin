package br.com.example.pedro.contentproviderexample.ui

import android.database.Cursor

interface NoteClickedListener {
    abstract fun noteClickItem(cursor: Cursor)
    abstract fun noteRemoveItem(cursor: Cursor)
}