//package com.example.qrcode1
//
//import android.os.Bundle
//import android.view.View
//import android.widget.Button
//import android.widget.EditText
//import androidx.appcompat.app.AppCompatActivity
//
//class MainPageAdmin : AppCompatActivity() {
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
//        //var addBtn = findViewById<Button>(R.id.addContact)
//
//        searchBtn.setOnClickListener(View.OnClickListener {
//            val userIDStr = phoneID.text.toString()
//
//            if (userIDStr.isNotEmpty()) {
//                // Search for the user in the database
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