package com.example.myapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class MidnightReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("MidnightReceiver", "Alarm received at midnight")

        // LocalBroadcast를 사용하여 MainActivity에 브로드캐스트 보내기
        val localIntent = Intent("MIDNIGHT_ALARM")
        LocalBroadcastManager.getInstance(context).sendBroadcast(localIntent)
    }
}
