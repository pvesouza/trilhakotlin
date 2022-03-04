package br.com.example.pedro.architecture_mvvm.framework.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieApiTask {

    companion object{
        const val BASE_URL = "https://raw.githubusercontent.com/"
    }

    fun retrofitApi(): MovieApi {
        return movieProvider().create(MovieApi::class.java)
    }

    private fun movieProvider(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}