package app.cansol.phonebook.ViewModel

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.cansol.phonebook.Model.Contact
import app.cansol.phonebook.Model.User
import app.cansol.phonebook.Repository.ContactRepository

class ContactViewModel: ViewModel() {
    private var repository:ContactRepository = ContactRepository()
    lateinit var allContact:LiveData<List<Contact>>

    fun getContact(id:String){
        repository = ContactRepository()
        allContact = repository.getAllContacts(id)
    }

    fun createContact(contact:Contact,userId:String){
         repository.createContact(contact,userId)

    }

    fun updateContact(contact:Contact,userId:String):LiveData<Contact>{
        return  repository.updateContact(contact,userId)
    }

    fun deleteContact(contactId:String,userId:String){
        repository.deleteCont(userId,contactId)
    }


}