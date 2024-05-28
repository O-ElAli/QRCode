package com.example.qrcode1

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Contacts (firstName : String = "", lastName : String = "", phoneNumber : String = "") : AppCompatActivity() {

    lateinit var contacts: ListView

    companion object {
        var contactList = ArrayList<String>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_contacts)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        contacts = findViewById(R.id.contactsList)
        contactList = ArrayList()


        // Add the contact to the list
        contactList.add("Contact 1")
        contactList.add("Contact 2")
        contactList.add("Contact 3")

//        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, contactList)
//        contacts.adapter = adapter




        var addBtn = findViewById<Button>(R.id.Contacts)

        addBtn.setOnClickListener {
            val intent = Intent(this, AddContact::class.java)
            startActivity(intent)
        }





    }
}