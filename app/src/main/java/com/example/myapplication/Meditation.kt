package com.example.myapplication

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.BroadcastReceiver
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import android.content.Context
import android.os.CountDownTimer
import android.widget.Button
import android.widget.Chronometer
import android.widget.TextView

class Meditation : BaseActivity() {

    lateinit var emotionWord: TextView
    lateinit var emotionMean: TextView

    private lateinit var chronometer: Chronometer
    private lateinit var buttonStartStop: Button
    private var timer: CountDownTimer? = null
    private var isRunning = false
    private var timeInMillis: Long = 0

    private val midnightReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            // TextView 내용 변경
            val (newWord, newMean) = getNewEmotionWordAndMean()
            emotionWord.text = newWord
            emotionMean.text = newMean
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // LocalBroadcastManager 해제
        LocalBroadcastManager.getInstance(this).unregisterReceiver(midnightReceiver)
    }

    private fun getNewEmotionWordAndMean(): Pair<String, String> {
        // 오늘의 랜덤 단어 (10개의 단어 세트)
        val emotionPairs = listOf(
            Pair("감동하다", "크게 느끼어 마음이 움직이다."),
            Pair("고깝다", "섭섭하고 야속하여 마음이 언짢다."),
            Pair("고맙다", "남이 베풀어 준 호의나 도움 따위에 대하여 마음이 흐뭇하고 즐겁다."),
            Pair("귀찮다", "마음에 들지 아니하고 괴롭거나 성가시다."),
            Pair("기쁘다", "욕구가 충족되어 마음이 흐뭇하고 흡족하다."),
            Pair("보람차다", "어떤 일을 한 뒤에 결과가 몹시 좋아서 자랑스러움과 자부심을 갖게 할 만큼 만족스럽다."),
            Pair("사무치다", "깊이 스며들거나 멀리까지 미치다."),
            Pair("반갑다", "그리워하던 사람을 만나거나 원하는 일이 이루어져서 마음이 즐겁고 기쁘다."),
            Pair("송연하다", "두려워 몸을 옹송그릴 정도로 오싹 소름이 끼치는 듯하다."),
            Pair("영예롭다", "영예로 여길 만하다.")
        )

        // 무작위로 하나의 쌍을 선택
        return emotionPairs.random()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_meditation)
        setupBottomNavigation(R.id.fragment_favorite)

        // TextView 초기화
        emotionWord = findViewById(R.id.emotionWord)
        emotionMean = findViewById(R.id.emotionMean)

        // 자정 알람 설정
        AlarmHelper.setMidnightAlarm(this)

        // LocalBroadcastManager 등록
        LocalBroadcastManager.getInstance(this).registerReceiver(midnightReceiver, IntentFilter("MIDNIGHT_ALARM"))

        // 앱 실행 시 한 번 midnightReceiver 실행
        midnightReceiver.onReceive(this, Intent())

        chronometer = findViewById(R.id.chronometer)
        buttonStartStop = findViewById(R.id.buttonStartStop)

        val buttons = listOf(
            findViewById<Button>(R.id.button0),
            findViewById<Button>(R.id.button1),
            findViewById<Button>(R.id.button2),
            findViewById<Button>(R.id.button3),
            findViewById<Button>(R.id.button4),
            findViewById<Button>(R.id.button5),
            findViewById<Button>(R.id.button6),
            findViewById<Button>(R.id.button7),
            findViewById<Button>(R.id.button8),
            findViewById<Button>(R.id.button9),
            findViewById<Button>(R.id.buttonDel)
        )

        val timeButtons = listOf(
            findViewById<Button>(R.id.btn_1min),
            findViewById<Button>(R.id.btn_5min),
            findViewById<Button>(R.id.btn_10min),
            findViewById<Button>(R.id.btn_15min),
            findViewById<Button>(R.id.btn_30min)
        )

        buttons.forEach { button ->
            button.setOnClickListener { onNumberButtonClick(button.text.toString()) }
        }

        findViewById<Button>(R.id.buttonDel).setOnClickListener {
            chronometer.text = "00:00"
        }

        timeButtons.forEach { button ->
            button.setOnClickListener { onTimeButtonClick(button.text.toString().replace("분", "")) }
        }

        buttonStartStop.setOnClickListener {
            if (isRunning) {
                stopTimer()
            } else {
                startTimer()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            WindowInsetsCompat.CONSUMED
        }

        //setupBottomNavigation() // 바텀 바
    }

    private fun onNumberButtonClick(number: String) {
        val currentText = chronometer.text.toString().replace(":", "")
        val newText = (currentText + number).takeLast(4).padStart(4, '0')
        val minutes = newText.substring(0, 2)
        val seconds = newText.substring(2, 4)
        chronometer.text = "$minutes:$seconds"
    }

    private fun onTimeButtonClick(time: String) {
        chronometer.text = time.padStart(2, '0') + ":00"
    }

    private fun startTimer() {
        val currentText = chronometer.text.toString().split(":")
        val minutes = currentText[0].toInt()
        val seconds = currentText[1].toInt()
        timeInMillis = ((minutes * 60) + seconds) * 1000L

        timer = object : CountDownTimer(timeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val totalSeconds = millisUntilFinished / 1000
                val minutes = totalSeconds / 60
                val seconds = totalSeconds % 60
                chronometer.text = minutes.toString().padStart(2, '0') + ":" + seconds.toString().padStart(2, '0')
            }

            override fun onFinish() {
                chronometer.text = "00:00"
                buttonStartStop.text = "START"
                isRunning = false
            }
        }.start()

        buttonStartStop.text = "STOP"
        isRunning = true
    }

    private fun stopTimer() {
        timer?.cancel()
        buttonStartStop.text = "START"
        isRunning = false
    }
}
