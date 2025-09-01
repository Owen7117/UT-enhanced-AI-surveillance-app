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
import androidx.lifecycle.lifecycleScope
import com.owenoneil.aisecuritycamera.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryPageActivity : AppCompatActivity() {

    private lateinit var btnHamburger: ImageButton
    private lateinit var customMenu: View
    private lateinit var btnHome: Button
    private lateinit var btnDevices: Button
    private lateinit var btnAlerts: Button
    private lateinit var btnHistory: Button
    private lateinit var profileDropdown: View
    private lateinit var btnlogin: Button
    private lateinit var btnlogout: Button
    private lateinit var menuProfile: Button
    private lateinit var database: DevicesDatabase
    private lateinit var alertsContainerHistory: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_history_page)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Bind views
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
        alertsContainerHistory = findViewById(R.id.alertsContainerHistory)
        database = DevicesDatabase.getInstance(this)

        // Bottom nav listeners
        btnHamburger.setOnClickListener {
            customMenu.visibility = if (customMenu.visibility == View.GONE) View.VISIBLE else View.GONE
        }
        menuProfile.setOnClickListener {
            profileDropdown.visibility = if (profileDropdown.visibility == View.GONE) View.VISIBLE else View.GONE
        }
        btnlogin.setOnClickListener { startActivity(Intent(this, LoginPageActivity::class.java)) }
        btnHome.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        btnDevices.setOnClickListener { startActivity(Intent(this, DevicesPageActivity::class.java)) }
        btnAlerts.setOnClickListener { startActivity(Intent(this, AlertsPageActivity::class.java)) }

        // Load all history alerts
        lifecycleScope.launch {
            val historyAlerts = database.historyAlertDao().getAllHistoryAlerts()
            for (alert in historyAlerts) {
                addHistoryAlertButton(alert)
            }
        }
    }

    private fun addHistoryAlertButton(alert: HistoryAlertEntity) {
        val button = Button(this).apply {
            text = alert.type
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { setMargins(0, 8, 0, 8) }
        }
        alertsContainerHistory.addView(button)
    }
}
