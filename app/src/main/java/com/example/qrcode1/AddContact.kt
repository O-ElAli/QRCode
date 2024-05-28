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
            Log.d("QRCodeScanner", "Scanned content: " + result.contents)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}