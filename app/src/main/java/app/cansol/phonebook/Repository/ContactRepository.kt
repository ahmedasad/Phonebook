package app.cansol.phonebook.Repository

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.cansol.phonebook.Model.Contact
import app.cansol.phonebook.RetrofitAPI.PhoneBookApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Repository class one of the component of MVVM
 * it will communicate with web services for crud operations
 * **/
class ContactRepository {

    private val responseContact = MutableLiveData<List<Contact>>()
    private val contactList = ArrayList<Contact>()

    fun getAllContacts(id:String):LiveData<List<Contact>>{
        PhoneBookApi().getContacts(id).enqueue(object:Callback<List<Contact>>{
            override fun onFailure(call: Call<List<Contact>>, t: Throwable) {}

            override fun onResponse(call: Call<List<Contact>>, response: Response<List<Contact>>) {
                if(!response.isSuccessful) return
                contactList.removeAll(contactList)
                contactList.addAll(response.body()!!)
                responseContact.value = contactList.sortedWith(compareBy({it.contact_name}))

            }
        })
        return responseContact
    }

    fun createContact(contact:Contact,userId:String):LiveData<List<Contact>>{
        PhoneBookApi()
            .createContact(contact,userId).enqueue(object : Callback<Contact>{
                override fun onFailure(call: Call<Contact>, t: Throwable) {}

                override fun onResponse(call: Call<Contact>, response: Response<Contact>) {
                    if(!response.isSuccessful) {
                        println("didnt done...")
                        return
                    }
                    contactList.add(response.body()!!)
                    responseContact.value = contactList.sortedWith(compareBy({it.contact_name}))
                    println("done....")
                }

            })
        return responseContact
    }

    fun updateContact(contact:Contact,userId:String):LiveData<List<Contact>>{
        PhoneBookApi()
            .updateContact(contact,userId,contact.id!!).enqueue(object: Callback<Contact>{
            override fun onFailure(call: Call<Contact>, t: Throwable) {}

            override fun onResponse(call: Call<Contact>, response: Response<Contact>) {
                if(!response.isSuccessful) return
                contactList.removeIf{
                    it.id == contact.id
                }
                contactList.add(response.body()!!)
                responseContact.value = contactList.sortedWith(compareBy({it.contact_name}))
            }
        })
        return responseContact
    }

    fun deleteCont(userId:String,contact:Contact):LiveData<List<Contact>>{
        PhoneBookApi()
            .deleteContact(userId,contact.id!!).enqueue(object: Callback<Unit>{
            override fun onFailure(call: Call<Unit>, t: Throwable) {}

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if(!response.isSuccessful) return
                contactList.removeIf {
                    it.id == contact.id
                }
                responseContact.value = contactList.sortedWith(compareBy({it.contact_name}))

            }
        })
        return responseContact
    }


}