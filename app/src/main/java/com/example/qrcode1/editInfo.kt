package com.example.qrcode1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class editInfo : AppCompatActivity() {
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_info)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val saveBtn = findViewById<Button>(R.id.editInfo) // Corrected button ID if necessary
        val firstName = findViewById<EditText>(R.id.firstName)
        val lastName = findViewById<EditText>(R.id.lastName)
        val phoneNumber = findViewById<EditText>(R.id.phoneNumber)

        // Observing the combinedData for changes
        sharedViewModel.combinedData.observe(this) { combinedData ->
            // Optionally, show the combined data in a Toast or log
            Toast.makeText(this, "Updated Info: $combinedData", Toast.LENGTH_SHORT).show()
        }

        // Instead of observing in onCreate, only show a toast when saving data
        saveBtn.setOnClickListener {
            if (firstName.text.isNotEmpty() && lastName.text.isNotEmpty() && phoneNumber.text.isNotEmpty()) {
                sharedViewModel.firstName.value = firstName.text.toString()
                sharedViewModel.lastName.value = lastName.text.toString()
                sharedViewModel.phoneNumber.value = phoneNumber.text.toString()
                Toast.makeText(this, "Data saved: ${sharedViewModel.formatData()}", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
