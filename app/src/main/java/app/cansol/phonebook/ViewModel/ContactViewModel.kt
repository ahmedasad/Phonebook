package app.cansol.phonebook.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import app.cansol.phonebook.Model.Contact
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
        allContact = repository.list()
    }

    fun updateContact(contact:Contact,userId:String){
        repository.updateContact(contact,userId)
        allContact = repository.list()

    }

    fun deleteContact(contact:Contact,userId:String){
        repository.deleteCont(userId,contact)
        allContact = repository.list()

    }


}