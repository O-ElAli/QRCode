package com.example.qrcode1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
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
    private lateinit var agendaIds: MutableList<String>
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

        listView = findViewById(R.id.agendaList)
        addAgedna = findViewById(R.id.addAgenda)
        agendaList = mutableListOf()
        agendaIds = mutableListOf()

        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser?.uid
        if (currentUser != null) {
            val userRef = database.getReference("agenda").child(currentUser)

            userRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    agendaList.clear()
                    agendaIds.clear()
                    for (data in snapshot.children) {
                        val agenda = data.getValue(Agenda::class.java)
                        if (agenda != null) {
                            agendaList.add(agenda)
                            agendaIds.add(data.key ?: "")
                        }
                    }
                    if (agendaList.isEmpty()) {
                        Toast.makeText(this@Agenda_Activity, "No agenda items found", Toast.LENGTH_SHORT).show()
                    } else {
                        val adapter = ArrayAdapter(
                            this@Agenda_Activity,
                            android.R.layout.simple_list_item_1,
                            agendaList.map { "${it.title} \n ${it.task} \n ${it.date} - ${it.time}" }
                        )
                        listView.adapter = adapter
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Agenda_Activity", "Failed to read agenda items", error.toException())
                }
            })
        } else {
            Log.e("Agenda_Activity", "Current user is null")
        }

        addAgedna = findViewById(R.id.addAgenda)

        addAgedna.setOnClickListener {
            val intent = Intent(this, AddAgenda::class.java)
            startActivity(intent)
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedAgenda = agendaList[position]
            val selectedId = agendaIds[position]

            val intent = Intent(this, DetailedAgenda::class.java).apply {
                putExtra("id", selectedId)
                putExtra("title", selectedAgenda.title)
                putExtra("task", selectedAgenda.task)
                putExtra("date", selectedAgenda.date)
                putExtra("time", selectedAgenda.time)
            }
            startActivity(intent)
        }

    }
}