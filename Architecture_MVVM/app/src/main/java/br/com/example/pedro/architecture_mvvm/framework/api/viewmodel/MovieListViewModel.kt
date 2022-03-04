package br.com.example.pedro.architecture_mvvm.framework.api.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.example.pedro.architecture_mvvm.framework.api.MovieApiTask
import br.com.example.pedro.architecture_mvvm.data.MovieRepository
import br.com.example.pedro.architecture_mvvm.domain.Movie
import br.com.example.pedro.architecture_mvvm.implementation.MovieDataSourceImplementation
import br.com.example.pedro.architecture_mvvm.usecase.MovieListUseCase
import java.lang.Exception

class MovieListViewModel: ViewModel() {

    private val movieApiTask = MovieApiTask()
    private val movieDataSource = MovieDataSourceImplementation(movieApiTask)
    private val movieRepository = MovieRepository(movieDataSource)
    private val movieListUseCase = MovieListUseCase(movieRepository)

    companion object {
        const val TAG = "MovieListViewModel"
    }

    // Data that is mutable and it is loaded with a list of Movies
    private var _movieList: MutableLiveData<List<Movie>> = MutableLiveData<List<Movie>>()
    // This is for encapsulating the mutable livedata
    val movieList: LiveData<List<Movie>>
    get() = _movieList


    fun init() {
        getAllMovies()
    }

    private fun getAllMovies() {
        Thread{
            try {
                _movieList.postValue(movieListUseCase.invoke())
            }catch (exception: Exception) {
                Log.d(TAG, exception.message.toString())
            }
        }.start()
    }
}