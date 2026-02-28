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
    private lateinit var btnDevices: Button
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
        btnDevices = findViewById(R.id.btnDevices)
        btnAlerts = findViewById(R.id.btnAlerts)
        btnHistory = findViewById(R.id.btnHistory)
        btnlogin = findViewById(R.id.btnlogin)
        btnlogout = findViewById(R.id.btnlogout)
        menuProfile = findViewById(R.id.menuProfile)

        youtubeWebView = findViewById(R.id.youtubeWebView)

        youtubeWebView.webViewClient = WebViewClient()
        youtubeWebView.settings.javaScriptEnabled = true
        youtubeWebView.settings.domStorageEnabled = true
        youtubeWebView.settings.mediaPlaybackRequiresUserGesture = false

        val videoId = "TKdyfoEEWYw"

        val html = """
            <html>
            <head>
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <style>
                body { margin:0; background:black; }
                iframe { position:fixed; top:0; left:0; width:100%; height:100%; }
            </style>
            </head>
            <body>
                <iframe
                    src="https://www.youtube.com/embed/$videoId?autoplay=1&controls=0&modestbranding=1&rel=0&showinfo=0"
                    frameborder="0"
                    allow="autoplay; encrypted-media"
                    allowfullscreen>
                </iframe>
            </body>
            </html>
            """.trimIndent()

        youtubeWebView.loadDataWithBaseURL(
            "https://www.youtube.com",
            html,
            "text/html",
            "utf-8",
            null
        )

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

        btnDevices.setOnClickListener {
            startActivity(Intent(this, DevicesPageActivity::class.java))
        }

        btnAlerts.setOnClickListener {
            startActivity(Intent(this, AlertsPageActivity::class.java))
        }

        btnHistory.setOnClickListener {
            startActivity(Intent(this, HistoryPageActivity::class.java))
        }
    }

    override fun onPause() {
        youtubeWebView.onPause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        youtubeWebView.onResume()
    }

    override fun onDestroy() {
        youtubeWebView.destroy()
        super.onDestroy()
    }
}