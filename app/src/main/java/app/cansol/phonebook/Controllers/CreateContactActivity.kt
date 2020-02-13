package app.cansol.phonebook.Controllers

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import app.cansol.phonebook.Network.Network
import app.cansol.phonebook.R

class CreateContactActivity : AppCompatActivity() {

    /**
     two fields for contact creation
     **/
    lateinit var name:EditText
    lateinit var number:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_contact)


        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_close)
        setTitle("Create Contact")

        name = findViewById(R.id.edittextName)
        number = findViewById(R.id.edittxtPhoneNum)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.create_contact_menu, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.save_contact -> {

                if (Network.checkNetwork(this)) {
                    saveContact()
                    return true
                }
                else{
                    Toast.makeText(this, "Check network connection", Toast.LENGTH_SHORT).show()
                    return false
                }

            }
            else -> {
                if (name.text.isNotEmpty() && number.text.trim().isNotEmpty()) {
                    Toast.makeText(this,"Contact didn't save",Toast.LENGTH_SHORT).show()
                }
                return super.onOptionsItemSelected(item)
            }
        }
    }

    /**
     takes data from edittext fields and send them through intent using putExtra function**/
    private fun saveContact() {
        if (name.text.isNotEmpty() && number.text.trim().isNotEmpty()) {
            val data = Intent()
            data.putExtra("EXTRA_NAME", "${name.text}")
            data.putExtra("EXTRA_NUMBER", "${number.text}")
            setResult(Activity.RESULT_OK, data)
            finish()
        } else {
            Toast.makeText(this, "Provide Complete Data", Toast.LENGTH_SHORT).show()
        }

    }
}
