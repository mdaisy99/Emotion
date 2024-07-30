package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.content.SharedPreferences
import android.widget.CompoundButton
import android.widget.Button

class LockSettingActivity : AppCompatActivity() {

    private lateinit var switchBtn: CompoundButton
    private lateinit var buttonPWChange: Button
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lock_setting)

        sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)
        switchBtn = findViewById(R.id.switchBtn)
        buttonPWChange = findViewById(R.id.buttonPWChange)

        // Load and set initial switch state
        switchBtn.isChecked = sharedPreferences.getBoolean("lock_enabled", false)

        switchBtn.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("lock_enabled", isChecked).apply()
        }

        buttonPWChange.setOnClickListener {
            val intent = Intent(this, PwSettingActivity::class.java)
            intent.putExtra("isChangeMode", true) // Indicate that it's for password change
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}