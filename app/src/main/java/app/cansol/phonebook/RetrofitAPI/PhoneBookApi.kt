package app.cansol.phonebook.RetrofitAPI

import app.cansol.phonebook.Model.Contact
import app.cansol.phonebook.Model.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface PhoneBookApi {

    @GET("users")
    fun getUser():Call<List<User>>

    @GET("users/{id}/contacts")
    fun getContacts(@Path("id") id: String): Call<List<Contact>>

    @POST("users/{id}/contacts")
    fun createContact(@Body contact: Contact, @Path("id") userId: String): Call<List<Contact>>

    @PUT("users/{id}/contacts/{contact_id}")
    fun updateContact(
        @Body contact: Contact,
        @Path("id") userId: String,
        @Path("contact_id") contactId: String
    ): Call<Contact>

    @DELETE("users/{id}/contacts/{contact_id}")
    fun deleteContact(
        @Path("id") userId: String,
        @Path("contact_id") contactId: String
    ): Call<Unit>


    companion object {
        operator fun invoke(): PhoneBookApi {
            return Retrofit.Builder()
                .baseUrl("https://5e425f20f6128600148ad36c.mockapi.io/pb/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PhoneBookApi::class.java)
        }
    }

}