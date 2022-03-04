package br.com.example.pedro.contentproviderexample.db

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.media.UnsupportedSchemeException
import android.net.Uri
import android.provider.BaseColumns._ID
import br.com.example.pedro.contentproviderexample.R
import br.com.example.pedro.contentproviderexample.db.NotesDb.Companion.TABLE_NOTES
import java.net.URI

class MyContentProvider : ContentProvider() {

    private lateinit var uriMatcher: UriMatcher         // Used to validate the requisition of a content provider
    private lateinit var dbHelper:NotesDb

    companion object {
        // Receives the same valus of the const in manifest.xml and it is used for access the provider
        const val AUTHORITIES = "br.com.example.pedro.contentproviderexample.db.provider"
        // Becomes an URI from a string
        val BASE_URI = Uri.parse("content://${AUTHORITIES}")
        // Defines an Uri to get notes
        val URI_NOTES = Uri.withAppendedPath(BASE_URI, "notes")
        const val NOTES = 1
        const val NOTES_BY_ID = 2
    }

    // Instatiates all the content provider
    override fun onCreate(): Boolean {
        // Instantiate a Uri Matcher
        uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        uriMatcher.addURI(AUTHORITIES, "notes", NOTES)
        uriMatcher.addURI(AUTHORITIES, "notes/#", NOTES_BY_ID)

        if (context != null){
            this.dbHelper = NotesDb(context as Context)
        }
        return true

    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        // Tests the Uri
        if (this.uriMatcher.match(uri) == NOTES_BY_ID) {
            // Creates a database and deletes a note by ID
            val db: SQLiteDatabase = dbHelper.writableDatabase
            val linesAffected: Int = db.delete(TABLE_NOTES, "$_ID =?", arrayOf(uri.lastPathSegment))
            db.close()
            context?.contentResolver?.notifyChange(uri, null)
            return linesAffected
        }else {
            throw UnsupportedSchemeException(context?.getString(R.string.label_not_valid_requisition))
        }
    }

    // Used for arquives manipulation
    override fun getType(uri: Uri): String? {
        TODO(
            "Implement this to handle requests for the MIME type of the data" +
                    "at the given URI"
        )
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        if (this.uriMatcher.match(uri) == NOTES) {
            val db: SQLiteDatabase = dbHelper.writableDatabase
            val id: Long = db.insert(TABLE_NOTES, null, values)
            val insertUri = Uri.withAppendedPath(BASE_URI, id.toString())
            db.close()
            context?.contentResolver?.notifyChange(uri, null)
            return insertUri
        }else {
            throw UnsupportedSchemeException(context?.getString(R.string.label_object_not_inserted))
        }
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when {
            // First requisition
            uriMatcher.match(uri) == NOTES ->{
                val db: SQLiteDatabase = dbHelper.writableDatabase
                val cursor = db.query(TABLE_NOTES, projection, selection, selectionArgs, null, null, sortOrder)
                cursor.setNotificationUri(context?.contentResolver, uri)
                cursor
            }
            // Second requisition
            uriMatcher.match(uri) == NOTES_BY_ID ->{
                val db: SQLiteDatabase = dbHelper.writableDatabase
                val cursor = db.query(TABLE_NOTES, projection, "$_ID = ?", arrayOf(uri.lastPathSegment), null, null, sortOrder)
                cursor.setNotificationUri(context?.contentResolver, uri)
                cursor
            }
            else ->{
                throw UnsupportedSchemeException(context?.getString(R.string.label_uri_not_implemented))
            }

        }
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        if (uriMatcher.match(uri) == NOTES_BY_ID) {
            val db: SQLiteDatabase = dbHelper.writableDatabase
            val updateId = db.update(TABLE_NOTES, values, "$_ID = ?", arrayOf(uri.lastPathSegment))
            context?.contentResolver?.notifyChange(uri, null)
            db.close()
            return updateId
        }else {
            throw UnsupportedSchemeException(context?.getString(R.string.label_uri_not_implemented))
        }
    }
}