package br.com.example.pedro.spaceapi.ui

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.example.pedro.spaceapi.R
import br.com.example.pedro.spaceapi.domain.PersonInSpace
import br.com.example.pedro.spaceapi.viewmodel.SpaceTripulationViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var textViewNumberOfPeopleOnSpace: TextView
    private lateinit var adapter: PersonInSpaceAdapter
    private lateinit var progresseBar: ProgressBar
    private lateinit var viewModel:SpaceTripulationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        initViews()
        setLoadingIndicationVisibility(true)
        initObserver()


    }

    private fun initObserver() {
        viewModel = ViewModelProvider.NewInstanceFactory().create(SpaceTripulationViewModel::class.java)
        viewModel.getSpaceCrew().observe(this, {
            initRecycler(it.people, it.number)
        })
    }

    // Initializes all views
    private fun initViews() {
        textViewNumberOfPeopleOnSpace = findViewById(R.id.tv_number_of_people)
        recyclerView = findViewById(R.id.rv_people_on_space)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(false)
        progresseBar = findViewById(R.id.progressbar_main)
        setLoadingIndicationVisibility(true)
    }

    // initializes the recycler view
    private fun initRecycler(list: List<PersonInSpace>, number: Int) {
        adapter = PersonInSpaceAdapter(list)
        recyclerView.adapter = adapter
        textViewNumberOfPeopleOnSpace.setText("${number}")
        setLoadingIndicationVisibility(false)
    }

    // Sets the visibility to gone
    fun setLoadingIndicationVisibility(status: Boolean) {
        progresseBar.visibility = if (status) View.VISIBLE else View.GONE
    }

}