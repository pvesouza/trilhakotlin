package br.com.example.pedro.architecture_mvvm.implementation

import android.util.Log
import br.com.example.pedro.architecture_mvvm.framework.api.MovieApiTask
import br.com.example.pedro.architecture_mvvm.data.MovieDataSource
import br.com.example.pedro.architecture_mvvm.domain.Movie

class MovieDataSourceImplementation(private val movieRestApiTask: MovieApiTask): MovieDataSource{

    private val movieList: ArrayList<Movie> = arrayListOf()

    companion object{
        const val TAG = "MovieRepository"
    }

    override fun getAllMovies(): List<Movie> {
        //Api calling to request all movies
        val request = movieRestApiTask.retrofitApi().getAllMovies().execute()

        if (request.isSuccessful) {
            request.body()?.let { list->
                movieList.addAll(list)
            }
        } else {
            request.errorBody()?.let { responseBody ->
                Log.d(TAG, responseBody.toString())
            }
        }

        return  movieList
    }

}