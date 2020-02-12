package app.cansol.phonebook.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.cansol.phonebook.Model.Contact
import app.cansol.phonebook.RetrofitAPI.PhoneBookApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContactRepository {

    private val responseContact = MutableLiveData<List<Contact>>()

    fun getAllContacts(id:String):LiveData<List<Contact>>{
        PhoneBookApi().getContacts(id).enqueue(object:Callback<List<Contact>>{
            override fun onFailure(call: Call<List<Contact>>, t: Throwable) {

            }

            override fun onResponse(call: Call<List<Contact>>, response: Response<List<Contact>>) {
                if(!response.isSuccessful) return
                responseContact.value = response.body()
            }
        })
        return responseContact
    }

    fun createContact(contact:Contact,userId:String):LiveData<List<Contact>>{
        val list = ArrayList<Contact>()
        PhoneBookApi()
            .createContact(contact,userId).enqueue(object : Callback<List<Contact>>{
            override fun onFailure(call: Call<List<Contact>>, t: Throwable) {

            }

            override fun onResponse(call: Call<List<Contact>>, response: Response<List<Contact>>) {
                if(!response.isSuccessful) return
                responseContact.value = response.body()!!
            }
        })
        return responseContact
    }

    fun updateContact(contact:Contact,userId:String):LiveData<Contact>{
        var cont = MutableLiveData<Contact>()
        PhoneBookApi()
            .updateContact(contact,userId,contact.id!!).enqueue(object: Callback<Contact>{
            override fun onFailure(call: Call<Contact>, t: Throwable) {}

            override fun onResponse(call: Call<Contact>, response: Response<Contact>) {
                if(!response.isSuccessful) return
                cont.value = response.body()
            }
        })
        return cont
    }

    fun deleteCont(userId:String,contactId:String){
        PhoneBookApi()
            .deleteContact(userId,contactId).enqueue(object: Callback<Unit>{
            override fun onFailure(call: Call<Unit>, t: Throwable) {}

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if(!response.isSuccessful) return
            }
        })
    }

}