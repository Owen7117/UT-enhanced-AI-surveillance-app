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

    private lateinit var btnHome: Button
    private lateinit var btnAlerts: Button
    private lateinit var btnHistory: Button
    private lateinit var alertsContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_alerts_page)


        btnHome = findViewById(R.id.btnHome)
        btnAlerts = findViewById(R.id.btnAlerts)
        btnHistory = findViewById(R.id.btnHistory)
        alertsContainer = findViewById(R.id.alertsContainerTempHistory)


        btnHome.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        btnHistory.setOnClickListener {
            startActivity(Intent(this, HistoryPageActivity::class.java))
        }
        btnAlerts.setOnClickListener { }

        loadTodayAlerts()
    }

    private fun loadTodayAlerts() {
        lifecycleScope.launch {
            try {
                val today = LocalDate.now().toString()

                Log.d("ALERTS", "Loading alerts for $today")

                val alerts = SupabaseClientProvider.client
                    .from("history")
                    .select()
                    .decodeList<HistoryAlert>()
                    .filter {
                        it.created_at.startsWith(today)
                    }

                Log.d("ALERTS", "Alerts found: ${alerts.size}")

                alertsContainer.removeAllViews()

                if (alerts.isEmpty()) {
                    addEmptyView()
                } else {
                    alerts.forEach { addAlertRow(it) }
                }

            } catch (e: Exception) {
                Log.e("ALERTS", "Failed to load alerts", e)

                alertsContainer.removeAllViews()
                addEmptyView()
            }
        }
    }

    private fun addAlertRow(alert: HistoryAlert) {
        val displayTime = alert.created_at.replace("T", " ")
        val rowButton = Button(this).apply {
            text = "${alert.alert}\nTime: $displayTime\nDanger: ${alert.danger_level}"
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
