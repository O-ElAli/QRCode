package com.example.qrcode1

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter

class Registration : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var username: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var passwordConfirm: EditText
    private lateinit var registerButton: Button
    private lateinit var radioGroup: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        // Initialize Firebase
        FirebaseApp.initializeApp(this)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        username = findViewById(R.id.username)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        passwordConfirm = findViewById(R.id.confirm_password)
        registerButton = findViewById(R.id.signup)
        radioGroup = findViewById(R.id.radioGroup)

        registerButton.setOnClickListener {
            val usernameStr = username.text.toString()
            val emailStr = email.text.toString()
            val passwordStr = password.text.toString()
            val passwordConfirmStr = passwordConfirm.text.toString()

            if (emailStr.isNotEmpty() && passwordStr.isNotEmpty() && usernameStr.isNotEmpty()) {
                if (passwordStr == passwordConfirmStr) {
                    val selectedRoleId = radioGroup.checkedRadioButtonId
                    val userRole = if (selectedRoleId == R.id.radioAdmin) "admin" else "user"
                    registerUser(usernameStr, emailStr, passwordStr, userRole)
                } else {
                    val MainActivityIntent = Intent(this, MainActivity::class.java)
                    startActivity(MainActivityIntent)
                    Toast.makeText(this, "The passwords do not match", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter username, email, and password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerUser(username: String, email: String, password: String, role: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Registration successful
                    val user = auth.currentUser
                    val userId = user?.uid

                    if (userId != null) {
                        Log.d("FirebaseDatabase", "User ID: $userId")
                        val userRef = database.getReference("users").child(userId)
                        val userMap = mapOf(
                            "username" to username,
                            "email" to email,
                            "role" to role
                        )

                        userRef.setValue(userMap).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                                val MainActivityIntent = Intent(this, MainActivity::class.java)
                                startActivity(MainActivityIntent)
                            } else {
                                Toast.makeText(this, "Failed to save user data: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                                Log.e("Firebase", "Failed to save user data", task.exception)
                            }
                        }
                    } else {
                        Toast.makeText(this, "User ID is null", Toast.LENGTH_SHORT).show()
                        Log.e("Firebase", "User ID is null")
                    }
                } else {
                    // Registration failed
                    Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    Log.e("Firebase", "Registration failed", task.exception)
                }
            }
    }
}
