package com.example.qrcode1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ConfirmContacts : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var data: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_confirm_contacts)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var intent = intent

        val firstName = intent.getStringExtra("firstName")
        val lastName = intent.getStringExtra("lastName")
        val phoneNumber = intent.getStringExtra("phoneNumber")

        Toast.makeText(this, "First Name: ${firstName}, Last Name: ${lastName}, Phone Number: ${phoneNumber}", Toast.LENGTH_SHORT).show()

        // Display the contact information
        val contactInfo = findViewById<TextView>(R.id.contactInfo)

        contactInfo.text = "First Name: ${firstName}\nLast Name: ${lastName}\nPhone Number: ${phoneNumber}"

        val confirm = findViewById<Button>(R.id.confirm)
        val cancel = findViewById<Button>(R.id.cancel)

        // Set an OnClickListener for the confirm button
        confirm.setOnClickListener {
            // Add the contact to the list
            //Contacts.contactList.add("${firstName}%${lastName}%${phoneNumber}")
            // Return to the contacts page
            if (firstName != null && lastName != null && phoneNumber != null) {
                auth = FirebaseAuth.getInstance()
                database = FirebaseDatabase.getInstance()

                val Current_user = auth.currentUser?.uid
                if (Current_user != null) {
                    val userRef = database.getReference("contacts").child(Current_user).push()

                    val userMap = hashMapOf<String, Any>(
                        "firstName" to firstName,
                        "lastName" to lastName,
                        "phoneNumber" to phoneNumber
                    )

                    userRef.setValue(userMap).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Contact saved successfully", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, Contacts::class.java))
                        } else {
                            Toast.makeText(this, "Failed to save contact: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                    startActivity(Intent(this, Contacts::class.java))
                } else {
                    Toast.makeText(this, "Current user isn't authenticated", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Some of the data added is missing", Toast.LENGTH_SHORT).show()
            }
            // Set an OnClickListener for the cancel button
            cancel.setOnClickListener {
                // Return to the add contact page
                startActivity(Intent(this, AddContact::class.java))
            }
        }
    }
}