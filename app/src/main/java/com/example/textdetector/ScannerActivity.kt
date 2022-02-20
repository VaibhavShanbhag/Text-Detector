package com.example.textdetector

import android.Manifest.permission.CAMERA
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_scanner.*

class ScannerActivity : AppCompatActivity() {

    private lateinit var imageBitmap: Bitmap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)

        snap.setOnClickListener {

        }

        detect.setOnClickListener {
            detectText()
        }


    }

    private fun checkPermission(): Boolean{
        val cameraPermission = ContextCompat.checkSelfPermission(applicationContext,CAMERA)
        return cameraPermission == PackageManager.PERMISSION_GRANTED
    }

    private fun detectText() {

    }
}