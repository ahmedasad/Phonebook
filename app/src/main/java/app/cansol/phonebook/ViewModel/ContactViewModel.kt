package app.cansol.phonebook.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import app.cansol.phonebook.Model.Contact
import app.cansol.phonebook.Repository.ContactRepository

/**
 * ViewModel class one of the component of MVVM
 * it communicate with repository
 * and also provide necessary data UI to display accordingly
 **/
class ContactViewModel: ViewModel() {
    private var repository:ContactRepository = ContactRepository()
    private lateinit var allContact:LiveData<List<Contact>>

    fun list():LiveData<List<Contact>>{
        return  allContact
    }

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