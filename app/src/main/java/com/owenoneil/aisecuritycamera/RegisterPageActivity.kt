package com.owenoneil.aisecuritycamera
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class RegisterPageActivity : AppCompatActivity() {
    private lateinit var btnClose: ImageButton
    private lateinit var btnLoginLink: Button
    private lateinit var etPassword: EditText
    private lateinit var cbShowPasswordRegister: CheckBox
    private lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnClose = findViewById(R.id.btnClose)
        btnRegister = findViewById(R.id.btnRegister)
        etPassword = findViewById(R.id.etPassword)
        cbShowPasswordRegister = findViewById(R.id.cbShowPasswordRegister)
        btnLoginLink = findViewById(R.id.btnLoginLink)

        btnClose.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        cbShowPasswordRegister.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                etPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                etPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            etPassword.setSelection(etPassword.text.length)
        }

        btnLoginLink.setOnClickListener {
            val intent = Intent(this,LoginPageActivity::class.java)
            startActivity(intent)
        }
    }
}
