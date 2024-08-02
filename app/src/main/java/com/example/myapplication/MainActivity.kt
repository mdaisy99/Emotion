package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.content.SharedPreferences
import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.widget.ImageView
import java.util.Calendar

class MainActivity : BaseActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private var selectedEmotion: String = "normal"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        setupBottomNavigation(R.id.fragment_home)

        sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)

        loadEmotion()

        setupMoodButtons()


        val btnSaveEmotion = findViewById<Button>(R.id.btn_emotion)
        btnSaveEmotion.setOnClickListener {
            saveEmotion(selectedEmotion)  // 선택된 감정 저장 및 이미지 업데이트
        }

        // 비밀번호 설정 확인
        if (sharedPreferences.getBoolean("lock_enabled", false)) {
            val savedPassword = sharedPreferences.getString("password", null)
            if (!intent.getBooleanExtra("unlocked", false) && savedPassword != null) {
                // 비밀번호 입력화면 표시
                val intent = Intent(this, LockActivity::class.java)
                startActivity(intent)
                finish()
                return
            }
        }

        val btnConfirm = findViewById<Button>(R.id.btn_confirm)
        btnConfirm.setOnClickListener {
            val intent = Intent(this, Diary_write::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.btn_confirm)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupMoodButtons() {
        findViewById<Button>(R.id.tv_anxious).setOnClickListener { selectedEmotion = "angry" }
        findViewById<Button>(R.id.tv_happy).setOnClickListener { selectedEmotion = "happy" }
        findViewById<Button>(R.id.tv_confused).setOnClickListener { selectedEmotion = "confused" }
        findViewById<Button>(R.id.tv_sad).setOnClickListener { selectedEmotion = "sad" }
        findViewById<Button>(R.id.tv_normal).setOnClickListener { selectedEmotion = "normal" }
        findViewById<Button>(R.id.tv_happy2).setOnClickListener { selectedEmotion = "happy2" }
    }

    private fun saveEmotion(emotion: String) {
        val editor = sharedPreferences.edit()
        val currentDate = getCurrentDate()
        editor.putString("DATE_$currentDate", emotion)
        //editor.putString("selected_emotion", emotion)
        editor.apply()
        updateMoodImage(emotion)
    }

    private fun getCurrentDate(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.DAY_OF_MONTH)
    }

    private fun loadEmotion() {
        val savedEmotion = sharedPreferences.getString("selected_emotion", "normal")
        updateMoodImage(savedEmotion ?: "normal")
    }

    private fun updateMoodImage(emotion: String) {
        val moodImage = when (emotion) {
            "angry" -> R.drawable.angry
            "happy" -> R.drawable.happy
            "confused" -> R.drawable.anxiety
            "sad" -> R.drawable.sad
            "normal" -> R.drawable.normal
            "happy2" -> R.drawable.excited
            else -> R.drawable.normal
        }
        findViewById<ImageView>(R.id.img_mood).setImageResource(moodImage)
    }
}