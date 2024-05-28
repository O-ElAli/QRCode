package com.example.qrcode1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ConfirmContacts : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_confirm_contacts)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var intent = Intent(this, AddContact::class.java)

        val firstName = intent.getStringExtra("firstName")
        val lastName = intent.getStringExtra("lastName")
        val phoneNumber = intent.getStringExtra("phoneNumber")

        // Display the contact information
        val contactInfo = findViewById<TextView>(R.id.contactInfo)

        contactInfo.text = "First Name: ${firstName}\nLast Name: ${lastName}\nPhone Number: ${phoneNumber}"

        val confirm = findViewById<Button>(R.id.confirm)
        val cancel = findViewById<Button>(R.id.cancel)

        // Set an OnClickListener for the confirm button
        confirm.setOnClickListener {
            // Add the contact to the list
            Contacts.contactList.add("${firstName}%${lastName}%${phoneNumber}")
            // Return to the contacts page
            startActivity(Intent(this, Contacts::class.java))
        }

        // Set an OnClickListener for the cancel button
        cancel.setOnClickListener {
            // Return to the add contact page
            startActivity(Intent(this, AddContact::class.java))
        }


    }
}