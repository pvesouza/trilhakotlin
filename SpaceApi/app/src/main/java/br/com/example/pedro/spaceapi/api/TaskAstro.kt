package br.com.example.pedro.spaceapi.api

import android.os.AsyncTask
import android.util.Log
import br.com.example.pedro.spaceapi.domain.SpaceTripulation
import br.com.example.pedro.spaceapi.repository.PersonInSpaceRepository
import br.com.example.pedro.spaceapi.ui.MainActivity
import br.com.example.pedro.spaceapi.viewmodel.SpaceTripulationViewModel

class TaskAstro(val viewModel: SpaceTripulationViewModel) : AsyncTask<Void, Int, SpaceTripulation>() {

    companion object{
        const val TAG = "TASK_ASTRO"
    }

    // The moment we can modify the UI
    override fun onPreExecute() {
        super.onPreExecute()
    }

    // Lança a Thread secundária em background
    override fun doInBackground(vararg params: Void?): SpaceTripulation {
        val astroApi = AstroApi()
        return astroApi.loadData()
    }

    override fun onPostExecute(result: SpaceTripulation?) {
        super.onPostExecute(result)
        if (result != null) {
            viewModel.postValue(result)
            Log.d(TAG, "${result.number}")
        }
    }
}