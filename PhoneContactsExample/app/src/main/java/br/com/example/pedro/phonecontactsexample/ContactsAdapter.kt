package br.com.example.pedro.phonecontactsexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactsAdapter(private var list: List<Contact>):
    RecyclerView.Adapter<ContactsViewHolder>() {

    private lateinit var listener: OnItemRecyclerClickListener

    fun setList(list: List<Contact>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun setListener(listener: OnItemRecyclerClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val layout: View = layoutInflater.inflate(R.layout.contactview_item, parent, false)
        return ContactsViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val itemObject = this.list[position]
        holder.bindView(itemObject, this.listener)
    }

    override fun getItemCount(): Int {
        return this.list.size
    }
}

class ContactsViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val textViewName: TextView = view.findViewById(R.id.textview_contact_name)
    private val textViewPhone: TextView = view.findViewById(R.id.textview_contact_phone)

    fun bindView(contact: Contact, listener: OnItemRecyclerClickListener) {
        this.textViewName.text = contact.name
        this.textViewPhone.text = contact.phone
        // Listener that comes from main activity
        itemView.setOnClickListener {
            listener.onItemClicked(contact)
        }
    }
}

