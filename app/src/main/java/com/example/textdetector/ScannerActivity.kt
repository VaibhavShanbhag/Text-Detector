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
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.TextRecognizerOptions
import kotlinx.android.synthetic.main.activity_scanner.*
import java.lang.StringBuilder

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
        val image: InputImage = InputImage.fromBitmap(imageBitmap,0)
        val recongniser: TextRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        val result: Task<Text> = recongniser.process(image).addOnSuccessListener{
            val result: StringBuilder? = null
            for (block in it.textBlocks){
                val blockText = block.text
                val blockCornerPoints = block.cornerPoints
                val blockFrame = block.boundingBox
                for (line in block.lines){
                    val lineText = line.text
                    val lineCornerPoints = line.cornerPoints
                    val lineFrame = line.boundingBox
                    for (element in line.elements) {
                        val elementText = element.text
                        result?.append(elementText)
                    }
                    resulttext.text = blockText
                }
            }
        }.addOnFailureListener {
            Toast.makeText(this,"Failed to detect text from image ${it.localizedMessage}",Toast.LENGTH_SHORT).show()
        }

    }

    companion object {
        const val RequestImageCapture = 1
    }
}