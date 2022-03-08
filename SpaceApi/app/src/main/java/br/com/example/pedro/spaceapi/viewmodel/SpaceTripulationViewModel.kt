package br.com.example.pedro.spaceapi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.example.pedro.spaceapi.api.TaskAstro
import br.com.example.pedro.spaceapi.domain.SpaceTripulation

class SpaceTripulationViewModel: ViewModel() {

    private val astroApiTask = TaskAstro(this)

    // The data that will be in contact with user interface
    private val _spaceCrew: MutableLiveData<SpaceTripulation> by lazy {
        MutableLiveData<SpaceTripulation>().also {
            getAllSpaceCrew()
        }
    }

    fun getSpaceCrew(): LiveData<SpaceTripulation> {
        return _spaceCrew
    }

    fun postValue(value: SpaceTripulation) {
        // This atualizes the data on user interface
        _spaceCrew.postValue(value)
    }

    private fun getAllSpaceCrew() {
        // Executes the assyncronous task
        astroApiTask.execute()
    }
}