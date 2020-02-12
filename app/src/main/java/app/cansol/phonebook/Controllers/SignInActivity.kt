package app.cansol.phonebook.Controllers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import app.cansol.phonebook.Data.UserData
import app.cansol.phonebook.Interface.ResultListener
import app.cansol.phonebook.Model.User
import app.cansol.phonebook.Network.Network
import app.cansol.phonebook.R
import app.cansol.phonebook.ViewModel.ContactViewModel

class SignInActivity : AppCompatActivity(), ResultListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        UserData.initUsers()
    }

    fun signIn(view: View) {
        val number = findViewById<EditText>(R.id.edTxtSignInPhoneNumber).text
        val password = findViewById<EditText>(R.id.edTxtSignInPassword).text

        if (number.trim().isNotEmpty() && password.isNotEmpty()) {
            if (Network.checkNetwork(this)) {
                UserData.findUser(number.toString(), password.toString(), this)
            } else Toast.makeText(this, "Check network connection", Toast.LENGTH_SHORT).show()
        } else Toast.makeText(this, "Field(s) is empty!", Toast.LENGTH_SHORT).show()

    }

    override fun onSuccess(message: String) {
        app.cansol.phonebook.AppData.UserData(this).userId = UserData.currentUser!!.id
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    override fun onFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
