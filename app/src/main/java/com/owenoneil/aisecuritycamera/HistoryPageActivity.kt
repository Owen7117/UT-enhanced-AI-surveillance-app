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

class HistoryPageActivity : AppCompatActivity() {

    private lateinit var btnHamburger: ImageButton
    private lateinit var customMenu: View
    private lateinit var btnHome: Button
    private lateinit var btnDevices: Button
    private lateinit var btnAlerts: Button
    private lateinit var btnHistory: Button
    private lateinit var alertsContainerHistory: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_history_page)

        // Bind views
        btnHamburger = findViewById(R.id.btnHamburger)
        customMenu = findViewById(R.id.customMenu)
        btnHome = findViewById(R.id.btnHome)
        btnDevices = findViewById(R.id.btnDevices)
        btnAlerts = findViewById(R.id.btnAlerts)
        btnHistory = findViewById(R.id.btnHistory)
        alertsContainerHistory = findViewById(R.id.alertsContainerHistory)

        // Hamburger menu toggle
        btnHamburger.setOnClickListener {
            customMenu.visibility =
                if (customMenu.visibility == View.GONE) View.VISIBLE else View.GONE
        }

        // Bottom navigation
        btnHome.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        btnDevices.setOnClickListener {
            startActivity(Intent(this, DevicesPageActivity::class.java))
        }
        btnAlerts.setOnClickListener {
            startActivity(Intent(this, AlertsPageActivity::class.java))
        }
        btnHistory.setOnClickListener { /* already here */ }

        // Load history
        loadHistory()
    }

    private fun loadHistory() {

        lifecycleScope.launch {
            try {
                val historyList = SupabaseClientProvider.client
                    .from("history")
                    .select()
                    .decodeList<HistoryAlert>()

                alertsContainerHistory.removeAllViews()

                if (historyList.isEmpty()) {
                    addEmptyView()
                } else {
                    historyList.forEach { addHistoryRow(it) }
                }

            } catch (e: Exception) {
                Log.e("HISTORY", "Failed to load history", e)
            }
        }
    }

    private fun addHistoryRow(alert: HistoryAlert) {
        val displayTime = alert.created_at.replace("T", " ")
        val rowButton = Button(this).apply {
            text = "${alert.alert}: ${displayTime} "
            isAllCaps = false
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 10, 0, 10)
            }
        }
        alertsContainerHistory.addView(rowButton)
    }

    private fun addEmptyView() {
        val emptyView = Button(this).apply {
            text = "No history found."
            isEnabled = false
        }
        alertsContainerHistory.addView(emptyView)
    }
}