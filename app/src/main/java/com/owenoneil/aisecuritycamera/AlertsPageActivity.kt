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
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch


class AlertsPageActivity : AppCompatActivity() {

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
    private lateinit var alertsContainer: LinearLayout
    private lateinit var btnAddAlert: Button
    private lateinit var database: DevicesDatabase
    private val alertTypes = listOf("Person", "Potential Threat", "Motion")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_alerts_page)


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
        alertsContainer = findViewById(R.id.alertsContainer)
        btnAddAlert = findViewById(R.id.btnAddAlert)
        database = DevicesDatabase.getInstance(this)



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
        btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        btnDevices.setOnClickListener{
            val intent = Intent(this, DevicesPageActivity::class.java)
            startActivity(intent)
        }
        btnHistory.setOnClickListener {
            val intent = Intent(this, HistoryPageActivity::class.java)
            startActivity(intent)
        }


        // Load saved alerts from DB
        lifecycleScope.launch {
            val savedAlerts = database.alertDao().getAllAlerts()
            for (alert in savedAlerts) {
                addAlertButton(alert)
            }
        }


        btnAddAlert.setOnClickListener {
            val randomType = alertTypes.random()
            val newAlert = AlertEntity(type = randomType)

            lifecycleScope.launch {
                val id = database.alertDao().insertAlert(newAlert)
                // Add button to UI
                addAlertButton(newAlert.copy(id = id.toInt()))
            }
        }
    }


    private fun addAlertButton(alert: AlertEntity) {
        val alertButton = Button(this).apply {
            text = alert.type
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { setMargins(0, 8, 0, 8) }
        }
        alertsContainer.addView(alertButton)
    }
}
