package com.example.myapplication

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LockActivity : AppCompatActivity() {

    private lateinit var buttons: List<Button>
    private lateinit var buttonDel: Button
    private lateinit var buttonBackspace: Button
    private lateinit var pwIndicators: List<ImageView>
    private lateinit var sharedPreferences: SharedPreferences
    private var inputPassword: StringBuilder = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lock)

        sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)

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
        buttonBackspace = findViewById(R.id.buttonStartStop)
        pwIndicators = listOf(
            findViewById(R.id.pw01),
            findViewById(R.id.pw02),
            findViewById(R.id.pw03),
            findViewById(R.id.pw04)
        )

        // 버튼 초기화
        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                onNumberButtonClick(index + 1)
            }
        }
        findViewById<Button>(R.id.button0).setOnClickListener { onNumberButtonClick(0) }
        buttonDel.setOnClickListener { onDeleteButtonClick() }
        buttonBackspace.setOnClickListener { onBackspaceButtonClick() }

        if (!sharedPreferences.getBoolean("lock_enabled", false)) {
            // 잠금이 활성화되지 않은 경우 잠금 화면 건너뛰기
            startActivity(Intent(this, MainActivity::class.java))
            finish()
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
            checkPassword()
        }
    }

    private fun checkPassword() {
        val savedPassword = sharedPreferences.getString("password", null)
        if (inputPassword.toString() == savedPassword) {
            // Correct password: navigate to MainActivity with unlocked flag
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("unlocked", true)
            startActivity(intent)
            finish() // Ensure LockActivity is finished
        } else {
            inputPassword.clear()
            updateIndicators()
            // 암호가 잘못되었다는 메시지 출력
            Toast.makeText(this, "암호가 잘못되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}
