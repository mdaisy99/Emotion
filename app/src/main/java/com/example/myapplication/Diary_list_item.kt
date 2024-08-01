package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class Diary_list_item : AppCompatActivity() {

    private lateinit var dbHelper: DiaryDatabaseHelper
    private var date: String? = null
    private var id: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_item_diary)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // 받아온 데이터로 일기 내용을 화면에 표시
        date = intent.getStringExtra("date")
        val content = intent.getStringExtra("content")
        id = intent.getLongExtra("id", 0)

        findViewById<TextView>(R.id.diary_text).text = content
        findViewById<TextView>(R.id.daily_date).text = date

        dbHelper = DiaryDatabaseHelper(this)

        val imageButton3: ImageButton = findViewById(R.id.imageButton3)
        imageButton3.setOnClickListener {
            val intent = Intent(this, Diary_List::class.java)
            startActivity(intent)
            finish() // 현재 액티비티를 종료
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_list_item_diary, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_retouch -> {
                val intent = Intent(this, Diary_write::class.java)
                intent.putExtra("id", id)
                intent.putExtra("date", date)
                intent.putExtra("content", intent.getStringExtra("content"))
                startActivity(intent)
                finish() // 현재 액티비티를 종료
                true
            }
            R.id.action_remove -> {
                // 데이터베이스에서 일기 삭제
                dbHelper.deleteDiaryEntry(id)
                val intent = Intent(this, Diary_List::class.java)
                startActivity(intent)
                finish() // 현재 액티비티를 종료
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
