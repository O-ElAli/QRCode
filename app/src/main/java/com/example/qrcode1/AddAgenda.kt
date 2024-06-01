package com.example.qrcode1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Locale

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

        val intent = Intent(this, Agenda_Activity::class.java)

        submit = findViewById(R.id.submit)
        title = findViewById(R.id.title)
        task = findViewById(R.id.task)
        time = findViewById(R.id.time)
        date = findViewById(R.id.date)


        submit.setOnClickListener {
            database = FirebaseDatabase.getInstance()
            auth = FirebaseAuth.getInstance()

            val titleText = title.text.toString()
            val taskText = task.text.toString()
            val timeText = time.text.toString()
            val dateText = date.text.toString()

            if (validateInput(titleText, taskText, dateText, timeText)) {
                val userId = auth.currentUser?.uid

                if (userId != null) {
                    val userRef = database.getReference("agenda").child(userId).push()

                    val agenda = Agenda(
                        title = titleText,
                        task = taskText,
                        date = dateText,
                        time = timeText
                    )

                    userRef.setValue(agenda)
                    Log.e("AddAgenda", "Agenda added")
                } else {
                    Log.e("AddAgenda", "User does not exist")
                }

                startActivity(Intent(this, Agenda_Activity::class.java))
            }
        }
    }

    private fun validateInput(title: String, task: String, date: String, time: String): Boolean {
        if (title.isEmpty() || task.isEmpty() || date.isEmpty() || time.isEmpty()) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!isValidTime(time)) {
            Toast.makeText(this, "Invalid time format. Please use HH:mm", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!isValidDate(date)) {
            Toast.makeText(this, "Invalid date format. Please use yyyy-MM-dd", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun isValidTime(time: String): Boolean {
        return try {
            val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            timeFormat.isLenient = false
            timeFormat.parse(time)
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun isValidDate(date: String): Boolean {
        return try {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            dateFormat.isLenient = false
            dateFormat.parse(date)
            true
        } catch (e: Exception) {
            false
        }
    }
}