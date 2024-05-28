package com.example.qrcode1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class editInfo : AppCompatActivity() {

    private lateinit var fname: EditText
    private lateinit var lname: EditText
    private lateinit var phone: EditText
    private lateinit var submit: Button

    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_info)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        fname = findViewById(R.id.firstName)
        lname = findViewById(R.id.lastName)
        phone = findViewById(R.id.phoneNumber)
        submit = findViewById(R.id.editInfo)

        val MainPageIntent = Intent(this, MainPage::class.java)

        submit.setOnClickListener {
            val fname = fname.text.toString()
            val lname = lname.text.toString()
            val phone = phone.text.toString()

            if(fname.isEmpty() || lname.isEmpty() || phone.isEmpty()){
                Toast.makeText(this, "Please fill in all fields if you want to edit your info", Toast.LENGTH_SHORT).show()
            } else {
                database = FirebaseDatabase.getInstance()
                auth = FirebaseAuth.getInstance()
                val userId = auth.currentUser?.uid

                if (userId != null){
                    val userRef = database.getReference("users").child(userId)
                    val userMap = hashMapOf<String, Any>(
                        "firstName" to fname,
                        "lastName" to lname,
                        "phoneNumber" to phone
                    )
                    userRef.updateChildren(userMap).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            startActivity(MainPageIntent)
                            Toast.makeText(this, "Information updated successfully", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Failed to update information: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                            Log.e("FirebaseDatabase", "Failed to update information", task.exception)
                        }
                    }
                } else {
                    Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
                    Log.e("FirebaseDatabase", "User ID is null")
                }
            }
        }
    }
}

