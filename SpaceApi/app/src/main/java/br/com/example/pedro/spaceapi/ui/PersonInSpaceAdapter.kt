package br.com.example.pedro.spaceapi.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.example.pedro.spaceapi.R
import br.com.example.pedro.spaceapi.domain.PersonInSpace

class PersonInSpaceAdapter(val list: List<PersonInSpace>):
    RecyclerView.Adapter<PersonInSpaceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonInSpaceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_people_in_space, parent, false)
        return PersonInSpaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonInSpaceViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class PersonInSpaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val textViewName = itemView.findViewById<TextView>(R.id.tv_item_person_name)
    val textViewCraft = itemView.findViewById<TextView>(R.id.tv_item_craft)

    fun bind(personInSpace: PersonInSpace) {
        textViewCraft.setText(personInSpace.craft)
        textViewName.setText(personInSpace.name)
    }

}
