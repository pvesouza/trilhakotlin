package br.com.example.pedro.architecture_mvvm.data

import br.com.example.pedro.architecture_mvvm.domain.Movie

interface MovieDataSource {
    fun getAllMovies(): List<Movie>
}