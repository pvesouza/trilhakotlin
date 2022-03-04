package br.com.example.pedro.architecture_mvvm.domain

data class Movie(
    private val id: Int = 0,
    internal val titulo: String,
    internal val imagem: String?,
    internal val descricao: String?,
    internal val data_lancamento: String?
)
