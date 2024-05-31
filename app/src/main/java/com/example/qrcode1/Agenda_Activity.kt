package com.example.qrcode1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Agenda_Activity : AppCompatActivity() {

    private lateinit var addAgedna: Button
    private lateinit var listView: ListView
    private lateinit var database: FirebaseDatabase
    private lateinit var agendaList: MutableList<Agenda>
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_agenda)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val currentUser = auth.currentUser?.uid
        if (currentUser != null) {
            val userRef = database.getReference("agenda").child(currentUser)

            userRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    agendaList.clear()
                    for (data in snapshot.children) {
                        val agenda = data.getValue(Agenda::class.java)
                        if (agenda != null) {
                            agendaList.add(agenda)
                        }
                    }

                    val adapter = ArrayAdapter(
                        this@Agenda_Activity,
                        android.R.layout.simple_list_item_1,
                        agendaList.map { "${it.title} \n ${it.task} \n ${it.date} - ${it.time}" }
                    )
                    listView.adapter = adapter
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("ContactsActivity", "Failed to read contacts", error.toException())
                }
            })
        } else {
            Log.e("ContactsActivity", "Current user is null")
        }

        addAgedna = findViewById(R.id.addAgenda)

        addAgedna.setOnClickListener {
            val intent = Intent(this, AddAgenda::class.java)
            startActivity(intent)
        }

    }
}