package br.com.example.pedro.apiexample.api

import br.com.example.pedro.apiexample.model.Product
import retrofit2.Call
import retrofit2.http.GET


interface ProductApi {
    @GET("getdata.php")
    fun getProductApi(): Call<List<Product>>
}