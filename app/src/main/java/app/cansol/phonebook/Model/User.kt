package app.cansol.phonebook.Model

import com.google.gson.annotations.SerializedName

class User(
    val id: String, @SerializedName("name") val userName: String,
    val email: String, @SerializedName("phone_number") val phoneNumber: String,
    val password:String
) {
    constructor():this("","","","","")
}