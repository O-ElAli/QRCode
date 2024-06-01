package com.example.qrcode1

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class DetailedAgenda : AppCompatActivity() {

    private lateinit var dlt : Button
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_agenda)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val id = intent.getStringExtra("id")
        val title = intent.getStringExtra("title")
        val task = intent.getStringExtra("task")
        val date = intent.getStringExtra("date")
        val time = intent.getStringExtra("time")

        // Find the EditText fields in the layout and set the data
        val titleEditText = findViewById<TextView>(R.id.title)
        val taskEditText = findViewById<TextView>(R.id.task)
        val dateEditText = findViewById<TextView>(R.id.date)
        val timeEditText = findViewById<TextView>(R.id.time)

        titleEditText.setText(title)
        taskEditText.setText(task)
        dateEditText.setText(date)
        timeEditText.setText(time)

        dlt = findViewById(R.id.submit)

        dlt.setOnClickListener {
            if (id != null) {
                database = FirebaseDatabase.getInstance()
                auth = FirebaseAuth.getInstance()
                val currentUser = auth.currentUser?.uid
                if (currentUser != null) {
                    val itemRef = database.getReference("agenda").child(currentUser).child(id)
                    itemRef.removeValue().addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Agenda deleted", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(this, "Failed to delete agenda", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Log.e("DetailedAgenda", "Current user is null")
                }
            } else {
                Log.e("DetailedAgenda", "Agenda ID is null")
            }
        }

    }


}