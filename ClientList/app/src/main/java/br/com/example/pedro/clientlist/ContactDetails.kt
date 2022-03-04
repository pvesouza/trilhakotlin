package br.com.example.pedro.clientlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar

class ContactDetails : AppCompatActivity() {

    private lateinit var contactImage:ImageView
    private lateinit var contactNameTv:TextView
    private lateinit var contactPhoneTv:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_details)

        this.contactImage = findViewById(R.id.imageView_contact_details)
        this.contactNameTv = findViewById(R.id.textview_name_contact_details)
        this.contactPhoneTv = findViewById(R.id.textview_phone_contact_details)

        val bundle = intent.extras

        if (bundle != null) {
            val contact = bundle.getSerializable("CONTACT") as Contact
            this.contactPhoneTv.text = contact.phone
            this.contactNameTv.text = contact.name
        }

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}