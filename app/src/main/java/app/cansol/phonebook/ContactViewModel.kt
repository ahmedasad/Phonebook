package app.cansol.phonebook

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import app.cansol.phonebook.Model.Contact
import app.cansol.phonebook.Repository.ContactRepository

class ContactViewModel(val app: Application): AndroidViewModel(app) {
    private var repository = ContactRepository()

    fun getContact(id:String):LiveData<List<Contact>>{
        return repository.getAllContacts(id)
    }

    fun createContact(contact:Contact,userId:String):LiveData<Contact>{
        return repository.createContact(contact,userId)
    }

    fun updateContact(contact:Contact,userId:String):LiveData<Contact>{
        return  repository.updateContact(contact,userId)
    }


    fun deleteContact(contactId:String,userId:String){
        repository.deleteCont(userId,contactId)
    }
}