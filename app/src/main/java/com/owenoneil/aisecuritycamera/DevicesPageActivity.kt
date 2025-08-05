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
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.setPadding
import androidx.room.Room
import com.owenoneil.aisecuritycamera.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

    private lateinit var database: DevicesDatabase


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

        database = DevicesDatabase.getInstance(this)

        CoroutineScope(Dispatchers.IO).launch {
            val savedDevices = database.dao.getAllDevices()
            withContext(Dispatchers.Main) {
                for (device in savedDevices) {
                    addDeviceButton(device.devicename)
                }
            }
        }


        // Find views
        btnHamburger = findViewById(R.id.btnHamburger)
        customMenu = findViewById(R.id.customMenu)
        btnAddDevice = findViewById(R.id.btnAddDevice)
        AddDeviceMenu = findViewById(R.id.AddDeviceMenu)
        etDeviceName = findViewById(R.id.etDeviceName)
        etDeviceCode = findViewById(R.id.etDeviceCode)
        btnDone = findViewById(R.id.btnDone)
        deviceContainer = findViewById(R.id.deviceContainer)
        btnHome = findViewById(R.id.btnHome)
        btnDevices = findViewById(R.id.btnDevices)
        btnAlerts = findViewById(R.id.btnAlerts)
        btnHistory = findViewById(R.id.btnHistory)
        profileDropdown = findViewById(R.id.profileDropdown)
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

        btnDone.setOnClickListener {
            val name = etDeviceName.text.toString()
            val idText = etDeviceCode.text.toString()

            if (name.isNotEmpty() && idText.isNotEmpty()) {
                val id = idText.toIntOrNull()
                if (id == null) {
                    Toast.makeText(this, "Device ID must be a number", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val device = AddDevice(deviceid = id, devicename = name)

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        database.dao.insertDevice(device)
                        runOnUiThread {
                            addDeviceButton(name)
                            AddDeviceMenu.visibility = View.GONE
                            etDeviceName.text.clear()
                            etDeviceCode.text.clear()
                        }
                    } catch (e: Exception) {
                        runOnUiThread {
                            Toast.makeText(this@DevicesPageActivity, "Device ID already exists!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Please enter both name and ID", Toast.LENGTH_SHORT).show()
            }
        }

        // Navigation buttons
        btnlogin.setOnClickListener {
            startActivity(Intent(this, LoginPageActivity::class.java))
        }
        btnHome.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        btnAlerts.setOnClickListener {
            startActivity(Intent(this, AlertsPageActivity::class.java))
        }
        btnHistory.setOnClickListener {
            startActivity(Intent(this, HistoryPageActivity::class.java))
        }

        // Underline "Cameras" title
        val textView = findViewById<TextView>(R.id.tvCameras)
        val content = SpannableString("Cameras")
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        textView.text = content
    }

    private fun addDeviceButton(name: String) {
        val newButton = Button(this).apply {
            text = name
            setBackgroundColor(android.graphics.Color.parseColor("#FFFFFF"))
            setTextColor(android.graphics.Color.parseColor("#67AB7"))
            textSize = 16f
            setPadding(20, 20, 20, 20)
            setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_camera, 0)
            compoundDrawablePadding = 16
        }
        deviceContainer.addView(newButton)
    }
}