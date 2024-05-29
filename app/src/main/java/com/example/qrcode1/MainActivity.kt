package com.example.qrcode1

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter

public class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loginUser: Button
    private lateinit var loginAdmin: Button
    private lateinit var signUp: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        // Initialize UI elements
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        loginUser = findViewById(R.id.userLogin)
        loginAdmin = findViewById(R.id.adminLogin)
        signUp = findViewById(R.id.signUp)

        // Intents for navigation
        val userIntent = Intent(this, MainPage::class.java)
        val registerIntent = Intent(this, Registration::class.java)
        //val adminIntent = Intent(this, MainPageAdmin::class.java)


        loginUser.setOnClickListener {
            val emailStr = email.text.toString()
            val passwordStr = password.text.toString()

            if (emailStr.isNotEmpty() && passwordStr.isNotEmpty()) {
                auth.signInWithEmailAndPassword(emailStr, passwordStr).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = auth.currentUser?.uid
                        if (userId != null) {
                            database.getReference("users").child(userId).child("role")
                                .addListenerForSingleValueEvent(object : ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        val role = snapshot.value as String
                                        if (role == "user") {
                                            startActivity(userIntent)
                                            Toast.makeText(this@MainActivity, "User Logged In", Toast.LENGTH_SHORT).show()
                                        } else {
                                            Toast.makeText(this@MainActivity, "Invalid credentials for User", Toast.LENGTH_SHORT).show()
                                        }
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                        Log.e("DatabaseError", error.message)
                                    }
                                })
                        }
                    } else {
                        Toast.makeText(this, "Login Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }

        loginAdmin.setOnClickListener {
            val emailStr = email.text.toString()
            val passwordStr = password.text.toString()

            if (emailStr.isNotEmpty() && passwordStr.isNotEmpty()) {
                auth.signInWithEmailAndPassword(emailStr, passwordStr).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = auth.currentUser?.uid
                        if (userId != null) {
                            database.getReference("users").child(userId).child("role")
                                .addListenerForSingleValueEvent(object : ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        val role = snapshot.value as String
                                        if (role == "admin") {
                                            //startActivity(adminIntent)
                                            Toast.makeText(this@MainActivity, "Admin Logged In", Toast.LENGTH_SHORT).show()
                                        } else {
                                            Toast.makeText(this@MainActivity, "Invalid credentials for Admin", Toast.LENGTH_SHORT).show()
                                        }
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                        Log.e("DatabaseError", error.message)
                                    }
                                })
                        }
                    } else {
                        Toast.makeText(this, "Login Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }


        signUp.setOnClickListener {
            startActivity(registerIntent)
        }
    }
}
