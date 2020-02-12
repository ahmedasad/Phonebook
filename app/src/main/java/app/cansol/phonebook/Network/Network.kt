package app.cansol.phonebook.Network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.core.content.ContextCompat
import java.lang.NullPointerException

object Network {
    fun checkNetwork(context: Context):Boolean {
        try {
            val manager = ContextCompat.getSystemService(context, ConnectivityManager::class.java)
            var networkInfo: NetworkInfo? = null
            if(manager != null){
                networkInfo = manager.activeNetworkInfo
            }
            return networkInfo != null && networkInfo.isConnected

        }catch (e: NullPointerException){
            return false
        }
    }
}