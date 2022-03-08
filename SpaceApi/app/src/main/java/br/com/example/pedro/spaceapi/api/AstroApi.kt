package br.com.example.pedro.spaceapi.api

import br.com.example.pedro.spaceapi.domain.SpaceTripulation
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

class AstroApi {

    companion object {
        const val BASE_URL = "http://api.open-notify.org/astros.json"
    }

    fun loadData(): SpaceTripulation {
        val client = OkHttpClient()
        val request = Request.Builder().url(BASE_URL).build()
        // API response. This is a JSON String
        val response = client.newCall(request).execute()
        val result = parseJsonToResult(response.body?.string())
        return result
    }

    private fun parseJsonToResult(json: String?): SpaceTripulation{
        return Gson().fromJson(json, SpaceTripulation::class.java)
    }
}