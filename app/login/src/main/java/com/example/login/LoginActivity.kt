package com.example.login

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class LoginActivity : AppCompatActivity() {
    private lateinit var userName: EditText
    private lateinit var passWord: EditText
    private lateinit var loginBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // initializing EditText and Button
        userName = findViewById(R.id.username)
        passWord = findViewById(R.id.password)
        loginBtn = findViewById(R.id.loginBtn)

        loginBtn.setOnClickListener {
            // for testing purpose we are using username as parthi
            // and password as 123
            // On successful login it will display a toast message
            if (userName.text.toString() == "parthi" && passWord.text
                    .toString() == "123"
            ) {
                Toast.makeText(
                    this@LoginActivity,
                    "Modular Architecture Works Fine",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}