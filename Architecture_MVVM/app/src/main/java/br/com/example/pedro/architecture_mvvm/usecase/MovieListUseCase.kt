package br.com.example.pedro.architecture_mvvm.usecase

import br.com.example.pedro.architecture_mvvm.data.MovieRepository

class MovieListUseCase(private val movieRepository: MovieRepository) {
    operator fun invoke() = movieRepository.getAllMovie()
}