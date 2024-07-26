package com.example.myapplication

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Timer
import android.app.AlarmManager
import android.content.BroadcastReceiver
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import android.content.Context
import android.widget.TextView
import java.util.Calendar

object AlarmHelper {

    fun setMidnightAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, MidnightReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        // 자정 시간 설정
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        // 다음 자정으로 설정하기 위해 24시간을 더함
        val triggerAtMillis = calendar.timeInMillis + AlarmManager.INTERVAL_DAY

        // 반복 알람 설정
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, triggerAtMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
    }
}

class Meditation : AppCompatActivity() {

        private var time = 0
        private var timerTask : Timer? = null

        lateinit var emotionWord : TextView
        lateinit var emotionMean : TextView

        private val midnightReceiver = object : BroadcastReceiver() {

            override fun onReceive(context: Context, intent: Intent) {
                // TextView 내용 변경
                emotionWord.text = "New Emotion Word"
            }

        }

        override fun onDestroy() {
            super.onDestroy()
            // LocalBroadcastManager 해제
            LocalBroadcastManager.getInstance(this).unregisterReceiver(midnightReceiver)
        }


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()

            // TextView 초기화
            emotionWord = findViewById(R.id.emotionWord)

            // 자정 알람 설정
            AlarmHelper.setMidnightAlarm(this)

            // LocalBroadcastManager 등록
            LocalBroadcastManager.getInstance(this).registerReceiver(midnightReceiver, IntentFilter("MIDNIGHT_ALARM"))

            setContentView(R.layout.activity_meditation)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets


        }
    }

}