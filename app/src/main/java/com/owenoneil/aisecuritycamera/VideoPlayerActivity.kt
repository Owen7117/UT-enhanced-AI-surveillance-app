package com.owenoneil.aisecuritycamera


import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class VideoPlayerActivity : AppCompatActivity() {

    private lateinit var videoView: VideoView
    private lateinit var btnClose: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)

        videoView = findViewById(R.id.videoView)
        btnClose = findViewById(R.id.btnCloseVideo)

        val videoUrl = intent.getStringExtra("video_url")

        if (!videoUrl.isNullOrEmpty()) {
            val uri = Uri.parse(videoUrl)
            videoView.setVideoURI(uri)

            videoView.setOnPreparedListener { mp ->
                mp.setOnVideoSizeChangedListener { _, _, _ -> }
                videoView.start()
            }

            videoView.setOnErrorListener { _, _, _ ->
                finish()
                true
            }
        } else {
            // No URL provided, close immediately
            finish()
        }

        btnClose.setOnClickListener {
            videoView.stopPlayback()
            finish()
        }
    }

    override fun onPause() {
        super.onPause()
        if (videoView.isPlaying) videoView.pause()
    }

    override fun onResume() {
        super.onResume()
        videoView.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        videoView.stopPlayback()
    }
}