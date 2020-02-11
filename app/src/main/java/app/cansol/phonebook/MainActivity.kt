package app.cansol.phonebook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.cansol.phonebook.Adapter.ContactListAdapter
import app.cansol.phonebook.Model.Contact
import app.cansol.phonebook.Model.User
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var contactViewModel:ContactViewModel
    lateinit var contactAdapter: ContactListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recView = findViewById<RecyclerView>(R.id.contactRecyclerView)
        recView.layoutManager = LinearLayoutManager(this)
        val context = this

        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)
        contactViewModel.getContact("1").observe(this,object: Observer<List<Contact>>{
            override fun onChanged(t: List<Contact>?) {
                contactAdapter = ContactListAdapter(context,t!!)
                recView.adapter = contactAdapter
                contactAdapter.notifyDataSetChanged()
            }
        })

    }


}
