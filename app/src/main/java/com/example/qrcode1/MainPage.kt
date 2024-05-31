package com.example.qrcode1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)

        //button sends to page called GenerateQrCode
        //call the function when the button is clicked
        var qrBtn = findViewById<Button>(R.id.QRCode)
        var editBtn = findViewById<Button>(R.id.Edit)
        var contactsBtn = findViewById<Button>(R.id.Contacts)
        var agendaBtn = findViewById<Button>(R.id.Agenda)


        val qrCode = Intent (this, GenerateQrCode::class.java)

        val editInfo = Intent (this, editInfo::class.java)

        val contacts = Intent(this, ContactsActivity::class.java)

        val agendaActivity = Intent(this, Agenda_Activity::class.java)

        editBtn.setOnClickListener {
            startActivity(editInfo)
        }

        qrBtn.setOnClickListener {
            startActivity(qrCode)
        }

        contactsBtn.setOnClickListener {
            startActivity(contacts)
        }

        agendaBtn.setOnClickListener {
            startActivity(agendaActivity)
        }


    }



}
