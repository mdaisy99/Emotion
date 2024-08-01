package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.content.SharedPreferences
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)

        if (sharedPreferences.getBoolean("lock_enabled", false)) {
            val savedPassword = sharedPreferences.getString("password", null)
            if (savedPassword != null) {
                // 비밀번호 입력화면 표시
                val intent = Intent(this, LockActivity::class.java)
                startActivity(intent)
                finish() // 잠금해제 후 앱 사용 가능
            }
        }

        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView)
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.fragment_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.fragment_search -> {
                    startActivity(Intent(this, Diary_write::class.java))
                    true
                }
                R.id.fragment_favorite -> {
                    startActivity(Intent(this, Meditation::class.java))
                    true
                }
                R.id.fragment_Calendar -> {
                    startActivity(Intent(this, EmotionCalActivity::class.java))
                    true
                }
                R.id.fragment_settings -> {
                    startActivity(Intent(this, LockSettingActivity::class.java))
                    true
                }
                else -> false
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.btn_confirm)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}