package app.cansol.phonebook

import app.cansol.phonebook.Model.Contact
import retrofit2.Call
import retrofit2.http.*

interface PhoneBookApi {

    @GET("users/{id}/contacts")
    fun getContacts(@Path("id") postid:String):Call<List<Contact>>

    @POST("users/{id}/contacts")
    fun createContact(@Body contact: Contact,@Path("id") userId:String):Call<Contact>

    @PUT("users/{id}/contacts/{contact_id}")
    fun updateContact(@Body contact: Contact,
                      @Path("id") userId: String,
                      @Path("contact_id") contactId: String):Call<Contact>

    @DELETE("users/{id}/contacts/{contact_id}")
    fun deleteContact(@Path("id") userId: String,
                      @Path("contact_id") contactId:String):Call<Unit>


}