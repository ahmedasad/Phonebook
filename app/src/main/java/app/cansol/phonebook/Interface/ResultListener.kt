package app.cansol.phonebook.Interface

interface ResultListener {
    fun onSuccess(message:String)
    fun onFailure(message: String)
}