package app.cansol.phonebook.AppData

import android.content.Context

class UserData(val context: Context) {
    private val PREFS_FILE = "pref"
    val pref = context.getSharedPreferences(PREFS_FILE,0)

    private val id = "userId"

    var userId:String?
    get() = pref.getString(id,"")
    set(value) = pref.edit().putString(id,value).apply()


}