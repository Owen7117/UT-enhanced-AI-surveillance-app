package com.owenoneil.aisecuritycamera

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegisterPageActivity : AppCompatActivity() {
    private lateinit var btnCloseRegister: ImageButton
    private lateinit var btnLoginLink: Button
    private lateinit var etPasswordRegister: EditText
    private lateinit var cbShowPasswordRegister: CheckBox
    private lateinit var btnRegister: Button
    private lateinit var etConfirmPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnCloseRegister = findViewById(R.id.btnCloseRegister)
        btnRegister = findViewById(R.id.btnRegister)
        etPasswordRegister = findViewById(R.id.etPasswordRegister)
        cbShowPasswordRegister = findViewById(R.id.cbShowPasswordRegister)
        btnLoginLink = findViewById(R.id.btnLoginLink)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)

        btnCloseRegister.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        cbShowPasswordRegister.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                etPasswordRegister.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                etConfirmPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                etPasswordRegister.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                etConfirmPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            etPasswordRegister.setSelection(etPasswordRegister.text.length)
        }

        btnLoginLink.setOnClickListener {
            val intent = Intent(this,LoginPageActivity::class.java)
            startActivity(intent)
        }
    }
}
