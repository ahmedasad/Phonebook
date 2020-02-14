package app.cansol.phonebook.Controllers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.cansol.phonebook.Adapter.ContactListAdapter
import app.cansol.phonebook.AppData.AppData
import app.cansol.phonebook.ViewModel.ContactViewModel
import app.cansol.phonebook.Model.Contact
import app.cansol.phonebook.Network.Network
import app.cansol.phonebook.R

class MainActivity : AppCompatActivity() {

    /**
     Following variables will initialize in onCreate()
     **/
    lateinit var contactViewModel: ContactViewModel
    lateinit var contactAdapter: ContactListAdapter
    lateinit var appData: AppData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val context = this

        /**
         initializations of sharedPreference class
          **/
        appData = AppData(this)

       //checking if user is already logged in or not
        if (appData.userId == "") {
            val intent = Intent(this, SignInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }


        // recyclerView from layout and assigning layoutManager
        val recView = findViewById<RecyclerView>(R.id.contactRecyclerView)
        recView.layoutManager = LinearLayoutManager(this)

        // getting view model class from ViewModelProviders

        if (Network.checkNetwork(this)) {
        contactViewModel = ViewModelProviders.of(this,object: ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return ContactViewModel(appData.userId.toString()) as T
            }
        }).get(ContactViewModel::class.java)
            val observer = Observer<List<Contact>> {
                contactAdapter = ContactListAdapter(context, it!!)
                recView.adapter = contactAdapter
                contactAdapter.notifyDataSetChanged()
            }
            contactViewModel.list().observe(this, observer)
        } else Toast.makeText(this, "Check network connection", Toast.LENGTH_SHORT).show()


        // Touch Listener for recyclerview item swapping
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // Inflating layout for alert dialog
                val builder = AlertDialog.Builder(context).create()
                val dialogView = LayoutInflater.from(context).inflate(R.layout.contact_menu, null)
                val editView =
                    LayoutInflater.from(context).inflate(R.layout.edit_contact_form, null)
                builder.setView(dialogView)
                builder.show()
                dialogView.findViewById<TextView>(R.id.txteditCancel).setOnClickListener {
                    contactAdapter.notifyDataSetChanged()
                    builder.dismiss()
                }

                dialogView.findViewById<TextView>(R.id.txtDelete).setOnClickListener {
                    contactViewModel.deleteContact(
                        contactAdapter.getContactAt(viewHolder.adapterPosition),
                        appData.userId.toString()
                    )

                    Toast.makeText(context, "Contact Deleted!", Toast.LENGTH_SHORT).show()
                    builder.dismiss()
                }
                // When edit option is chosen from Alert dialog menu
                dialogView.findViewById<TextView>(R.id.txtEdit).setOnClickListener {
                    builder.dismiss()

                    val builder = AlertDialog.Builder(context).create()
                    builder.setView(editView)
                    builder.show()

                    var contact = contactAdapter.getContactAt(viewHolder.adapterPosition)
                    val name = editView.findViewById<EditText>(R.id.txtEditName)
                    val number = editView.findViewById<EditText>(R.id.txtEditNumber)
                    name.setText(contact.contact_name)
                    number.setText(contact.contact_number)

                    editView.findViewById<TextView>(R.id.txtEditDone).setOnClickListener {


                        if (name.text.isNotEmpty() && number.text.isNotEmpty()) {
                            Toast.makeText(context, "Save", Toast.LENGTH_SHORT)
                                .show()
                            val n = name.text.toString().substring(0,1).toUpperCase()+ name.text.toString().substring(1)
                            val cont = Contact(
                                contact.id,
                                contact.user_id,
                                n,
                                number.text.toString()
                            )
                            contactViewModel.updateContact(
                                cont,
                                appData.userId.toString()
                            )
                            builder.dismiss()
                        } else {
                            contactAdapter.notifyDataSetChanged()
                            Toast.makeText(
                                context,
                                "Field should not be empty!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    editView.findViewById<TextView>(R.id.txtEditCancel).setOnClickListener {
                        contactAdapter.notifyDataSetChanged()
                        Toast.makeText(context, "nothing happened!", Toast.LENGTH_SHORT).show()
                        builder.dismiss()
                    }

                }
                contactAdapter.notifyDataSetChanged()
            }
        }).attachToRecyclerView(recView)


    }

    /**
     starting activity to create new contact
     **/
    fun createContact(view: View) {
        if (Network.checkNetwork(this)) {
            val intent = Intent(this, CreateContactActivity::class.java)
            startActivityForResult(intent, 1)
        } else Toast.makeText(this, "Check network connection", Toast.LENGTH_SHORT).show()
    }

    /**
     the following function get the results and evaluating them and then calling ViewModel to create contact and perform related operations
     **/
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && data != null) {
            var name = data.getStringExtra("EXTRA_NAME")
            val number = data.getStringExtra("EXTRA_NUMBER")
            name = name.substring(0, 1).toUpperCase() + name.substring(1)
            val contact = Contact("", "1", name, number)
            if (Network.checkNetwork(this)) {
                contactViewModel.createContact(contact, appData.userId.toString())
                Toast.makeText(this, "Contact Saved!", Toast.LENGTH_SHORT).show()
            } else Toast.makeText(this, "Check network connection", Toast.LENGTH_SHORT).show()
        }

    }

    /**
     the menu from resource file inflating into action bar
     **/
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.main_activity_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     perform action on choosing option from menu inflated into actionbar
     **/
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.logout -> {
                AppData(this).userId = ""
                val intent = Intent(this, SignInActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

}
