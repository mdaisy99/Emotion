package com.example.myapplication

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Switch
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LockSettingActivity : BaseActivity() {

    private lateinit var switchBtn: Switch
    private lateinit var buttonPWChange: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lock_setting)
        setupBottomNavigation(R.id.fragment_settings)

        sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)
        switchBtn = findViewById(R.id.switchBtn)
        buttonPWChange = findViewById(R.id.buttonPWChange)

        // 스위치 초기값 불러오기
        switchBtn.isChecked = sharedPreferences.getBoolean("lock_enabled", false)

        switchBtn.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("lock_enabled", isChecked).apply()
        }

        buttonPWChange.setOnClickListener {
            val intent = Intent(this, PWSettingActivity::class.java)
            intent.putExtra("isChangeMode", true) // Indicate that it's for password change
            startActivity(intent)
        }
        //setupBottomNavigation()
    }
}
