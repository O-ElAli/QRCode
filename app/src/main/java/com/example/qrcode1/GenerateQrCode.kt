package com.example.qrcode1

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter



class GenerateQrCode : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var QRCode: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate_qr_code)

        // Initialize Firebase Auth and Database
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        // Initialize the QRCode ImageView
        QRCode = findViewById(R.id.QRCode)

        // Fetch user info and generate QR code
        fetchUserInfoAndGenerateQRCode()
    }

    private fun fetchUserInfoAndGenerateQRCode() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val userRef = database.getReference("users").child(userId)

            userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val firstName = snapshot.child("firstName").getValue(String::class.java)
                    val lastName = snapshot.child("lastName").getValue(String::class.java)
                    val phoneNumber = snapshot.child("phoneNumber").getValue(String::class.java)

                    if (firstName != null && lastName != null && phoneNumber != null) {
                        // Combine the data into a single string
                        val data = "$firstName $lastName $phoneNumber"
                        // Generate the QR code with the retrieved data
                        generateQRCode(data)
                    } else {
                        Toast.makeText(this@GenerateQrCode, "Failed to retrieve user data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("DatabaseError", error.message)
                    Toast.makeText(this@GenerateQrCode, "Failed to read user info: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
        }
    }

    private fun generateQRCode(data: String) {
        if (data.isEmpty()) {
            Toast.makeText(this, "No data to generate QR code", Toast.LENGTH_SHORT).show()
        } else {
            val qrCodeWriter = QRCodeWriter()
            try {
                val bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 512, 512)
                val width = bitMatrix.width
                val height = bitMatrix.height
                val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
                for (x in 0 until width) {
                    for (y in 0 until height) {
                        bmp.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                    }
                }
                QRCode.setImageBitmap(bmp)
            } catch (e: WriterException) {
                e.printStackTrace()
                Toast.makeText(this, "Error generating QR code: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}