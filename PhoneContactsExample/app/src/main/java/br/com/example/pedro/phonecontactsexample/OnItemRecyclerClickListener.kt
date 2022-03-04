package br.com.example.pedro.phonecontactsexample

import android.provider.ContactsContract
import android.view.View
import android.widget.AdapterView

interface OnItemRecyclerClickListener {

    fun onItemClicked(contact: Contact)

}