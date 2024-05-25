package com.example.qrcode1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.MutableLiveData

class editInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_info)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var saveBtn = findViewById<Button>(R.id.editInfo)
        var firstName = findViewById<EditText>(R.id.firstName)
        var lastName = findViewById<EditText>(R.id.lastName)
        var phoneNumber = findViewById<EditText>(R.id.phoneNumber)

        //ccreate instance of mediator
        val sharedViewModel: SharedViewModel by viewModels()





        saveBtn.setOnClickListener{
            //save the data to the database
            sharedViewModel.setFirstName(firstName.text.toString())
            sharedViewModel.setLastName(lastName.text.toString())
            sharedViewModel.setPhoneNumber(phoneNumber.text.toString())
            var allData = sharedViewModel.getAllData()
            Toast.makeText(this, allData, Toast.LENGTH_SHORT).show()
            //Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show()

        }


    }
}