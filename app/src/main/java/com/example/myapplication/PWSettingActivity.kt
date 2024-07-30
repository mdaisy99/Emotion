package com.example.myapplication

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class PwSettingActivity : AppCompatActivity() {

    private lateinit var buttons: List<Button>
    private lateinit var buttonDel: Button
    private lateinit var button0: Button
    private lateinit var buttonStartStop: Button
    private lateinit var pwIndicators: List<ImageView>
    private lateinit var sharedPreferences: SharedPreferences
    private var inputPassword: StringBuilder = StringBuilder()
    private var isChangeMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pwsetting)

        sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)
        isChangeMode = intent.getBooleanExtra("isChangeMode", false)

        buttons = listOf(
            findViewById(R.id.button1),
            findViewById(R.id.button2),
            findViewById(R.id.button3),
            findViewById(R.id.button4),
            findViewById(R.id.button5),
            findViewById(R.id.button6),
            findViewById(R.id.button7),
            findViewById(R.id.button8),
            findViewById(R.id.button9),
            findViewById(R.id.button0)
        )
        buttonDel = findViewById(R.id.buttonDel)
        buttonStartStop = findViewById(R.id.buttonStartStop)
        pwIndicators = listOf(
            findViewById(R.id.pw01),
            findViewById(R.id.pw02),
            findViewById(R.id.pw03),
            findViewById(R.id.pw04)
        )

        // Initialize buttons
        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                onNumberButtonClick(index + 1)
            }
        }
        button0.setOnClickListener { onNumberButtonClick(0) }
        buttonDel.setOnClickListener { onDeleteButtonClick() }
        buttonStartStop.setOnClickListener { onBackspaceButtonClick() }

        // Handle the case of changing or setting the password
        if (isChangeMode) {
            title = "Change Password"
        } else {
            title = "Set Password"
        }
    }

    private fun onNumberButtonClick(number: Int) {
        if (inputPassword.length < 4) {
            inputPassword.append(number)
            updateIndicators()
        }
    }

    private fun onDeleteButtonClick() {
        inputPassword.clear()
        updateIndicators()
    }

    private fun onBackspaceButtonClick() {
        if (inputPassword.isNotEmpty()) {
            inputPassword.deleteAt(inputPassword.length - 1)
            updateIndicators()
        }
    }

    private fun updateIndicators() {
        pwIndicators.forEachIndexed { index, imageView ->
            imageView.visibility = if (index < inputPassword.length) ImageView.VISIBLE else ImageView.INVISIBLE
        }
        if (inputPassword.length == 4) {
            savePassword()
        }
    }

    private fun savePassword() {
        if (inputPassword.length == 4) {
            val password = inputPassword.toString()
            sharedPreferences.edit().putString("password", password).apply()
            if (!isChangeMode) {
                finish() // Close activity and go back to lock settings
            }
        }
    }
}