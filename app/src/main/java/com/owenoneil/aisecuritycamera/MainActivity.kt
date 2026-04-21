package com.owenoneil.aisecuritycamera

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {


    private lateinit var btnHome: Button
    private lateinit var btnAlerts: Button
    private lateinit var btnHistory: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        btnHome = findViewById(R.id.btnHome)
        btnAlerts = findViewById(R.id.btnAlerts)
        btnHistory = findViewById(R.id.btnHistory)

        btnAlerts.setOnClickListener {
            startActivity(Intent(this, AlertsPageActivity::class.java))
        }

        btnHistory.setOnClickListener {
            startActivity(Intent(this, HistoryPageActivity::class.java))
        }
    }
}