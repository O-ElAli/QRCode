package com.example.qrcode1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class editInfo : AppCompatActivity() {

    //create static variables to store the data and access it from other activities
    companion object{
        var allData = ""
        var fname = ""
        var lname = ""
        var pnumber = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_info)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val saveBtn = findViewById<Button>(R.id.editInfo) // Corrected button ID if necessary


        saveBtn.setOnClickListener {

            fname = findViewById<EditText>(R.id.firstName).text.toString()
            lname = findViewById<EditText>(R.id.lastName).text.toString()
            pnumber = findViewById<EditText>(R.id.phoneNumber).text.toString()
            allData = "$fname%$lname%$pnumber".trim()

            Toast.makeText(this, "Data saved: $allData}", Toast.LENGTH_SHORT).show()

        }

    }
}
