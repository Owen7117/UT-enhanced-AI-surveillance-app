package com.owenoneil.aisecuritycamera
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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

//    private lateinit var database: DevicesDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_devices_page)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        database = DevicesDatabase.getInstance(this)

//        CoroutineScope(Dispatchers.IO).launch {
//            val savedDevices = database.deviceDao().getAllDevices()
//            withContext(Dispatchers.Main) {
//                for (device in savedDevices) {
//                    addDeviceButton(device.devicename)
//                }
//            }
//        }


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


        btnHamburger.setOnClickListener {
            if (customMenu.visibility == View.GONE) {
                customMenu.visibility = View.VISIBLE
            } else {
                customMenu.visibility = View.GONE
            }
        }
        btnAddDevice.setOnClickListener {
            if (AddDeviceMenu.visibility == View.GONE) {
                AddDeviceMenu.visibility = View.VISIBLE
            } else {
                AddDeviceMenu.visibility = View.GONE
            }
        }
        menuProfile.setOnClickListener {
            if (profileDropdown.visibility == View.GONE) {
                profileDropdown.visibility = View.VISIBLE
            } else {
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

//                val device = AddDevice(deviceid = id, devicename = name)
//
//                CoroutineScope(Dispatchers.IO).launch {
//                    val existing =
//                        database.deviceDao().getDeviceById(id)
//
//                    if (existing == null) {
//                        database.deviceDao().insertDevice(device)
//                        withContext(Dispatchers.Main) {
//                            addDeviceButton(name)
//                            AddDeviceMenu.visibility = View.GONE
//                            etDeviceName.text.clear()
//                            etDeviceCode.text.clear()
//                        }
//                    } else {
//                        withContext(Dispatchers.Main) {
//                            Toast.makeText(
//                                this@DevicesPageActivity,
//                                "Device ID already exists!",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    }
//                }
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
    }

    private fun addDeviceButton(name: String) {
        val wrapperLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, dpToPx(10), 0, dpToPx(10))
            }
            gravity = android.view.Gravity.CENTER_VERTICAL
        }


        val newButton = Button(this).apply {
            text = name
            setBackgroundColor(android.graphics.Color.parseColor("#D3D3D3"))
            setTextColor(android.graphics.Color.BLACK)
            textSize = 18f
            setPadding(dpToPx(20), dpToPx(20), dpToPx(20), dpToPx(20))
            setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_camera, 0)

            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            ).apply {
                leftMargin = dpToPx(16)
            }
        }


        val menuButton = ImageButton(this).apply {
            setImageResource(R.drawable.ic_more_vert)
            setBackgroundColor(android.graphics.Color.TRANSPARENT)
            layoutParams = LinearLayout.LayoutParams(
                dpToPx(48),
                dpToPx(48)
            ).apply {
                rightMargin = dpToPx(16)
            }
        }


        menuButton.setOnClickListener { view ->
            val popup = PopupMenu(this, view)
            popup.menuInflater.inflate(
                R.menu.device_item_menu,
                popup.menu
            )
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_delete -> {

                        AlertDialog.Builder(this)
                            .setTitle("Delete Device")
                            .setMessage("Are you sure you want to delete \"$name\"?")
                            .setPositiveButton("Delete") { dialog, _ ->

                                deviceContainer.removeView(wrapperLayout)

                                CoroutineScope(Dispatchers.IO).launch {
//                                    database.deviceDao().deleteDeviceByName(name)
                                }

                                dialog.dismiss()
                            }
                            .setNegativeButton("Cancel") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .show()
                        true
                    }

                    else -> false
                }
            }
            popup.show()
        }


        wrapperLayout.addView(newButton)
        wrapperLayout.addView(menuButton)


        deviceContainer.addView(wrapperLayout)
    }

    private fun dpToPx(dp: Int): Int {
        val density = resources.displayMetrics.density
        return (dp * density).toInt()
    }
}