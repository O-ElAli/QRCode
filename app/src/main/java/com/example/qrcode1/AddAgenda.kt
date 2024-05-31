package com.example.qrcode1

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AddAgenda: AppCompatActivity() {

    private lateinit var database : FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var submit : Button
    private lateinit var title : EditText
    private lateinit var task : EditText
    private lateinit var time: EditText
    private lateinit var date: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_agenda)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        submit = findViewById(R.id.submit)

        submit.setOnClickListener {
            title = findViewById(R.id.title)
            task = findViewById(R.id.task)
            time = findViewById(R.id.time)
            date = findViewById(R.id.date)

            database = FirebaseDatabase.getInstance()
            auth = FirebaseAuth.getInstance()

            val userId = auth.currentUser?.uid

            if(userId != null){
                val userRef = database.getReference("agenda").child(userId).push()

                val agenda = Agenda(
                    title.text.toString(),
                    task.text.toString(),
                    time.text.toString(),
                    date.text.toString()
                )

                userRef.setValue(agenda)
                Log.e("AddContact", "User added")
            } else  {
                Log.e("AddContact", "User does not exist")
            }

        }

    }
}