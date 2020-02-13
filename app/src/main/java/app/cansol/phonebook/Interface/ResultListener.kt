package app.cansol.phonebook.Interface

/**
 * Interface used proived result on specific occasion**/
interface ResultListener {
    fun onSuccess(message:String)
    fun onFailure(message: String)
}