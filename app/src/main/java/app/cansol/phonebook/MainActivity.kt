package app.cansol.phonebook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import app.cansol.phonebook.Model.Contact
import app.cansol.phonebook.Model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://5e425f20f6128600148ad36c.mockapi.io/pb/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val user = User("1","John","john@gmail.com","9654783124","thisisJohn")

        val contact = Contact("",user.id,"Ahmed","03482524344")

        val phoneBookApi = retrofit.create(PhoneBookApi::class.java)

        val call = phoneBookApi.deleteContact(user.id,"4")

//        call.enqueue(object: Callback<List<User>>{
//            override fun onFailure(call: Call<List<User>>, t: Throwable) {
//                findViewById<TextView>(R.id.mainText).text =t.localizedMessage
//            }
//
//            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
//
//                if(!response.isSuccessful){
//                    findViewById<TextView>(R.id.mainText).text = "code: ${response.code()}"
//                    return
//                }
//
//                val users = response.body()!!
//                val user = users[0].userName
//                findViewById<TextView>(R.id.mainText).text =user
//            }
//        })
//
//        call.enqueue(object: Callback<List<Contact>>{
//            override fun onFailure(call: Call<List<Contact>>, t: Throwable) {
//                findViewById<TextView>(R.id.mainText).text =t.localizedMessage
//            }
//
//            override fun onResponse(call: Call<List<Contact>>, response: Response<List<Contact>>) {
//                if(!response.isSuccessful){
//                    findViewById<TextView>(R.id.mainText).text = "code: ${response.code()}"
//                    return
//                }
//
//                val users = response.body()!!
//                val user = users[0].contact_number
//                findViewById<TextView>(R.id.mainText).text =user
//            }
//
//        })

        call.enqueue(object:Callback<Unit>{
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                findViewById<TextView>(R.id.mainText).text = t.localizedMessage
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if(!response.isSuccessful) {
                    findViewById<TextView>(R.id.mainText).text = response.code().toString()
                    return
                }
                val contact = response.body()
                findViewById<TextView>(R.id.mainText).text = " ,,${response.code()}"
            }
        })
    }


}
