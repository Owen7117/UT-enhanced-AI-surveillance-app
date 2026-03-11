package com.owenoneil.aisecuritycamera

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var btnHamburger: ImageButton
    private lateinit var customMenu: View

    private lateinit var btnHome: Button
    private lateinit var btnAlerts: Button
    private lateinit var btnHistory: Button
    private lateinit var profileDropdown: View
    private lateinit var btnlogin: Button
    private lateinit var btnlogout: Button
    private lateinit var menuProfile: Button

    private lateinit var youtubeWebView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        btnHamburger = findViewById(R.id.btnHamburger)
        customMenu = findViewById(R.id.customMenu)

        profileDropdown = findViewById(R.id.profileDropdown)
        btnHome = findViewById(R.id.btnHome)
        btnAlerts = findViewById(R.id.btnAlerts)
        btnHistory = findViewById(R.id.btnHistory)
        btnlogin = findViewById(R.id.btnlogin)
        btnlogout = findViewById(R.id.btnlogout)
        menuProfile = findViewById(R.id.menuProfile)

        youtubeWebView = findViewById(R.id.youtubeWebView)

        setupWebView()

        btnHamburger.setOnClickListener {
            customMenu.visibility =
                if (customMenu.visibility == View.GONE) View.VISIBLE else View.GONE
        }

        menuProfile.setOnClickListener {
            profileDropdown.visibility =
                if (profileDropdown.visibility == View.GONE) View.VISIBLE else View.GONE
        }

        btnlogin.setOnClickListener {
            startActivity(Intent(this, LoginPageActivity::class.java))
        }

        btnAlerts.setOnClickListener {
            startActivity(Intent(this, AlertsPageActivity::class.java))
        }

        btnHistory.setOnClickListener {
            startActivity(Intent(this, HistoryPageActivity::class.java))
        }
    }

    private fun setupWebView() {

        youtubeWebView.webViewClient = WebViewClient()

        val settings = youtubeWebView.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.mediaPlaybackRequiresUserGesture = false
        settings.loadWithOverviewMode = true
        settings.useWideViewPort = true

        val videoId = "R7wB5AoCMms"

        youtubeWebView.loadUrl("https://www.youtube.com/watch?v=$videoId")
    }

    override fun onPause() {
        youtubeWebView.onPause()
        super.onPause()
    }

    override fun onResume() {
        youtubeWebView.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        youtubeWebView.destroy()
        super.onDestroy()
    }
}