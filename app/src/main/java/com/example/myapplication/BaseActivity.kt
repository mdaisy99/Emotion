package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    protected fun setupBottomNavigation() {
        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView)
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.fragment_home -> {
                    if (this !is MainActivity) {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                    true
                }
                R.id.fragment_search -> {
                    if (this !is Diary_List) {
                        startActivity(Intent(this, Diary_List::class.java))
                        finish()
                    }
                    true
                }
                R.id.fragment_favorite -> {
                    if (this !is Meditation) {
                        startActivity(Intent(this, Meditation::class.java))
                        finish()
                    }
                    true
                }
                R.id.fragment_Calendar -> {
                    if (this !is EmotionCalActivity) {
                        startActivity(Intent(this, EmotionCalActivity::class.java))
                        finish()
                    }
                    true
                }
                R.id.fragment_settings -> {
                    if (this !is LockSettingActivity) {
                        startActivity(Intent(this, LockSettingActivity::class.java))
                        finish()
                    }
                    true
                }
                else -> false
            }
        }
    }
}
