package br.com.example.pedro.clientlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(val listener: ClickItemRecyclerView) : RecyclerView.Adapter<MyViewHolder>(){

    private var list: MutableList<Contact> = mutableListOf()

    fun updateList(list:List<Contact>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    // Creates the view that will be populated by onBindViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layoutitem, parent, false);
        return MyViewHolder(view, listener)
    }

    // Fills every item in my list on the screen
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val contact:Contact = list[position]
        holder.bind(contact)
    }

    // The number of itens in the list
    override fun getItemCount(): Int {
        return list.size
    }


}