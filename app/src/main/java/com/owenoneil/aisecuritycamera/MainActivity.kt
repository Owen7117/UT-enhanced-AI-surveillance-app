package com.owenoneil.aisecuritycamera
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
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
    private lateinit var profileDropdown: View
    private lateinit var btnlogin: Button
    private lateinit var btnlogout: Button
    private lateinit var menuProfile: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        btnHamburger = findViewById(R.id.btnHamburger)
        customMenu = findViewById(R.id.customMenu)
        profileDropdown = findViewById(R.id.profileDropdown)
        btnHome = findViewById(R.id.btnHome)
        btnDevices = findViewById(R.id.btnDevices)
        btnAlerts = findViewById(R.id.btnAlerts)
        btnHistory = findViewById(R.id.btnHistory)
        btnlogin = findViewById(R.id.btnlogin)
        btnlogout = findViewById(R.id.btnlogout)
        menuProfile = findViewById(R.id.menuProfile)


        btnHamburger.setOnClickListener {
            if (customMenu.visibility == View.GONE) {
                customMenu.visibility = View.VISIBLE
            } else {
                customMenu.visibility = View.GONE
            }
        }
        menuProfile.setOnClickListener{
            if (profileDropdown.visibility == View.GONE){
                profileDropdown.visibility = View.VISIBLE
            } else{
                profileDropdown.visibility = View.GONE
            }
        }

        btnlogin.setOnClickListener{
            val intent = Intent(this,LoginPageActivity::class.java)
            startActivity(intent)
        }
        btnDevices.setOnClickListener{
            val intent = Intent(this,DevicesPageActivity::class.java)
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
    }
}