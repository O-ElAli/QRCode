//package com.example.qrcode1
//
//import android.os.Bundle
//import android.view.View
//import android.widget.Button
//import android.widget.EditText
//import androidx.appcompat.app.AppCompatActivity
//import com.google.firebase.database.DataSnapshot
//import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.database.ValueEventListener
//
//class MainPageAdmin : AppCompatActivity() {
//
//    private lateinit var database : FirebaseDatabase
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_admin)
//
//
//        var phoneID = findViewById<EditText>(R.id.userID)
//        var searchBtn = findViewById<Button>(R.id.searchUser)
//
//        var firstName = findViewById<EditText>(R.id.firstName)
//        var lastName = findViewById<EditText>(R.id.lastName)
//        var phoneNumber = findViewById<EditText>(R.id.phoneNumber)
//
//        var addBtn = findViewById<Button>(R.id.addContact)
//
//        searchBtn.setOnClickListener(View.OnClickListener {
//            val userIDStr = phoneID.text.toString()
//
//            if (userIDStr.isNotEmpty()) {
//                database = FirebaseDatabase.getInstance()
//                val userRef = database.getReference("users")
//
//                userRef.orderByChild("phoneNumber").equalTo(userIDStr).addListenerForSingleValueEvent(object : ValueEventListener {
//                        override fun onDataChange(snapshot: DataSnapshot) {
//                            for (userSnapshot in snapshot.children) {
//                                val userId = userSnapshot.key
//                                println("User ID: $userId")
//                            }
//                        }
//                    })
//            }
//        })
//
//        // Add the information to the user contacts
//        addBtn.setOnClickListener(View.OnClickListener {
//            val firstNameStr = firstName.text.toString()
//            val lastNameStr = lastName.text.toString()
//            val phoneNumberStr = phoneNumber.text.toString()
//
//            if (firstNameStr.isNotEmpty() && lastNameStr.isNotEmpty() && phoneNumberStr.isNotEmpty()) {
//                // Add the contact to the user's contact list
//            }
//        })
//    }
//}