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
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter

public class MainActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth
    private lateinit var imageView: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //button sends to page called GenerateQrCode
        //call the function when the button is clicked
        //var btn = findViewById<Button>(R.id.QRCode)
        var userBtn = findViewById<Button>(R.id.userLogin)
        var singupBtn = findViewById<Button>(R.id.signUp)
        var username_input = findViewById<EditText>(R.id.username)
        var password_input = findViewById<EditText>(R.id.password)
        val userIntent = Intent (this, MainPage::class.java)
        val registerIntent = Intent(this, Registration::class.java)


        userBtn.setOnClickListener {
            startActivity(userIntent)
            auth = FirebaseAuth.getInstance()
            var username = username_input.text.toString()
            var password = password_input.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()){
                auth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(
                    OnCompleteListener {
                        if (it.isSuccessful){
                            Toast.makeText(this, "User Created", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            }

        }

        singupBtn.setOnClickListener {
            startActivity(registerIntent)
        }
    }
}
