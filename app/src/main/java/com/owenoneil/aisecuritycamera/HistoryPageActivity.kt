package com.owenoneil.aisecuritycamera

import android.net.Uri
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

class HistoryPageActivity : AppCompatActivity() {

    private lateinit var btnHome: Button
    private lateinit var btnAlerts: Button
    private lateinit var btnHistory: Button
    private lateinit var alertsContainerHistory: LinearLayout


    private lateinit var videoOverlay: FrameLayout
    private lateinit var btnCloseVideo: ImageButton
    private lateinit var playerView: androidx.media3.ui.PlayerView
    private lateinit var player: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_page)

        btnHome = findViewById(R.id.btnHome)
        btnAlerts = findViewById(R.id.btnAlerts)
        btnHistory = findViewById(R.id.btnHistory)
        alertsContainerHistory = findViewById(R.id.alertsContainerHistory)

        videoOverlay = findViewById(R.id.videoOverlay)
        btnCloseVideo = findViewById(R.id.btnCloseVideo)
        playerView = findViewById(R.id.playerView)


        player = ExoPlayer.Builder(this).build()
        playerView.player = player
        playerView.hideController()

        playerView.useController = true
        playerView.controllerAutoShow = false
        playerView.controllerHideOnTouch = false

        playerView.setOnClickListener {
            if (!player.isPlaying) {
                if (playerView.isControllerFullyVisible) {
                    playerView.hideController()
                } else {
                    playerView.showController()
                }
            }
        }

        player.addListener(object : androidx.media3.common.Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                playerView.post {
                    if (isPlaying) {
                        playerView.hideController()
                    }
                }
            }
        })


        btnHome.setOnClickListener { finish() }
        btnAlerts.setOnClickListener { finish() }
        btnHistory.setOnClickListener { }

        btnCloseVideo.setOnClickListener {
            player.stop()
            videoOverlay.visibility = View.GONE
        }

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

                    videoOverlay.visibility = View.VISIBLE

                    val mediaItem = MediaItem.fromUri(videoUrl)

                    player.setMediaItem(mediaItem)
                    player.prepare()
                    player.play()
                }

            } else {
                isEnabled = true
                setTextColor(resources.getColor(android.R.color.black))
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

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}