package com.owenoneil.aisecuritycamera

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch
import java.time.LocalDate

class AlertsPageActivity : AppCompatActivity() {

    private lateinit var btnHome: Button
    private lateinit var btnAlerts: Button
    private lateinit var btnHistory: Button
    private lateinit var alertsContainer: LinearLayout

    private lateinit var videoOverlay: FrameLayout
    private lateinit var btnCloseVideo: ImageButton
    private lateinit var playerView: androidx.media3.ui.PlayerView
    private lateinit var player: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alerts_page)

        // NAV
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


        videoOverlay = findViewById(R.id.videoOverlay)
        btnCloseVideo = findViewById(R.id.btnCloseVideo)
        playerView = findViewById(R.id.playerView)

        player = ExoPlayer.Builder(this).build()
        playerView.player = player

        playerView.useController = true
        playerView.controllerAutoShow = false
        playerView.controllerHideOnTouch = false
        playerView.hideController()

        player.addListener(object : androidx.media3.common.Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                if (isPlaying) {
                    playerView.post {
                        playerView.hideController()
                    }
                }
            }
        })
        playerView.setOnClickListener {
            if (!player.isPlaying) {
                if (playerView.isControllerFullyVisible) {
                    playerView.hideController()
                } else {
                    playerView.showController()
                }
            }
        }
        btnCloseVideo.setOnClickListener {
            player.stop()
            videoOverlay.visibility = View.GONE
        }

        loadTodayAlerts()
    }

    private fun loadTodayAlerts() {
        lifecycleScope.launch {
            try {
                val today = LocalDate.now().toString()

                val alerts = SupabaseClientProvider.client
                    .from("history")
                    .select()
                    .decodeList<HistoryAlert>()
                    .filter {
                        it.created_at.startsWith(today)
                    }
                    .sortedByDescending { it.created_at }

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
        val videoUrl = alert.video_url

        val rowButton = Button(this).apply {
            text = "${alert.alert}\nTime: $displayTime\nDanger: ${alert.danger_level}"
            isAllCaps = false

            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 8, 0, 8)
            }

            if (!videoUrl.isNullOrEmpty()) {
                setOnClickListener {

                    videoOverlay.visibility = View.VISIBLE

                    val mediaItem = MediaItem.fromUri(videoUrl)

                    player.setMediaItem(mediaItem)
                    player.prepare()
                    player.play()
                }
            } else {
                setOnClickListener {
                    Toast.makeText(
                        this@AlertsPageActivity,
                        "No video available",
                        Toast.LENGTH_SHORT
                    ).show()
                }
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

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}