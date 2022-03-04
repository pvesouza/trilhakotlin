package br.com.example.pedro.clientlist

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity(), ClickItemRecyclerView {

    val contactsList: RecyclerView by lazy {
        findViewById(R.id.rv_list)
    }

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var toogle: ActionBarDrawerToggle
    private val myAdapter: MyAdapter = MyAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_layout)

        val list: MutableList<Contact> = fetchContactsList()
        bindViews()
        initializeDrawerLayout()
    }

    // Creates an options menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater: MenuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.activity_mail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_menu_1 -> {
                showToast("${item.title}")
            }

            R.id.item_menu_2 -> {
                showToast("${item.title}")

            }

            R.id.item_menu_3 -> {
                showToast("${item.title}")
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onClickItem(contact: Contact) {
        val intent = Intent(this, ContactDetails::class.java)
        val bundle = Bundle();
        bundle.putSerializable("CONTACT", contact)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun showToast(s: String): Boolean {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show()
        return true
    }

    private fun updateList(list: List<Contact>) {
        myAdapter.updateList(list)
    }

    private fun bindViews() {
        contactsList.adapter = myAdapter
        contactsList.layoutManager = LinearLayoutManager(this)
        val list = getContactsList()
        updateList(list)
    }

    private fun initializeDrawerLayout() {

        this.drawerLayout = findViewById(R.id.drawer_layout)
        this.toolbar = findViewById(R.id.toolbar_main)

        setSupportActionBar(toolbar)

        this.toogle = ActionBarDrawerToggle(this, drawerLayout,
        this.toolbar, R.string.label_drawer_open, R.string.label_drawer_closed)

        this.toogle.isDrawerIndicatorEnabled = true
        this.drawerLayout.addDrawerListener(this.toogle)
        this.toogle.syncState()
    }

    private fun fetchContactsList(): MutableList<Contact> {
        val list: MutableList<Contact> = mutableListOf()
        list.add(Contact("Pedro Victor", "(83)99629-3341"))
        list.add(Contact("Janaina","(83)99998-8108"))
        getInstanceOfSharedPreferences().edit {
            putString("contacts", Gson().toJson(list))
        }
        return list
    }

    private fun getInstanceOfSharedPreferences() : SharedPreferences {

        return getSharedPreferences("br.com.example.pedro.clientList.SHAREDPREFERENCES", Context.MODE_PRIVATE)
    }

    private fun getContactsList() : List<Contact> {
        val listStr = getInstanceOfSharedPreferences().getString("contacts", "[]")
        val turnsType = object : TypeToken<List<Contact>>(){}.type
        return Gson().fromJson(listStr, turnsType)
    }


}
