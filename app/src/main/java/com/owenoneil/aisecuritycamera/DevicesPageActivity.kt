package com.owenoneil.aisecuritycamera
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.setPadding
import com.owenoneil.aisecuritycamera.R



class DevicesPageActivity : AppCompatActivity() {

    private lateinit var btnHamburger: ImageButton
    private lateinit var customMenu: View
    private lateinit var btnAddDevice: Button
    private lateinit var AddDeviceMenu: View
    private lateinit var etDeviceName: EditText
    private lateinit var etDeviceCode: EditText
    private lateinit var btnDone: Button
    private lateinit var deviceContainer: LinearLayout
    private lateinit var btnHome: Button
    private lateinit var btnDevices: Button
    private lateinit var btnAlerts: Button
    private lateinit var btnHistory: Button
    private lateinit var profileDropdown: View
    private lateinit var btnlogin: Button
    private lateinit var btnlogout: Button
    private lateinit var menuProfile: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_devices_page)

        // Handle system bars padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Find views
        btnHamburger = findViewById(R.id.btnHamburger)
        customMenu = findViewById(R.id.customMenu)
        btnAddDevice = findViewById(R.id.btnAddDevice)
        AddDeviceMenu = findViewById(R.id.AddDeviceMenu)
        profileDropdown = findViewById(R.id.profileDropdown)
        btnHome = findViewById(R.id.btnHome)
        btnDevices = findViewById(R.id.btnDevices)
        btnAlerts = findViewById(R.id.btnAlerts)
        btnHistory = findViewById(R.id.btnHistory)
        etDeviceName = findViewById(R.id.etDeviceName)
        etDeviceCode = findViewById(R.id.etDeviceCode)
        btnDone = findViewById(R.id.btnDone)
        deviceContainer = findViewById(R.id.deviceContainer)
        btnlogin = findViewById(R.id.btnlogin)
        btnlogout = findViewById(R.id.btnlogout)
        menuProfile = findViewById(R.id.menuProfile)

        // Toggle dropdown menu visibility on hamburger click
        btnHamburger.setOnClickListener {
            if (customMenu.visibility == View.GONE) {
                customMenu.visibility = View.VISIBLE
            } else {
                customMenu.visibility = View.GONE
            }
        }
        btnAddDevice.setOnClickListener{
            if (AddDeviceMenu.visibility == View.GONE){
                AddDeviceMenu.visibility = View.VISIBLE
            }
            else{
                AddDeviceMenu.visibility = View.GONE
            }
        }
        menuProfile.setOnClickListener{
            if (profileDropdown.visibility == View.GONE){
                profileDropdown.visibility = View.VISIBLE
            } else{
                profileDropdown.visibility = View.GONE
            }
        }

        btnDone.setOnClickListener{
            val deviceName = etDeviceName.text.toString()
            if (deviceName.isNotEmpty()){
                val newButton = Button(this)
                newButton.text = deviceName
                newButton.setBackgroundColor(android.graphics.Color.parseColor("#FFFFFF"))
                newButton.setTextColor(android.graphics.Color.parseColor("#67AB7"))
                newButton.textSize = 16f
                newButton.setPadding(20,20,20,20)
                newButton.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_camera,0)
                newButton.compoundDrawablePadding = 16
                deviceContainer.addView(newButton)
                AddDeviceMenu.visibility = View.GONE
                etDeviceName.text.clear()
                etDeviceCode.text.clear()
            }
        }

        btnHome.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        btnAlerts.setOnClickListener {
            val intent = Intent(this, AlertsPageActivity::class.java)
            startActivity(intent)
        }
        btnHistory.setOnClickListener {
            val intent = Intent(this, HistoryPageActivity::class.java)
            startActivity(intent)
        }
        val textView = findViewById<TextView>(R.id.tvCameras)
        val content = SpannableString("Cameras")
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        textView.text = content
    }
}