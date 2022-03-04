package br.com.example.pedro.architecture_mvvm.framework.api

import br.com.example.pedro.architecture_mvvm.domain.Movie
import retrofit2.Call
import retrofit2.http.GET

interface MovieApi {
    // API's Address to search the movies' list
//    https://raw.githubusercontent.com/natanfelipe/FilmesFlixJson/master/moviesList

    @GET("natanfelipe/FilmesFlixJson/master/moviesList")
    fun getAllMovies(): Call<List<Movie>>
    
}