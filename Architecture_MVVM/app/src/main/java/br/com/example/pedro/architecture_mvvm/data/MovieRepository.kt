package br.com.example.pedro.architecture_mvvm.data

import br.com.example.pedro.architecture_mvvm.domain.Movie

class MovieRepository(private val movieDataSource: MovieDataSource){

    fun getAllMovie() : List<Movie> {
        return movieDataSource.getAllMovies()
    }
}