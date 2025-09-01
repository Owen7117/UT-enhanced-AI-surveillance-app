package com.owenoneil.aisecuritycamera

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class AlertsPageActivity : AppCompatActivity() {

    private lateinit var btnHamburger: ImageButton
    private lateinit var customMenu: View
    private lateinit var profileDropdown: View

    // Bottom nav buttons
    private lateinit var btnHome: Button
    private lateinit var btnDevices: Button
    private lateinit var btnAlerts: Button
    private lateinit var btnHistory: Button
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

        // Initialize views
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
        alertsContainer = findViewById(R.id.alertsContainerAlerts)
        btnAddAlert = findViewById(R.id.btnAddAlert)

        database = DevicesDatabase.getInstance(this)

        setupMenu()
        setupBottomNav()
        loadSavedAlerts()

        btnAddAlert.setOnClickListener {
            addNewAlert()
        }
    }

    private fun setupMenu() {
        btnHamburger.setOnClickListener {
            customMenu.visibility = if (customMenu.visibility == View.GONE) View.VISIBLE else View.GONE
        }
        menuProfile.setOnClickListener {
            profileDropdown.visibility = if (profileDropdown.visibility == View.GONE) View.VISIBLE else View.GONE
        }
        btnlogin.setOnClickListener {
            startActivity(Intent(this, LoginPageActivity::class.java))
        }
    }

    private fun setupBottomNav() {
        btnHome.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        btnDevices.setOnClickListener { startActivity(Intent(this, DevicesPageActivity::class.java)) }
        btnAlerts.setOnClickListener { /* Already on Alerts */ }
        btnHistory.setOnClickListener { startActivity(Intent(this, HistoryPageActivity::class.java)) }
    }

    private fun loadSavedAlerts() {
        lifecycleScope.launch {
            val savedAlerts = database.alertDao().getAllAlerts()
            for (alert in savedAlerts) {
                addAlertButton(alert.type)
            }
        }
    }

    private fun addNewAlert() {
        val randomType = alertTypes.random()
        val newAlert = AlertEntity(type = randomType)
        val historyAlert = HistoryAlertEntity(type = randomType)

        lifecycleScope.launch {
            // Insert into Alerts table
            val alertId = database.alertDao().insertAlert(newAlert)
            addAlertButton(newAlert.copy(id = alertId.toInt()).type)

            // Insert into History table
            database.historyAlertDao().insertHistoryAlert(historyAlert)
        }
    }

    private fun addAlertButton(type: String) {
        val alertButton = Button(this).apply {
            text = type
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { setMargins(0, 8, 0, 8) }
        }
        alertsContainer.addView(alertButton)
    }
}
