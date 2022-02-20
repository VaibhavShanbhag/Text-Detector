package com.example.textdetector

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        capturebtn.setOnClickListener {
            Intent(this, ScannerActivity::class.java).also {
                startActivity(it)
            }
        }

    }
}