package app.cansol.phonebook.Data

import app.cansol.phonebook.Interface.ResultListener
import app.cansol.phonebook.Model.User

object UserData {

    val users: ArrayList<User> = ArrayList()

    var currentUser: User? = null
    fun initUsers() {
        users.add(User("1", "John", "John@gmail.com", "009234582146", "thisisJohn"))
        users.add(User("2", "Mike", "mike@gmail.com", "009234582564", "thisisMike"))
        users.add(User("3", "alex", "alex@gmail.com", "009234582234", "thisisAlex"))
    }

    fun findUser(phoneNumber: String, password: String, listener: ResultListener){

            for (user in users) {
                if (user.phoneNumber == phoneNumber) {
                    if (user.password == password) {
                        currentUser = user
                        listener.onSuccess("Success")
                        return
                    } else {
                        listener.onFailure("Number/Password is wrong!")
                        return
                    }
                }
            }
            listener.onFailure("Account doesn't exist!")
        }


}