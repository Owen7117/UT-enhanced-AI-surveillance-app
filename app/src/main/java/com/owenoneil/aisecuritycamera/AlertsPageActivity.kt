package com.owenoneil.aisecuritycamera

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch
import java.time.LocalDate

class AlertsPageActivity : AppCompatActivity() {

    private lateinit var btnHamburger: ImageButton
    private lateinit var customMenu: View
    private lateinit var btnHome: Button
    private lateinit var btnDevices: Button
    private lateinit var btnAlerts: Button
    private lateinit var btnHistory: Button
    private lateinit var alertsContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_alerts_page)

        btnHamburger = findViewById(R.id.btnHamburger)
        customMenu = findViewById(R.id.customMenu)
        btnHome = findViewById(R.id.btnHome)
        btnDevices = findViewById(R.id.btnDevices)
        btnAlerts = findViewById(R.id.btnAlerts)
        btnHistory = findViewById(R.id.btnHistory)
        alertsContainer = findViewById(R.id.alertsContainerTempHistory)

        btnHamburger.setOnClickListener {
            customMenu.visibility =
                if (customMenu.visibility == View.GONE) View.VISIBLE else View.GONE
        }

        btnHome.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        btnDevices.setOnClickListener {
            startActivity(Intent(this, DevicesPageActivity::class.java))
        }
        btnHistory.setOnClickListener {
            startActivity(Intent(this, HistoryPageActivity::class.java))
        }
        btnAlerts.setOnClickListener { /* already here */ }

        loadTodayAlerts()
    }

    private fun loadTodayAlerts() {
        lifecycleScope.launch {
            try {
                val today = LocalDate.now().toString() // "2026-02-02"

                val alerts = SupabaseClientProvider.client
                    .from("history")
                    .select()
                    .decodeList<HistoryAlert>()
                    .filter {
                        it.created_at.startsWith(today)
                    }

                alertsContainer.removeAllViews()

                if (alerts.isEmpty()) {
                    addEmptyView()
                } else {
                    alerts.forEach { addAlertRow(it) }
                }

            } catch (e: Exception) {
                Log.e("ALERTS", "Failed to load alerts", e)
            }
        }
    }

    private fun addAlertRow(alert: HistoryAlert) {
        val displayTime = alert.created_at.replace("T", " ")
        val rowButton = Button(this).apply {
            text = "${alert.alert}: ${displayTime} "
            isAllCaps = false
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 8, 0, 8)
            }
        }
        alertsContainer.addView(rowButton)
    }

    private fun addEmptyView() {
        val emptyView = Button(this).apply {
            text = "No alerts for today"
            isEnabled = false
        }
        alertsContainer.addView(emptyView)
    }
}
