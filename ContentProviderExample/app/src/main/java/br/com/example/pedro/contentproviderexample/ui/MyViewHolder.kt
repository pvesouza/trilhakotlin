package br.com.example.pedro.contentproviderexample.ui

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.example.pedro.contentproviderexample.Note
import br.com.example.pedro.contentproviderexample.R

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val tvTitle = itemView.findViewById<TextView>(R.id.tv_title)
    private var tvDecription = itemView.findViewById<TextView>(R.id.tv_description)
    private val btExclude = itemView.findViewById<Button>(R.id.bt_exclude_note)

    fun bindView(note: Note) {
        tvTitle.text = note.title
        tvDecription.text = note.description
    }

    fun bindListener(clickListener: View.OnClickListener){
        btExclude.setOnClickListener(clickListener)
    }

}