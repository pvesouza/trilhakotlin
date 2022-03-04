package br.com.example.pedro.contentproviderexample.ui

import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns._ID
import android.util.Log
import android.widget.Toast
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.example.pedro.contentproviderexample.R
import br.com.example.pedro.contentproviderexample.db.MyContentProvider.Companion.URI_NOTES
import br.com.example.pedro.contentproviderexample.db.NotesDb
import br.com.example.pedro.contentproviderexample.db.NotesDb.Companion.COLUMN_TITLE
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor>{

    private lateinit var recyclerView: RecyclerView
    private lateinit var buttonAdd:FloatingActionButton
    private lateinit var adapter:MYAdapter

    companion object{
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "on create")
        setContentView(R.layout.activity_main)

        initViews()             // Init views from layout
        setListeners()          // Sets all listeners needed of this layout
    }

    // Method to access the content provider without break the ui thread

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
//        Instantiates what will be search on content proveder
        val cursorLoader = CursorLoader(this, URI_NOTES, null,
            null, null, COLUMN_TITLE)
        return cursorLoader
    }

//    Fetch the received data from the content provider
    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
//        test if data in not null
        if (data != null) {
            Log.d(TAG, "Load")
            adapter.setCursor(data)
        }
    }
//    stops the backend research os the load manager
    override fun onLoaderReset(loader: Loader<Cursor>) {
        adapter.setCursor(null)
    }

    //-------------------------------------------------------------------------

    // Initializes the views
    private fun initViews() {
        recyclerView = findViewById(R.id.recycler_notes)
        buttonAdd = findViewById(R.id.button_add_note)
        iniRecyclerView()
    }
//  Initiates the recycler view with adapter
    private fun iniRecyclerView() {
        val noteListener = NoteListener()
        adapter = MYAdapter(noteListener)
        adapter.setHasStableIds(true)                   // For not havind double ids
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        LoaderManager.getInstance(this).initLoader(0, null, this)
    }

    // Initioalizes all listeners from the main activity
    private fun setListeners() {
        // Button Add new note activity
        buttonAdd.setOnClickListener {
            val dialog = NotesDetailFragment()
            dialog.show(supportFragmentManager, "customDialog")
        }
    }

    inner class NoteListener: NoteClickedListener{

        override fun noteClickItem(cursor: Cursor) {
            val idColumnIndex = cursor.getColumnIndex(_ID)
            val id:Long = cursor.getLong(idColumnIndex)
            val fragment = NotesDetailFragment.newInstance(id)
            fragment.show(supportFragmentManager, "dialog")
        }

        override fun noteRemoveItem(cursor: Cursor) {
            val idColumnIndex = cursor.getColumnIndex(_ID)
            val id: Long = cursor.getLong(idColumnIndex)
            contentResolver.delete(Uri.withAppendedPath(URI_NOTES, id.toString()), null, null)
        }
    }
}