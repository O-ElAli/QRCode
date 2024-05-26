package com.example.qrcode1

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter

class Registration : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var password_confirm: EditText
    private lateinit var registerButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        auth = FirebaseAuth.getInstance()

        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        password_confirm = findViewById(R.id.confirm_password)
        registerButton = findViewById(R.id.signup)

        registerButton.setOnClickListener {
            val email = email.text.toString()
            val password = password.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                if(password == password_confirm.text.toString()) {
                    registerUser(email, password)
                } else {
                    Toast.makeText(this, "The Passwords do not match", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Registration successful
                    Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                    // Optionally, redirect to another activity or clear the input fields
                } else {
                    // Registration failed
                    Toast.makeText(
                        this,
                        "Registration failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}