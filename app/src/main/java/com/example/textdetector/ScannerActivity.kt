package com.example.textdetector

import android.Manifest.permission.CAMERA
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_scanner.*

class ScannerActivity : AppCompatActivity() {

    private lateinit var imageBitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)

        snap.setOnClickListener {
            if (checkPermission()){
                captureImage()
            }

            else{
                requestPermission()
            }
        }

        detect.setOnClickListener {
            detectText()
        }


    }

    private fun checkPermission(): Boolean{
        val cameraPermission = ContextCompat.checkSelfPermission(applicationContext, CAMERA)
        return cameraPermission == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        val PermissionCode = 200
        ActivityCompat.requestPermissions(this, arrayOf(CAMERA), PermissionCode)

    }

    private fun captureImage(){
        val takePict = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePict.resolveActivity(packageManager) != null){
            startActivityForResult(takePict, RequestImageCapture)
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.size > 0){
            val cameraPermisson: Boolean = grantResults[0] == PackageManager.PERMISSION_GRANTED

            if (cameraPermisson){
                Toast.makeText(this,"Permission Granted", Toast.LENGTH_SHORT).show()
                captureImage()
            }

            else{
                Toast.makeText(this,"Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RequestImageCapture && resultCode == RESULT_OK){
            val extras: Bundle? = data?.extras
            imageBitmap = extras?.get("data") as Bitmap
            image.setImageBitmap(imageBitmap)
        }
    }

    private fun detectText() {

    }

    companion object {
        const val RequestImageCapture = 1
    }
}