package br.com.example.pedro.vmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    var modelCounter = MutableLiveData<String>().apply {
        value = counter.toString()
    }

    private var counter: Int = 0

    fun count(){
        restoreCounter()
    }

    private fun restoreCounter() {
        if (this.counter > 5) {
            this.counter = 0
        }else {
            this.counter++
        }
        setModelCounter()
    }

    private fun setModelCounter() {
        this.modelCounter.value = this.counter.toString()
    }
}