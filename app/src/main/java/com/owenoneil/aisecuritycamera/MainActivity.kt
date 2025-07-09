package com.owenoneil.aisecuritycamera

import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var btnHamburger: ImageButton
    private lateinit var customMenu: View

    // Add buttons from bottom nav
    private lateinit var btnHome: Button
    private lateinit var btnDevices: Button
    private lateinit var btnAlerts: Button
    private lateinit var btnHistory: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Handle system bars padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Find views
        btnHamburger = findViewById(R.id.btnHamburger)
        customMenu = findViewById(R.id.customMenu)

        btnHome = findViewById(R.id.btnHome)
        btnDevices = findViewById(R.id.btnDevices)
        btnAlerts = findViewById(R.id.btnAlerts)
        btnHistory = findViewById(R.id.btnHistory)

        // Toggle dropdown menu visibility on hamburger click
        btnHamburger.setOnClickListener {
            if (customMenu.visibility == View.GONE) {
                customMenu.visibility = View.VISIBLE
            } else {
                customMenu.visibility = View.GONE
            }
        }

        // Set Home button as active on start
        setActiveButton(btnHome)
    }

    private fun setActiveButton(activeButton: Button) {
        val buttons = listOf(btnHome, btnDevices, btnAlerts, btnHistory)
        buttons.forEach { btn ->
            if (btn == activeButton) {
                btn.paintFlags = btn.paintFlags or Paint.UNDERLINE_TEXT_FLAG
                btn.setTextColor(android.graphics.Color.WHITE)
            } else {
                btn.paintFlags = btn.paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv()
                btn.setTextColor(android.graphics.Color.parseColor("#FFFFFF"))
            }
        }
    }
}