package com.example.qrcode1

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe  // Import LiveData.observe


class GenerateQrCode : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate_qr_code)

        // Initialize the views
        var QRCode = findViewById<ImageView>(R.id.QRCode)
//        var qrGenerator = findViewById<Button>(R.id.QRGenerator)

        // create multiple variables for first name last name and phone number
        // then create a variable to store all of them together
        // then create a qr code based on the final variable

        // Get data from mediator class
        val data = editInfo.allData

        Toast.makeText(this, data, Toast.LENGTH_SHORT).show()


        if (data.isEmpty()) {
            Toast.makeText(this, "Please enter data in the EditInfo activity", Toast.LENGTH_SHORT)
                .show()
        } else {
            val qrCodeWriter = QRCodeWriter()
            try {
                val bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 512, 512)
                val width = bitMatrix.width
                val height = bitMatrix.height
                val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
                for (x in 0 until width) {
                    for (y in 0 until height) {
                        bmp.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                    }
                }
                QRCode.setImageBitmap(bmp)
            } catch (e: WriterException) {
                e.printStackTrace()
            }
        }



    }
}