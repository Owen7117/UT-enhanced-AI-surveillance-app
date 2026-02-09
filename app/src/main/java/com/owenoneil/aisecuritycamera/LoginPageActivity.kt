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


class LoginPageActivity : AppCompatActivity() {

    private lateinit var btnCloseLogin: ImageButton
    private lateinit var btnloginpagelogin: Button
    private lateinit var etPasswordLogin: EditText
    private lateinit var cbShowPasswordLogin: CheckBox
    private lateinit var btnRegisterLink: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login_page)

        btnCloseLogin = findViewById(R.id.btnCloseLogin)
        btnloginpagelogin = findViewById(R.id.btnloginpagelogin)
        etPasswordLogin = findViewById(R.id.etPasswordLogin)
        cbShowPasswordLogin = findViewById(R.id.cbShowPasswordLogin)
        btnRegisterLink = findViewById(R.id.btnRegisterLink)

        btnCloseLogin.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        btnRegisterLink.setOnClickListener{
            val intent = Intent(this,RegisterPageActivity::class.java)
            startActivity(intent)
        }
        cbShowPasswordLogin.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                etPasswordLogin.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else{
                etPasswordLogin.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            etPasswordLogin.setSelection(etPasswordLogin.text.length)
        }
    }
}