package br.com.example.pedro.clientlist

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Class responsible for doing the bind between the list
class MyViewHolder(itemView: View, listener: ClickItemRecyclerView) : RecyclerView.ViewHolder(itemView) {

    private val nameTextView:TextView = itemView.findViewById(R.id.textView_name)
    private val phonTextView:TextView = itemView.findViewById(R.id.textView_phone)
    private val imageView: ImageView = itemView.findViewById(R.id.imageView)
    private lateinit var contact:Contact

    init {
        itemView.setOnClickListener{
            listener.onClickItem(this.contact)
        }
    }

    fun bind(contact:Contact){
        nameTextView.text = contact.name
        phonTextView.text = contact.phone
        val imageByteArray = contact.getImage()
        this.contact = contact
    }

}