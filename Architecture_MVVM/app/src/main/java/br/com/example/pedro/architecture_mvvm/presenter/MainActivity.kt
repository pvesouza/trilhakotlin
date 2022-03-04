package br.com.example.pedro.architecture_mvvm.presenter

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.example.pedro.architecture_mvvm.MovieAdapter
import br.com.example.pedro.architecture_mvvm.R
import br.com.example.pedro.architecture_mvvm.domain.Movie
import br.com.example.pedro.architecture_mvvm.framework.api.viewmodel.MovieListViewModel

class MainActivity : AppCompatActivity(), ItemClickListener {

    private lateinit var movieRecycler: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var movieListViewModel: MovieListViewModel         // Ideally it is important to have one viewmodel for every screen
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Instantiates a viewModel
        movieListViewModel = ViewModelProvider.NewInstanceFactory().create(MovieListViewModel::class.java)
        movieListViewModel.init()

        //Initializes the recycler and adapter
        movieRecycler = findViewById(R.id.rv_movie)
        progressBar = findViewById(R.id.progress_bar)
        movieRecycler.setHasFixedSize(true)
        movieRecycler.layoutManager = LinearLayoutManager(this)
        loadVisibility(true)
        initObserver()
    }

    // Populates the recycler view with the list adapter
    private fun populateList(list: List<Movie>) {
        movieAdapter = MovieAdapter(list, this)
        movieRecycler.adapter = movieAdapter
    }

    // Observates the livedata
    private fun initObserver() {
        movieListViewModel.movieList.observe(this, { list->
            if (list.isNotEmpty()) {
                loadVisibility(false)
                populateList(list)
            }
        })
    }

    private fun loadVisibility(isLoading: Boolean) {
        if (isLoading) {
            progressBar.visibility = View.VISIBLE
        }else {
            progressBar.visibility = View.GONE
        }
    }

    override fun onItemClick(movie: Movie) {
        Toast.makeText(this, movie.titulo, Toast.LENGTH_LONG).show()
    }
}