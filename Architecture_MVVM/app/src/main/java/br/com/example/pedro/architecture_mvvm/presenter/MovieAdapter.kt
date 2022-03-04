package br.com.example.pedro.architecture_mvvm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import br.com.example.pedro.architecture_mvvm.domain.Movie
import br.com.example.pedro.architecture_mvvm.presenter.ItemClickListener
import com.bumptech.glide.Glide

class MovieAdapter(private val list: List<Movie>, val listener: ItemClickListener): RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(list[position], listener)
    }

    override fun getItemCount(): Int {
        return this.list.size
    }
}

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    private val tvMovieTitle: TextView = itemView.findViewById(R.id.tv_movie_title)
    private val tvMovieDescription: TextView = itemView.findViewById(R.id.tv_movie_description)
    private val layout: ConstraintLayout = itemView.findViewById(R.id.item_layout)
    private val ivMoviewImage: ImageView = itemView.findViewById(R.id.iv_movie_item)


    fun bind(movie: Movie, listener: ItemClickListener) {
        tvMovieTitle.setText(movie.titulo)
        tvMovieDescription.setText(movie.descricao)
        Glide.with(itemView.context)
            .load(movie.imagem)
            .into(ivMoviewImage);
        layout.setOnClickListener {
            listener.onItemClick(movie)
        }
    }
}
