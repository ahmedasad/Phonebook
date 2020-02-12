package app.cansol.phonebook.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.cansol.phonebook.Model.Contact
import app.cansol.phonebook.Repository.ContactRepository

class ContactViewModel: ViewModel() {
    private lateinit var repository:ContactRepository
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