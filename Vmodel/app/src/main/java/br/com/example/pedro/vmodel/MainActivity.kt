package br.com.example.pedro.vmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var editCounter: EditText
    private lateinit var buttonCount: Button
    private lateinit var buttonShow: Button

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViews()
        initListeners()
    }

    private fun initListeners() {
        this.buttonCount.setOnClickListener{
            this.mainViewModel.count()
        }

        this.buttonShow.setOnClickListener{
            Toast.makeText(this, "Counter = ${this.mainViewModel.modelCounter.value}", Toast.LENGTH_LONG).show()
        }
    }

    private fun bindViews() {

        this.mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        this.editCounter = findViewById(R.id.edittext_counter)
        this.buttonCount = findViewById(R.id.button_counter)
        this.buttonShow = findViewById(R.id.button_showcounter)

        mainViewModel.modelCounter.observe(this, Observer{ value->
            this.editCounter.setText(value)
        })
    }
}