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
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter

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

        val contacts = Intent(this, Contacts::class.java)

        val agenda = Intent(this, Agenda::class.java)

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
            startActivity(agenda)
        }


    }



}
