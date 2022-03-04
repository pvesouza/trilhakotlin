package br.com.example.pedro.contentproviderexample.ui

import android.database.Cursor
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.example.pedro.contentproviderexample.Note
import br.com.example.pedro.contentproviderexample.R
import br.com.example.pedro.contentproviderexample.db.NotesDb.Companion.COLUMN_DESCRIPTION
import br.com.example.pedro.contentproviderexample.db.NotesDb.Companion.COLUMN_TITLE

// From the list I will create the view and pass it to viewHolder

class MYAdapter(val listener: NoteClickedListener) : RecyclerView.Adapter<MyViewHolder>() {

    private var myCursor: Cursor? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutView =
            LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return MyViewHolder(layoutView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val note: Note = getNoteFromDb(position)
        holder.bindView(note)

        holder.bindListener({
            excludeNote(position)
        })

        holder.itemView.setOnClickListener {
            listener.noteClickItem(myCursor as Cursor)
        }
    }

    // Get an object Note
    private fun getNoteFromDb(position: Int): Note {
        myCursor?.moveToPosition(position)
        val title: String? = myCursor?.getString(myCursor?.getColumnIndex(COLUMN_TITLE) as Int)
        val description: String? = myCursor?.getString(myCursor?.getColumnIndex(COLUMN_DESCRIPTION) as Int)
        return Note(title, description)
    }

    // Listener from activity
    private fun excludeNote(position: Int){
        myCursor?.moveToPosition(position)
        listener.noteRemoveItem(myCursor as Cursor)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        if (myCursor != null) {
            return myCursor?.count as Int
        }

        return 0
    }

    fun setCursor(newCursor: Cursor?) {
        this.myCursor = newCursor
        notifyDataSetChanged()
    }
}
