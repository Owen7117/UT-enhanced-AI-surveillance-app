package com.owenoneil.aisecuritycamera

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch

class HistoryPageActivity : AppCompatActivity() {

    private lateinit var btnHome: Button
    private lateinit var btnAlerts: Button
    private lateinit var btnHistory: Button
    private lateinit var alertsContainerHistory: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_history_page)

        btnHome = findViewById(R.id.btnHome)
        btnAlerts = findViewById(R.id.btnAlerts)
        btnHistory = findViewById(R.id.btnHistory)
        alertsContainerHistory = findViewById(R.id.alertsContainerHistory)

        btnHome.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        btnAlerts.setOnClickListener {
            startActivity(Intent(this, AlertsPageActivity::class.java))
        }

        btnHistory.setOnClickListener { }

        loadHistory()
    }

    private fun loadHistory() {
        lifecycleScope.launch {
            try {
                val historyList = SupabaseClientProvider.client
                    .from("history")
                    .select()
                    .decodeList<HistoryAlert>()
                    .sortedByDescending { it.created_at }

                alertsContainerHistory.removeAllViews()

                if (historyList.isEmpty()) {
                    addEmptyView()
                } else {
                    historyList.forEach { addHistoryRow(it) }
                }

            } catch (e: Exception) {
                Log.e("HISTORY", "Failed to load history", e)
                addEmptyView()
            }
        }
    }

    private fun addHistoryRow(alert: HistoryAlert) {

        val displayTime = alert.created_at
            ?.replace("T", " ")
            ?: "Unknown time"

        val danger = alert.danger_level ?: "N/A"

        val rowButton = Button(this).apply {
            text = "${alert.alert}\nTime: $displayTime\nDanger: $danger"
            isAllCaps = false

            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 10, 0, 10)
            }

            val videoUrl = alert.video_url

            if (!videoUrl.isNullOrEmpty()) {
                setOnClickListener {
                    Log.d("VIDEO_URL", videoUrl)

                    val intent = Intent(
                        this@HistoryPageActivity,
                        VideoPlayerActivity::class.java
                    )

                    intent.putExtra("VIDEO_URL", videoUrl)
                    startActivity(intent)
                }
            } else {
                isEnabled = false
                alpha = 0.5f
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