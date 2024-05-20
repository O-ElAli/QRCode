package com.example.qrcode1

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter

class MainActivity : AppCompatActivity() {
    private lateinit var QRCode: ImageView
    private lateinit var Data: EditText
    private lateinit var qrGenerator: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the views
        QRCode = findViewById(R.id.QRCode)
        Data = findViewById(R.id.Data)
        qrGenerator = findViewById(R.id.QRGenerator)

        qrGenerator.setOnClickListener {
            val data = Data.text.toString().trim()
            if (data.isEmpty()) {
                Toast.makeText(this, "Please enter data", Toast.LENGTH_SHORT).show()
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
}
