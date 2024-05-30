package com.example.qrcode1

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ContactsActivity : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var contactList: MutableList<Contact>
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        contactList = mutableListOf()
        listView = findViewById(R.id.contactsList)

        val currentUser = auth.currentUser?.uid
        if (currentUser != null) {
            val userRef = database.getReference("contacts").child(currentUser)

            userRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    contactList.clear()
                    for (data in snapshot.children) {
                        val contact = data.getValue(Contact::class.java)
                        if (contact != null) {
                            contactList.add(contact)
                        }
                    }

                    val adapter = ArrayAdapter(
                        this@ContactsActivity,
                        android.R.layout.simple_list_item_1,
                        contactList.map { "${it.firstName} ${it.lastName} - ${it.phoneNumber}" }
                    )
                    listView.adapter = adapter
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("ContactsActivity", "Failed to read contacts", error.toException())
                }
            })
        } else {
            Log.e("ContactsActivity", "Current user is null")
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedContact = contactList[position]
            val intent = Intent(Intent.ACTION_INSERT).apply {
                type = ContactsContract.RawContacts.CONTENT_TYPE
                putExtra(ContactsContract.Intents.Insert.NAME, "${selectedContact.firstName} ${selectedContact.lastName}")
                putExtra(ContactsContract.Intents.Insert.PHONE, selectedContact.phoneNumber)
            }
            startActivity(intent)
        }

        val addBtn = findViewById<Button>(R.id.Contacts)
        addBtn.setOnClickListener {
            val intent = Intent(this, AddContact::class.java)
            startActivity(intent)
        }
    }
}
