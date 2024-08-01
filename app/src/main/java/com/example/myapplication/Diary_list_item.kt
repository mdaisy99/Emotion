package com.example.myapplication

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class Diary_list_item : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_item_diary)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // 받아온 데이터로 일기 내용을 화면에 표시
        val date = intent.getStringExtra("date")
        val content = intent.getStringExtra("content")
        findViewById<TextView>(R.id.diary_text).text = content
        findViewById<TextView>(R.id.daily_date).text = date
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_list_item_diary, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_retouch -> {
                // 수정 작업
                true
            }
            R.id.action_remove -> {
                // 삭제 작업
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
