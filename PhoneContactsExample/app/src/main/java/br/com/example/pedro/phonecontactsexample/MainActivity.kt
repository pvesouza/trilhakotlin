package br.com.example.pedro.phonecontactsexample

import android.content.pm.PackageManager
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class MainActivity : AppCompatActivity(), OnItemRecyclerClickListener {

    private lateinit var adapter: ContactsAdapter
    private val REQUEST_CONTACT = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerContacts: RecyclerView = findViewById(R.id.contacts_recyclerview)
        this.adapter = ContactsAdapter(mutableListOf())
        recyclerContacts.adapter = this.adapter
        recyclerContacts.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,
            false)
        this.adapter.setListener(this)
        // Test if there is permission to read contacts
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
            arrayOf(android.Manifest.permission.READ_CONTACTS),
            REQUEST_CONTACT)
        } else {
          setContacts()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CONTACT) {
            setContacts()
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun setContacts() {
        val contactList: ArrayList<Contact> = ArrayList()
        val cursor: Cursor? = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null)

        if (cursor != null) {

//            Fill in the contacts list
            while (cursor.moveToNext()) {
                val nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                val contactName: String = cursor.getString(nameIndex)
                val phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                val phoneNumber: String = cursor.getString(phoneIndex)
                contactList.add(Contact(contactName, phoneNumber))
            }
            cursor.close()
            // Sets all list of contacs
            this.adapter.setList(contactList)
        }
    }

    override fun onItemClicked(contact: Contact) {
        Toast.makeText(this, contact.name, Toast.LENGTH_LONG).show()
    }
}