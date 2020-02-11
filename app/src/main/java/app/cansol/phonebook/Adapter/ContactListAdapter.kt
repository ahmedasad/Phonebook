package app.cansol.phonebook.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.cansol.phonebook.Model.Contact
import app.cansol.phonebook.R

class ContactListAdapter(val context: Context,val contacts:List<Contact>): RecyclerView.Adapter<ContactListAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.contact_layout,parent,false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return contacts.count()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindView(context,contacts[position])
    }

    inner class Holder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bindView(context: Context,contact: Contact){
            val contactName = itemView.findViewById<TextView>(R.id.txtContactName)
            val contactNumber = itemView.findViewById<TextView>(R.id.txtContactNumber)


            contactName.text = contact.contact_name
            contactNumber.text = contact.contact_number
        }
    }



}