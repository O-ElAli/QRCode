package com.example.qrcode1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class AddContact : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_contact)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set an OnClickListener for the scan QR button
        findViewById<Button>(R.id.scanQR).setOnClickListener {
            val scanner = IntentIntegrator(this)
            scanner.initiateScan()
        }
    }

    // Handle the result of the scan
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result: IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result.contents != null) {
            // Handle the scanned data here
            Toast.makeText(this, "Scanned content: ${result.contents}", Toast.LENGTH_SHORT).show()
            var resultString = result.contents

            // Split the result string into individual fields
            val fields = resultString.split("%")

            val firstName = fields[0]
            val lastName = fields[1]
            val phoneNumber = fields[2]

            //Toast.makeText(this, "First Name: ${firstName}, Last Name: ${lastName}, Phone Number: ${phoneNumber}", Toast.LENGTH_SHORT).show()

            var intent = Intent(this, ConfirmContacts::class.java)

            intent.putExtra("firstName", firstName)
            intent.putExtra("lastName", lastName)
            intent.putExtra("phoneNumber", phoneNumber)

            startActivity(intent)

        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}