package com.example.qrcode1

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainPageAdmin : AppCompatActivity() {

    private lateinit var database : FirebaseDatabase
    private lateinit var userDataTextView: TextView
    private var userId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)


        var phoneID = findViewById<EditText>(R.id.userID)
        var searchBtn = findViewById<Button>(R.id.searchUser)

        var firstName = findViewById<EditText>(R.id.firstName)
        var lastName = findViewById<EditText>(R.id.lastName)
        var phoneNumber = findViewById<EditText>(R.id.phoneNumber)

        var addBtn = findViewById<Button>(R.id.addContact)

        userDataTextView = findViewById(R.id.userInfo)

        searchBtn.setOnClickListener {
            val userIDStr = phoneID.text.toString()

            if (userIDStr.isNotEmpty()) {
                database = FirebaseDatabase.getInstance()
                val userRef = database.getReference("users")

                userRef.orderByChild("phoneNumber").equalTo(userIDStr)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if(snapshot.exists()) {
                                for (userSnapshot in snapshot.children) {
                                    userId = userSnapshot.key
                                    val firstName = userSnapshot.child("firstName").getValue(String::class.java)
                                    val lastName = userSnapshot.child("lastName").getValue(String::class.java)

                                    userDataTextView.text = "First Name: $firstName\nLast Name: $lastName"
                                    // Make the TextView visible
                                    userDataTextView.visibility = View.VISIBLE
                                }
                            } else {
                                println("User not found")
                                Toast.makeText(this@MainPageAdmin, "User not found", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Log.e("MainPageAdmin", "Failed to read user data", error.toException())
                        }
                    })
            }
        }

        // Add the information to the user contacts
        addBtn.setOnClickListener(View.OnClickListener {
            val firstNameStr = firstName.text.toString()
            val lastNameStr = lastName.text.toString()
            val phoneNumberStr = phoneNumber.text.toString()

            if (firstNameStr.isNotEmpty() && lastNameStr.isNotEmpty() && phoneNumberStr.isNotEmpty()) {
                if (userId != null) {
                    database = FirebaseDatabase.getInstance()
                    val contactRef = database.getReference("contacts").child(userId!!).push()

                    val contactMap = hashMapOf<String, Any>(
                        "firstName" to firstNameStr,
                        "lastName" to lastNameStr,
                        "phoneNumber" to phoneNumberStr
                    )

                    contactRef.setValue(contactMap).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Contact added successfully", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Failed to add contact: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "User ID not found, please search for a user first", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        })
    }
}