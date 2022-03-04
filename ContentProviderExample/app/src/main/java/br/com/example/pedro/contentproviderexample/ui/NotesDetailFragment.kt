package br.com.example.pedro.contentproviderexample.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentValues
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import br.com.example.pedro.contentproviderexample.R
import br.com.example.pedro.contentproviderexample.db.MyContentProvider.Companion.URI_NOTES
import br.com.example.pedro.contentproviderexample.db.NotesDb.Companion.COLUMN_DESCRIPTION
import br.com.example.pedro.contentproviderexample.db.NotesDb.Companion.COLUMN_TITLE

class NotesDetailFragment : DialogFragment(), DialogInterface.OnClickListener{

    private lateinit var noteEditTitle: EditText
    private lateinit var noteEditDescription:EditText
    private var id: Long = 0

    companion object {

        private const val EXTRA_ID = "id"
        fun newInstance(id: Long) : NotesDetailFragment {
            val bundle = Bundle()
            bundle.putLong(EXTRA_ID, id)

            val notesFragment = NotesDetailFragment()
            notesFragment.arguments = bundle
            return notesFragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view: View? = activity?.layoutInflater?.inflate(R.layout.note_detail, null)

        this.noteEditTitle = view?.findViewById(R.id.ed_title_note_detail) as EditText
        this.noteEditDescription = view?.findViewById(R.id.ed_description_note_detail) as EditText

        var newNote = true

        if (arguments != null){
            val id = arguments?.getLong(EXTRA_ID)
            if (id != 0L) {
                this.id = id as Long
                val uri:Uri = Uri.withAppendedPath(URI_NOTES, this.id.toString())
                val cursor = activity?.contentResolver?.query(
                    uri,
                    null,
                    null,
                    null,
                    null
                )

                if (cursor != null) {
                    if (cursor.moveToNext()) {
                        newNote = false
                        val columnTitleId = cursor.getColumnIndex(COLUMN_TITLE)
                        val columnDescId = cursor.getColumnIndex(COLUMN_DESCRIPTION)
                        val title = cursor.getString(columnTitleId)
                        val desc = cursor.getString(columnDescId)
                        this.noteEditTitle.setText(title)
                        this.noteEditDescription.setText(desc)
                    }
                    cursor.close()
                }
            }
        }
        return AlertDialog.Builder(activity).
                setTitle(if (newNote) "New Note" else "Edit Note").
                setView(view).
                setPositiveButton(R.string.frag_pos_button_label, this).
                setNegativeButton(R.string.frag_neg_button_label, this).
                create()
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        val values = ContentValues()
        values.put(COLUMN_TITLE, this.noteEditTitle.text.toString())
        values.put(COLUMN_DESCRIPTION, this.noteEditDescription.text.toString())

        if (this.id != 0L) {
            val uri:Uri = Uri.withAppendedPath(URI_NOTES, this.id.toString())
            context?.contentResolver?.update(uri, values, null, null)
        } else {
            context?.contentResolver?.insert(URI_NOTES, values)
        }
    }
}