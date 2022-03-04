package br.com.example.pedro.architecture_mvvm.presenter

import br.com.example.pedro.architecture_mvvm.domain.Movie

interface ItemClickListener {

    fun onItemClick(movie: Movie)
}