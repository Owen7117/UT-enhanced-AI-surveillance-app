package com.owenoneil.aisecuritycamera
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.owenoneil.aisecuritycamera.R



class DevicesPageActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_devices_page)

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
        btnHome.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        btnAlerts.setOnClickListener {
            val intent = Intent(this, AlertsPageActivity::class.java)
            startActivity(intent)
        }
        btnHistory.setOnClickListener {
            val intent = Intent(this, HistoryPageActivity::class.java)
            startActivity(intent)
        }
        val textView = findViewById<TextView>(R.id.tvCameras)
        val content = SpannableString("Cameras")
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        textView.text = content
    }
}