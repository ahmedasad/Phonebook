package app.cansol.phonebook.Model

class Contact(val id:String?,val user_id:String,val contact_name:String, val contact_number:String) {
    constructor():this("","","","")
}